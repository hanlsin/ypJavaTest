package io.blocko.hex;

import java.nio.ByteBuffer;

import org.apache.commons.codec.binary.Hex;

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

	public static void main(String[] args) throws Throwable {
		StringBuffer hexBuf = new StringBuffer();
		hexBuf.append("4f41");
		hexBuf.append("0000");
		hexBuf.append("0000");

		System.out.println(hexBuf.toString());
		printBytes(hexBuf.toString());
		byte[] hex = Hex.decodeHex(hexBuf.toString().toCharArray());
		printBytes(hex);

		byte[] mhex = new byte[32];
		mhex[0] = 0x4f;
		mhex[1] = 0x41;
		mhex[2] = 0x00;
		mhex[3] = 0x00;
		mhex[4] = 0x00;
		mhex[5] = 0x00;
		printBytes(mhex);

		ByteBuffer bbuf = ByteBuffer.allocate(32);
		bbuf.put((byte) 0x4f);
		bbuf.put((byte) 0x41);
		bbuf.put((byte) 0x00);
		bbuf.put((byte) 0x00);
		bbuf.put((byte) 0x00);
		bbuf.put((byte) 0x00);
		System.out.println(bbuf.toString());
		printBytes(bbuf.array());

		System.out.println("\n-----------------------");
		System.out.println("APPEND ABCD");
		printBytes("ABCD".getBytes());

		System.out.println(">>>> SB1");
		StringBuffer sb1 = new StringBuffer(hexBuf.toString());
		sb1.append("ABCD");
		printBytes(sb1.toString());
		printBytes(Hex.decodeHex(sb1.toString().toCharArray()));

		System.out.println(">>>> SB2");
		StringBuffer sb2 = new StringBuffer(hexBuf.toString());
		sb2.append(Hex.encodeHex("ABCD".getBytes()));
		printBytes(sb2.toString());
		printBytes(Hex.decodeHex(sb2.toString().toCharArray()));

		System.out.println(">>>> BBuf");
		ByteBuffer bbuf2 = ByteBuffer.allocate(bbuf.capacity());
		bbuf2.put(bbuf.array(), 0, bbuf.position());
		System.out.println(bbuf2.toString());
		printBytes(bbuf2.array());
		bbuf2.put("ABCD".getBytes());
		System.out.println(bbuf2.toString());
		printBytes(bbuf2.array());

		System.out.println("\n-----------------------");
		System.out.println("APPEND 9 8 16");
		printBytes(Integer.toHexString(9).getBytes());

		System.out.println(">>>> SB1");
		sb1 = new StringBuffer(hexBuf.toString());
		sb1.append(9);
		sb1.append(8);
		sb1.append(16);
		printBytes(sb1.toString());
		printBytes(Hex.decodeHex(sb1.toString().toCharArray()));

		System.out.println(">>>> SB2");
		sb2 = new StringBuffer(hexBuf.toString());
		sb2.append(Integer.toHexString(9));
		sb2.append(Integer.toHexString(8));
		sb2.append(Integer.toHexString(16));
		printBytes(sb2.toString());
		printBytes(Hex.decodeHex(sb2.toString().toCharArray()));

		System.out.println(">>>> BBuf");
		bbuf2 = ByteBuffer.allocate(bbuf.capacity());
		bbuf2.put(bbuf.array(), 0, bbuf.position());
		System.out.println(bbuf2.toString());
		printBytes(bbuf2.array());
		bbuf2.putShort((short) 9);
		bbuf2.putShort((short) 8);
		bbuf2.putShort((short) 16);
		System.out.println(bbuf2.toString());
		printBytes(bbuf2.array());

		System.out.println("\n-----------------------");
		System.out.println("CREATE Auto Transfer Document.");
		
		ByteBuffer byteBuf = null;
		String privateKey = ECKey.createNewPrivateKey();
		String address = ECKey.deriveAddress(privateKey);
		String signed = null;
		byte[] tmp = null;

		// generate document
		tmp = generateDocument();
		printBytes(tmp);
		// SIG_COM: 문서에 대한 기업 서명 100 bytes
		signed = ECDSA.signMessage(privateKey, new String(tmp));
		System.out.println(signed);
		printBytes(signed);
		byteBuf = ByteBuffer.allocate(150);
		byteBuf.put(tmp);
		byteBuf.put(convertStringToFixedSizeBytes(signed, 100));
		System.out.println(byteBuf.toString());
		printBytes(byteBuf.array());

		System.out.println("\n-----------------------");
		byte[] bytes = byteBuf.array();
		String str = null;
		byteBuf = ByteBuffer.wrap(bytes);
		System.out.println(byteBuf.get());
		System.out.println(byteBuf.get());
		System.out.println(byteBuf.get());
		System.out.println(byteBuf.get());
		System.out.println(byteBuf.get());
		System.out.println(byteBuf.get());
		// 기업 코드
		tmp = new byte[5];
		for (int i = 0; i < 5; i++) {
			tmp[i] = byteBuf.get();
		}
		System.out.println(new String(tmp));
		printBytes(tmp);
		str = convertFixedSizeBytesToString(tmp, 0, 5);
		System.out.println(str);
		printBytes(str);
		str = convertFixedSizeBytesToString(bytes, 6, 5);
		System.out.println(str);
		printBytes(str);
		// 고객 이름
		tmp = new byte[10];
		for (int i = 0; i < 10; i++) {
			tmp[i] = byteBuf.get();
		}
		System.out.println(new String(tmp));
		printBytes(tmp);
		str = convertFixedSizeBytesToString(tmp, 0, 10);
		System.out.println(str);
		printBytes(str);
		// 은행 코드
		tmp = new byte[3];
		for (int i = 0; i < 3; i++) {
			tmp[i] = byteBuf.get();
		}
		System.out.println(new String(tmp));
		printBytes(tmp);
		str = convertFixedSizeBytesToString(tmp, 0, 3);
		System.out.println(str);
		printBytes(str);
		// 계좌 번호
		tmp = new byte[20];
		for (int i = 0; i < 20; i++) {
			tmp[i] = byteBuf.get();
		}
		System.out.println(new String(tmp));
		printBytes(tmp);
		str = convertFixedSizeBytesToString(tmp, 0, 20);
		System.out.println(str);
		printBytes(str);
		// DATE
		System.out.println(byteBuf.getShort());
		int i = (int) (bytes[44] << 8 | bytes[45]);
		System.out.println(i);
		// Start  month 
		System.out.println(byteBuf.getShort());
		i = (int) (bytes[46] << 8 | bytes[47]);
		System.out.println(i);
		// End month 
		System.out.println(byteBuf.getShort());
		i = (int) (bytes[48] << 8 | bytes[49]);
		System.out.println(i);

		System.out.println("\n-----------------------");
		bytes = byteBuf.array();
		// verify sign
		System.out.println(signed);
		str = new String(bytes, 0, 50);
		printBytes(str);
		signed = ECDSA.signMessage(privateKey, str);
		System.out.println(signed);
		printBytes(signed);
		boolean b = ECDSA.verifyMessageSignature(address, str, signed);
		System.out.println(b);
		signed = convertFixedSizeBytesToString(bytes, 50, 100);
		System.out.println(signed);
		printBytes(signed);
		b = ECDSA.verifyMessageSignature(address, str, signed);
		System.out.println(b);

		System.out.println("\n-----------------------");
	}
	
	private static byte[] generateDocument() {
		String companyCode = "12";
		String custName = "yp123456789";
		String bankCode = "93";
		String account = "987654321012345";
		int date = 25;
		int startMonth = 3;
		int endMonth = 9;
		byte[] tmp = null;

		ByteBuffer byteBuf = ByteBuffer.allocate(50);
		// MAGIC_BYTE: 금융보안원 코드 2 bytes: (4f41)
		byteBuf.put((byte) 0x4f);
		byteBuf.put((byte) 0x41);
		// BIZ_CODE: 업무 코드: 출금이체 2 bytes: (0000)
		byteBuf.put((byte) 0x00);
		byteBuf.put((byte) 0x00);
		// OP_CODE: 수행 코드: 출금이체등록 2 bytes: (0000)
		byteBuf.put((byte) 0x00);
		byteBuf.put((byte) 0x00);
		// COM_CODE: 기업 코드 5 bytes
		tmp = convertStringToFixedSizeBytes(companyCode, 5);
		byteBuf.put(tmp);
		// CUST_NAME: 고객 이름 10 bytes
		tmp = convertStringToFixedSizeBytes(custName, 10);
		byteBuf.put(tmp);
		// BANK_CODE: 은행 코드 3 bytes
		tmp = convertStringToFixedSizeBytes(bankCode, 3);
		byteBuf.put(tmp);
		// ACC_NUMBER: 계좌 번호 20 bytes
		tmp = convertStringToFixedSizeBytes(account, 20);
		byteBuf.put(tmp);
		// DATE: 이체 날짜 (매월) 2 bytes
		byteBuf.putShort((short) date);
		// START_MON: 이체 시작 월 2 bytes
		byteBuf.putShort((short) startMonth);
		// END_MON: 이체 끝 월 2 bytes
		byteBuf.putShort((short) endMonth);
		
		return byteBuf.array();
	}

	public static byte[] convertStringToFixedSizeBytes(String str, int size) {
		byte[] strBytes = str.getBytes();
		int strLen = strBytes.length;
		if (strLen == size) {
			return strBytes;
		} else if (strLen > size) {
			strLen = size;
		}

		int diff = size - strLen;
		ByteBuffer bbuf = ByteBuffer.allocate(size);
		bbuf.position(diff);
		bbuf.put(strBytes, 0, strLen);
		return bbuf.array();
	}

	public static String convertFixedSizeBytesToString(byte[] bytes, int offset, int length) {
		ByteBuffer bbuf = ByteBuffer.allocate(length);
		boolean isStarted = false;
		for (int i = offset; i < offset + length; i++) {
			if (!isStarted && bytes[i] != 0) {
				isStarted = true;
			}
			if (isStarted) {
				if (bytes[i] == 0) {
					break;
				}
				bbuf.put(bytes[i]);
			}
		}
		return new String(bbuf.array(), 0, bbuf.position());
	}

}
