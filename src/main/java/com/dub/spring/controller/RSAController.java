package com.dub.spring.controller;



import java.math.BigInteger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dub.spring.rsaEncryption.KeyInitMessage;
import com.dub.spring.rsaEncryption.PrivateKey;
import com.dub.spring.rsaEncryption.PrivateKeyResponse;
import com.dub.spring.rsaEncryption.PublicKey;
import com.dub.spring.rsaEncryption.RSARequest;
import com.dub.spring.rsaEncryption.RSAResponse;
import com.dub.spring.rsaEncryption.RSAUtils;


@Controller
public class RSAController {


	@RequestMapping(value="/dKeyGen")
	@ResponseBody
	public PrivateKeyResponse dKeyGen(
				@RequestBody KeyInitMessage keyParams, 
				HttpServletRequest request) 
	{						
		BigInteger p = keyParams.getBigP();
		BigInteger q = keyParams.getBigQ();
		BigInteger e = keyParams.getBigE();
			
		PrivateKey privateKey;
		
		PublicKey publicKey = new PublicKey();
			
		privateKey = RSAUtils.dKeyGen(p, q, e);
		
		PrivateKeyResponse privateKeyResponse = new PrivateKeyResponse();
		
		if (!privateKey.getStatus().equals(PrivateKey.Status.OK)) {
			privateKeyResponse.setStatus(PrivateKeyResponse.Status.NOT_COPRIME);
		} else {
			publicKey.setE(e);
			publicKey.setN(privateKey.getN());
			
			privateKeyResponse.setBigD(privateKey.getD());
			privateKeyResponse.setBigN(privateKey.getN());
			
			HttpSession session = request.getSession();
			session.setAttribute("publicKey", publicKey);
			session.setAttribute("privateKey", privateKey);
			privateKeyResponse.setStatus(PrivateKeyResponse.Status.OK);
		}
		return privateKeyResponse;
	}

	
	@RequestMapping(value="/rsaProcess")
	@ResponseBody
	public RSAResponse encrypt(
				@RequestBody RSARequest rsaRequest, 
				HttpServletRequest request) 
	{
	
		HttpSession session = request.getSession();
		
		RSAResponse rsaResponse = new RSAResponse();
		
		RSARequest.Type type = rsaRequest.getType();
		String message = rsaRequest.getMessage();
		
		if (type.equals(RSARequest.Type.ENCRYPT)) {
			// use public key to encrypt
			PublicKey key = (PublicKey)session.getAttribute("publicKey");
				
			BigInteger exponent = key.getE();
			BigInteger modulus = key.getN();
			
			BigInteger msg = new BigInteger(message, 10);
			
			// preliminary check 
			if (msg.compareTo(modulus) != -1) {
				rsaResponse.setType(RSAResponse.Type.TOOLONG);
			} else {
			
				BigInteger msg2 = msg.modPow(exponent, modulus);
			
				rsaResponse.setType(RSAResponse.Type.ENCRYPTED);
				rsaResponse.setMessage(msg2.toString(10));
			}
		} else if (type.equals(RSARequest.Type.DECRYPT)) {
			// use private key to decrypt
			PrivateKey key = (PrivateKey)session.getAttribute("privateKey");
					
			BigInteger exponent = key.getD();
			BigInteger modulus = key.getN();
			
			BigInteger msg = new BigInteger(message, 10);
			
			if (msg.compareTo(modulus) != -1) {
				
				rsaResponse.setType(RSAResponse.Type.TOOLONG);
			} else {
				BigInteger msg2 = msg.modPow(exponent, modulus);
			
				rsaResponse.setType(RSAResponse.Type.DECRYPTED);
				rsaResponse.setMessage(msg2.toString(10));
			}
		}
		return rsaResponse;
	
	}
	
}
	