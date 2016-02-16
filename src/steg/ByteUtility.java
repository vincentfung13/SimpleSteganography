package steg;

import java.io.File;

public class ByteUtility {
	/**
	 * Given a string, returns an array of binary string representing its byte array
	 */
	public static String[] getBinaryArray(String str) {
		byte[] byteArray = str.getBytes();
		String[] binaryArray = new String[byteArray.length];
		
		for (int i = 0; i < byteArray.length; i++) {
			String binaryString = Integer.toBinaryString(byteArray[i] & 255 | 256).substring(1);
			binaryArray[i] = binaryString;
		}
		
		return binaryArray;
	}
	
	/**
	 * Given an array of binary string, returns the original string
	 */
	public static String getOriginalString(String[] binaryArray) {
		byte[] byteArray = new byte[binaryArray.length];
		
		int recoveredVal;
		for (int i = 0; i < binaryArray.length; i++) {
			recoveredVal = Integer.parseInt(binaryArray[i], 2);
			byteArray[i] = (byte) recoveredVal;
		}
		
		return new String(byteArray);
	}
	
	/**
	 * Given a file, returns an array of binary string representing its byte array
	 */
	public static String[] getBinaryArrayOfFile(File file) {
		return null;
	}
	
	/**
	 * Given an binary, write the original file in the disk
	 */
	public static void writeFileToDisk(String[] binaryArray) {
		
	}
}
