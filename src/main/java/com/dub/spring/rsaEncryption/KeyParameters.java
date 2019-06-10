package com.dub.spring.rsaEncryption;

import java.math.BigInteger;

	
// POJO

public class KeyParameters {
		
	private BigInteger p, q, e;
		
	public KeyParameters() {
		p = new BigInteger("0", 10);
		q = new BigInteger("0", 10);
		e = new BigInteger("0", 10);
	}

	public BigInteger getP() {
		return p;
	}

	public void setP(BigInteger p) {
		this.p = p;
	}

	public BigInteger getQ() {
		return q;
	}

	public void setQ(BigInteger q) {
		this.q = q;
	}

	public BigInteger getE() {
		return e;
	}

	public void setE(BigInteger e) {
		this.e = e;
	}

	
}
