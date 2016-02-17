package steg;

import java.util.List;

public class ByteUtility {
	
	/**
	 * Convert a byte to an array of its bits with size 8
	 * @param byteNum
	 * @return
	 */
	public static int[] getBits(byte byteNum) {
		int[] result = new int[8];
		
		for (int i = 0; i < result.length; i++) {
			result[i] = byteNum >> 7 - i & 1;
		}
		
		return result;
	}
	
	/**
	 * Given the string the hide, return a byte array consist of the size followed by the payload in bytes
	 * @param payload: string to hide
	 * @return array of bytes to hide
	 */
	public static byte[] getBytesToHide(String payload) {
		byte[] payloadInBytes = payload.getBytes();
		
		int numOfBits = payloadInBytes.length * 8;
		byte[] sizeInBytes = new byte[] {
				(byte) (numOfBits >>> 24), 
				(byte) (numOfBits >>> 16), 
				(byte) (numOfBits >>> 8), 
				(byte) (numOfBits)
		};
		
		byte[] bytesToHide = new byte[sizeInBytes.length + payloadInBytes.length];
		for (int i = 0; i < sizeInBytes.length; i++) {
			bytesToHide[i] = sizeInBytes[i];
		}
		
		for (int i = sizeInBytes.length; i < bytesToHide.length; i++) {
			bytesToHide[i] = payloadInBytes[i - sizeInBytes.length];
		}
		
		return bytesToHide;
	}
	
	/**
	 * Return an array of bytes given a bit buffer
	 * @param bitBuffer
	 * @return
	 */
	public static byte[] getByteArrayFromBuffer(List<String> bitBuffer) {
		StringBuilder sb = new StringBuilder();
		for (int k = 0; k < bitBuffer.size(); k++) {
			sb.append(bitBuffer.get(k));
		}
		
		String resultBitString = sb.toString();
		byte[] resultByteArray = new byte[bitBuffer.size()/8];
		for (int k = 0; k < bitBuffer.size(); k+=8) {
			resultByteArray[k/8] = Byte.parseByte(resultBitString.substring (k, k + 8), 2);
		}
		
		return resultByteArray;
	}
	
	/**
	 * Convert a four byte array to an integer
	 * @param byteArray
	 * @return
	 */
	public static int getBitNum(byte[] byteArray) {
		return (byteArray[0] << 24) | (byteArray[1] << 16) | (byteArray[2] << 8) | byteArray[3];
	}
	
	/**
	 * @param pixel
	 * @return a map from rgb colour to its binary string for that pixel
	 */
	public static byte[] getRGBFromPixel(int pixel) {
		byte r = (byte) ((pixel >> 16) & 0xff);
		byte g = (byte) ((pixel >> 8) & 0xff);
		byte b = (byte) ((pixel) & 0xff);
		
		return new byte[] {r, g, b};
	}
	
	/**
	 * @param rgb: a map from rgb colour to its binary string for a pixel
	 * @return the rgb value as a pixel
	 */
	public static int getPixel(byte[] rgb) {	
		return ((rgb[0] & 0x0ff) << 16) | ((rgb[1] & 0x0ff) << 8) | (rgb[2] & 0x0ff);
	}
}
