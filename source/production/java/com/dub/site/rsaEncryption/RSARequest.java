package com.dub.site.rsaEncryption;

	
// POJO
public class RSARequest {
	
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
		ENCRYPT, DECRYPT
	}
}
	