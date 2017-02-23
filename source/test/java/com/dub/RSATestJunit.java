package com.dub;

import static org.junit.Assert.*;

import java.math.BigInteger;

import org.junit.Test;

import com.dub.site.rsaEncryption.PrivateKey;
import com.dub.site.rsaEncryption.RSAUtils;

public class RSATestJunit {

	@Test
	public void testAdd() {
		
		BigInteger p = new BigInteger("36064141637732512937");
		BigInteger q = new BigInteger("94512646076867083291");
		BigInteger e = new BigInteger("65537");
		
		PrivateKey privateKey = RSAUtils.dKeyGen(p, q, e);
		
		BigInteger d = privateKey.getD();
		
		BigInteger n = privateKey.getN();
		
		assertEquals("3408517454673018616991107522307114035667", n.toString(10));
		
		assertEquals("30945387880593345393167459405870669873", d.toString(10));
		
		BigInteger msg = new BigInteger("123456700009876543");
		
		BigInteger msgE = msg.modPow(e, n);
		
		BigInteger msgD = msgE.modPow(d, n);
		
		assertEquals(msg, msgD);
		
   }
}