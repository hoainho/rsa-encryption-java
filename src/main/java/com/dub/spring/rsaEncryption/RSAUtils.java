package com.dub.spring.rsaEncryption;

import java.math.BigInteger;



public class RSAUtils {
	
	public static PrivateKey dKeyGen(
								BigInteger p, 
								BigInteger q,
								BigInteger e) 
	{
		BigInteger pTru1 = p.subtract(BigInteger.ONE); // p - 1 
		BigInteger qTru1 = q.subtract(BigInteger.ONE); // q - 1
			
		BigInteger lambda, d; 
		
		EuclidResult result = new EuclidResult();
		//Tìm ước chung lớn nhất của e và lambda N
		GCD(pTru1, qTru1, result);
		//result = (p - 1) * (q - 1)
//		lambda = pTru1.multiply(qTru1).divide(result.getD());// LCM(p-1, q-1) // bội số chung của 2 số 
		lambda = pTru1.multiply(qTru1);
		System.out.println(" lamda: " +lambda);
		PrivateKey privateKey = new PrivateKey();
		// Tìm E nếu E sai thì báo lỗi
		if (lambda.mod(e) == BigInteger.ZERO) {// invalid parameter set
			privateKey.setStatus(PrivateKey.Status.ERROR);
			return privateKey;
		}
//		System.out.println("Check valid: " + lambda.mod(e));	
		
	
		//Tìm ước chung lớn nhất của e và lambda N
	    GCD(e, lambda, result);
	    
	    d = result.getS();
	    privateKey.setD(result.getS());
	    privateKey.setN(p.multiply(q));
		    
	    System.out.println("Verif: " + d.multiply(e).mod(lambda));
	    privateKey.setStatus(PrivateKey.Status.OK);
	    System.out.println("Private KEY: " + d);
		return privateKey;
	}
	//Tìm ước chung lớn nhất của a và lambda b lưu vào result
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
