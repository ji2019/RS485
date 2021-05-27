package com.iw2f.netty.utils;

public class U {

	/**
	 * 
	 * 字节转十六进制
	 * 
	 * @param b 需要进行转换的byte字节
	 * @return 转换后的Hex字符串
	 * 
	 */
	public static String byteToHex(byte b) {
		String hex = Integer.toHexString(b & 0xFF);
		if (hex.length() < 2) {
			hex = "0" + hex;
		}
		return hex;
	}

	/**
	 * 
	 * 字节转十进制int OK
	 * 
	 * @param b
	 * @return int
	 * 
	 */
	public static int byteToInt(byte b) {
		return (b >= 0) ? b : (b + 256);
	}

	/**
	 * 字节数组转16进制
	 * 
	 * @param bytes 需要转换的byte数组
	 * 
	 * @return 转换后的Hex字符串
	 */
	public static String bytesToHex(byte[] bytes) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(bytes[i] & 0xFF);
			if (hex.length() < 2) {
				sb.append(0);
			}
			sb.append(hex);
		}
		return sb.toString();
	}

	/**
	 * Hex字符串转byte
	 * 
	 * @param inHex 待转换的Hex字符串
	 * @return 转换后的byte
	 */
	public static byte hexToByte(String inHex) {
		return (byte) Integer.parseInt(inHex, 16);
	}

	/**
	 * byte[]转int
	 * 
	 * @param bytes
	 * @return
	 */
	public static int byteArrayToInt(byte[] bytes) {
		int value = 0;
		// 由高位到低位
		for (int i = 0; i < bytes.length; i++) {
			int shift = (bytes.length - 1 - i) * 8;
			value += (bytes[i] & 0x000000FF) << shift;// 往高位游
		}
		return value;
	}

	/**
	 * 从一个byte[]数组中截取一部分
	 * 
	 * @param src
	 * @param begin
	 * @param count
	 * @return
	 */
	public static byte[] subBytes(byte[] req, int begin, int count) {
		byte[] bs = new byte[count];
		for (int i = begin; i < begin + count; i++)
			bs[i - begin] = req[i];
		return bs;
	}

	public static byte subByte(byte[] req, int begin) {
		return req[begin];
	}

}