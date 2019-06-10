package com.dub.spring.rsaEncryption;

	
// POJO
public class RSAResponse {
	
	private Type type;	
	private String message;
	
	
	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
	public static enum Type {		
		ENCRYPTED, DECRYPTED, TOOLONG
	}
}
	