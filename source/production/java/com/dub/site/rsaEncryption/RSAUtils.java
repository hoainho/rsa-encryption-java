package com.dub.site.rsaEncryption;

import java.math.BigInteger;

/**
 * Utility class that implements two RSA reated methods 
 * */

public class RSAUtils {
	
	public static PrivateKey dKeyGen(
								BigInteger p, 
								BigInteger q,
								BigInteger e) 
	{
		BigInteger p1 = p.subtract(BigInteger.ONE);
		BigInteger q1 = q.subtract(BigInteger.ONE);
			
		BigInteger lambda, d; 
		
		EuclidResult result = new EuclidResult();
		
		GCD(p1, q1, result);
		
		lambda = p1.multiply(q1).divide(result.getD());// LCM(p-1, q-1)
	
		PrivateKey privateKey = new PrivateKey();
		
		if (lambda.mod(e) == BigInteger.ZERO) {// invalid parameter set
			privateKey.setStatus(PrivateKey.Status.ERROR);
			return privateKey;
		}
		System.out.println("Check valid: " + lambda.mod(e));
		
	
		
		// extended Euclidean algorithm
	    GCD(e, lambda, result);
	    
	    d = result.getS();
	    privateKey.setD(result.getS());
	    privateKey.setN(p.multiply(q));
		    
	    System.out.println("Verif: " + d.multiply(e).mod(lambda));
	    privateKey.setStatus(PrivateKey.Status.OK);
		return privateKey;
	} 
		
	public static void GCD(
						BigInteger a, 
						BigInteger b, EuclidResult result) 
	{	
	/** affects d, s, t in POJO result such as the Bezout relation holds: 
	 * d = s * a + t * b
	 * 
	 */	
		BigInteger rt2 = a;
	    BigInteger rt1 = b;
	    BigInteger st1 = new BigInteger("0", 10);
	    BigInteger tt1 = new BigInteger("1", 10);
	    BigInteger st2 = new BigInteger("1", 10);
	    BigInteger tt2 = new BigInteger("0", 10);
	    BigInteger r, q, s, t; 
	    
	    BigInteger[] divRes = new BigInteger[2];
		
	    while (rt1 != BigInteger.ZERO) {
	    	divRes = rt2.divideAndRemainder(rt1);
	    	
	    	q = divRes[0];
	    	r = divRes[1];
	           	
	        s = st2.subtract(q.multiply(st1));
	        t = tt2.subtract(q.multiply(tt1));
	        	 
	        // save all
	        rt2 = rt1;
	        rt1 = r;
	        st2 = st1;
	        st1 = s;
	        tt2 = tt1;
	        tt1 = t;
	    }
	    
	    s = st2;
	    t = tt2;

	    result.setD(rt2);
	    result.setS(st2);
	    result.setT(tt2);
	
	}
	
	
	public static class EuclidResult {
		BigInteger d, s, t;
		
		public EuclidResult() {
			d = new BigInteger("0", 10);
			s = new BigInteger("0", 10);
			t = new BigInteger("0", 10);
		}
		
		public BigInteger getD() {
			return d;
		}

		public void setD(BigInteger d) {
			this.d = d;
		}

		public BigInteger getS() {
			return s;
		}

		public void setS(BigInteger s) {
			this.s = s;
		}

		public BigInteger getT() {
			return t;
		}

		public void setT(BigInteger t) {
			this.t = t;
		}
		
	}// class	
}
