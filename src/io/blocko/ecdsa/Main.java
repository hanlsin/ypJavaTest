package io.blocko.ecdsa;

import java.nio.ByteBuffer;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;

import io.blocko.coinstack.ECDSA;
import io.blocko.coinstack.ECKey;

public class Main {
	private static void printBytes(byte[] bytes) throws Exception {
		for (int i = 0; i < bytes.length; i++) {
			System.out.print(" " + bytes[i]);
		}
		System.out.println();
	}

	private static void printBytes(String str) throws Exception {
		printBytes(str.getBytes("UTF-8"));
	}

	private static void coinstackECDSA() throws Exception {
		String privateKey = ECKey.createNewPrivateKey();
		String address = ECKey.deriveAddress(privateKey);
		String str = "12345TEST";
		String signature = ECDSA.signMessage(privateKey, str);
		System.out.println(signature);
		boolean b = ECDSA.verifyMessageSignature(address, str, signature);
		System.out.println(b);

		ByteBuffer bbuf = ByteBuffer.allocate(25);
		System.out.println(str.getBytes().length);
		printBytes(str.getBytes());
		bbuf.position(10);
		bbuf.put(str.getBytes(), 0, str.getBytes().length);
		printBytes(bbuf.array());
		byte[] bytes = bbuf.array();
		signature = ECDSA.signMessage(privateKey, new String(bytes));
		System.out.println(signature);
		b = ECDSA.verifyMessageSignature(address, new String(bytes), signature);
		System.out.println(b);
		bbuf = ByteBuffer.allocate(150);
		bbuf.put(bytes, 0, 25);
		bbuf.position(50);
		bytes = signature.getBytes();
		bbuf.put(bytes, 0, bytes.length);
		printBytes(bbuf.array());
		b = ECDSA.verifyMessageSignature(address, new String(bbuf.array(), 0, 25), new String(bbuf.array(), 50, 100));
		System.out.println(b);
		signature = new String(bbuf.array(), 50, bytes.length);
		System.out.println(signature);
		b = ECDSA.verifyMessageSignature(address, new String(bbuf.array(), 0, 25), signature);
		System.out.println(b);
	}

	private static void normalECDSA(PrivateKey priv, PublicKey pub) throws Exception {
		String tmp;
		String str;
		byte[] strBytes;

		Signature dsa = Signature.getInstance("SHA1withECDSA");
		dsa.initSign(priv);

		str = "12345TEST";
		System.out.println("Message: " + str);
		printBytes(str);

		strBytes = str.getBytes("UTF-8");
		dsa.update(strBytes);
		byte[] signed = dsa.sign();
		System.out.println("Signed: " + signed);
		tmp = new String(signed);
		System.out.println("Signed Str: " + tmp);
		printBytes(str);
	}

	public static void main(String[] args) {
		try {
			KeyPairGenerator kpg = KeyPairGenerator.getInstance("EC");
			SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
			PrivateKey privateKey;
			PublicKey publicKey;

			kpg.initialize(256, sr);
			KeyPair pair = kpg.generateKeyPair();

			privateKey = pair.getPrivate();
			System.out.println("Private Key: " + privateKey);
			System.out.println("ENC:");
			printBytes(privateKey.getEncoded());

			publicKey = pair.getPublic();
			System.out.println("Public Key: " + publicKey);
			System.out.println("ENC:");
			printBytes(publicKey.getEncoded());

			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

			normalECDSA(privateKey, publicKey);
			System.out.println("---------------------------------------------------------");
			coinstackECDSA();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
