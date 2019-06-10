package com.dub.spring.rsaEncryption;

import java.math.BigInteger;

// POJO

public class KeyInitMessage {
		
	private String p, q, e;

	public String getP() {
		return p;
	}

	public void setP(String p) {
		this.p = p;
	}

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}

	public String getE() {
		return e;
	}

	public void setE(String e) {
		this.e = e;
	}
	
	// convenience methods
	
	public BigInteger getBigP() {
		return new BigInteger(p, 10); 
	}
	
	public BigInteger getBigQ() {
		return new BigInteger(q, 10); 
	}
		
	public BigInteger getBigE() {
		return new BigInteger(e, 10); 
	}
	
}
