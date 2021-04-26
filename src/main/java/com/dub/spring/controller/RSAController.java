package com.dub.spring.controller;

import java.math.BigInteger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin(origins = "http://localhost:3000")
public class RSAController {

	// Handle String
	@RequestMapping(value = "/dKeyGen")
	@ResponseBody
	public PrivateKeyResponse dKeyGen(@RequestBody KeyInitMessage keyParams, HttpServletRequest request) {
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

	@RequestMapping(value = "/rsaProcess")
	@ResponseBody
	public RSAResponse encrypt(@RequestBody RSARequest rsaRequest) {
		System.out.print(rsaRequest);

		RSAResponse rsaResponse = new RSAResponse();
		String message = rsaRequest.getMessage();
		String type = rsaRequest.getType();
		BigInteger KeyU = rsaRequest.getE();
		BigInteger N = rsaRequest.getN();
		BigInteger KeyR = rsaRequest.getD();
		System.out.println(message);

		byte[] messageToASCII = message.getBytes();

		if (type.equals("ENCRYPT")) {
			// Kiểm tra loại tin nhắn

			String messageConverted = "";
			String temp = "";
			for (int i = 0; i < messageToASCII.length; i++) {
				System.out.println(messageToASCII[i]);
				temp += messageToASCII[i];
				BigInteger msgSplit = new BigInteger(temp);
				// Kiểm tra độ rộng của message
				if (msgSplit.compareTo(N) != -1) {
					rsaResponse.setType("TOOLONG");
					// Nếu vượt qua N thì return TOOLONG
				} else {
					BigInteger msgSplitEn = msgSplit.modPow(KeyU, N); // ( msgSplit ^ e ) mod N
					messageConverted += msgSplitEn + "%-707-#";
					System.out.println("Message cut and Encrypt :" + msgSplitEn);
				}
				temp = "";
			}
			System.out.println(messageConverted);
			rsaResponse.setType("ENCRYPTED");
			rsaResponse.setMessage(messageConverted);
		} else if (type.equals("DECRYPT")) {
			// Dùng khóa bí mật để giải mã

			String[] listKey = message.split("%-707-#");
			String msgDecrypt = "";
			for (String item : listKey) {
				BigInteger msg = new BigInteger(item, 10);
				System.out.println(item);
				// Decrypt
				BigInteger msg2 = msg.modPow(KeyR, N);
				System.out.println("message decrypt:" + msg2);
				// ---------------------------
				String msgChar = msg2.toString(10);
				System.out.println("message char decrypt:" + msgDecrypt);
				char msgCharFull = (char) Integer.parseInt(msgChar);
				msgDecrypt += msgCharFull;
				System.out.println("message cut:" + msgDecrypt);

				rsaResponse.setType("DECRYPTED");
				rsaResponse.setMessage(msgDecrypt);
			}
		}
		return rsaResponse;

	}
	// Hanle Number

	@RequestMapping(value = "/dKeyGenNum")
	@ResponseBody
	public PrivateKeyResponse dKeyGenNum(@RequestBody KeyInitMessage keyParams, HttpServletRequest request) {
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

	@RequestMapping(value = "/rsaProcessNum")
	@ResponseBody
	public RSAResponse encryptNum(@RequestBody RSARequest rsaRequest) {
		System.out.print(rsaRequest);

		RSAResponse rsaResponse = new RSAResponse();
		String message = rsaRequest.getMessage();
		String type = rsaRequest.getType();
		BigInteger KeyU = rsaRequest.getE();
		BigInteger N = rsaRequest.getN();
		BigInteger KeyR = rsaRequest.getD();
		System.out.println(message);
		if (type.equals("ENCRYPT")) {
			// Kiểm tra loại tin nhắn
				BigInteger msg = new BigInteger(message);
				// Kiểm tra độ rộng của message
				if (msg.compareTo(N) != -1) {
					rsaResponse.setType("TOOLONG");
					// Nếu vượt qua N thì return TOOLONG
				} else {
					BigInteger msgSEn = msg.modPow(KeyU, N); // ( msgSplit ^ e ) mod N
					System.out.println(msgSEn);
					rsaResponse.setType("ENCRYPTED");
					rsaResponse.setMessage(msgSEn.toString(10));
				}
		} else if (type.equals("DECRYPT")) {
			// Dùng khóa bí mật để giải mã
				BigInteger msg = new BigInteger(message);
				// Decrypt
				BigInteger msg2 = msg.modPow(KeyR, N);
				System.out.println("message decrypt:" + msg2);
				// ---------------------------
				rsaResponse.setType("DECRYPTED");
				rsaResponse.setMessage(msg2.toString(10));
			}
		return rsaResponse;

	}

}
