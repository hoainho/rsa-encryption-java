package com.dub.spring.rsaEncryption;

import java.io.Serializable;
import java.math.BigInteger;

	
// POJO
public class PrivateKey implements Serializable {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigInteger n, d;
	private Status Status;
		
	public PrivateKey() {
		n = new BigInteger("0", 10);
		d = new BigInteger("0", 10);
	}

	public BigInteger getN() {
		return n;
	}

	public void setN(BigInteger n) {
		this.n = n;
	}

	public BigInteger getD() {
		return d;
	}

	public void setD(BigInteger d) {
		this.d = d;
	}
	
	public Status getStatus() {
		return Status;
	}

	public void setStatus(Status status) {
		Status = status;
	}



	public static enum Status {
		OK, ERROR
	}
}
