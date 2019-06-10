package com.dub.spring.rsaEncryption;

import java.math.BigInteger;

// POJO

public class PrivateKeyResponse {
		
	private String n, d;
	private Status status;
	
	public String getN() {
		return n;
	}

	public void setN(String n) {
		this.n = n;
	}

	public String getD() {
		return d;
	}

	public void setD(String d) {
		this.d = d;
	}
	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	// convenience methods
	
	public void setBigN(BigInteger n) {
		this.n = n.toString(10);
	}
	
	public void setBigD(BigInteger d) {
		this.d = d.toString(10);
	}
	


	public static enum Status {		
		OK, NOT_COPRIME
	}
		
	
}
