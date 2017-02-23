package com.dub.site.rsaEncryption;

import java.io.Serializable;
import java.math.BigInteger;

	
// POJO

public class PublicKey implements Serializable {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigInteger n, e;
		
	public PublicKey() {
		n = new BigInteger("0", 10);
		e = new BigInteger("0", 10);
	}

	public BigInteger getN() {
		return n;
	}

	public void setN(BigInteger n) {
		this.n = n;
	}

	public BigInteger getE() {
		return e;
	}

	public void setE(BigInteger e) {
		this.e = e;
	}
}
