package com.dub.spring.rsaEncryption;

import java.math.BigInteger;

// POJO
public class RSARequest {
	
	private String type;		
	private String message;
	private BigInteger E;		
	private BigInteger N;
	private BigInteger d;

	public BigInteger getD() {
		return d;
	}

	public void setD(BigInteger d) {
		this.d = d;
	}

	public BigInteger getE() {
		return E;
	}

	public void setE(BigInteger e) {
		E = e;
	}

	public BigInteger getN() {
		return N;
	}

	public void setN(BigInteger n) {
		N = n;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
	