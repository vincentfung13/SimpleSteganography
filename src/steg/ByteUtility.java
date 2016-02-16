package steg;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

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
	
	/**
	 * @param pixel
	 * @return a map from rgb colour to its binary string for that pixel
	 */
	public static Map<String, String> getRGBFromPixel(int pixel) {
		HashMap<String, String> rgb = new HashMap<String, String>();
		
		int r = (pixel >> 16) & 0xff;
		int g = (pixel >> 8) & 0xff;
		int b = (pixel) & 0xff;
		
		rgb.put("r", Integer.toBinaryString(r));
		rgb.put("g", Integer.toBinaryString(g));
		rgb.put("b", Integer.toBinaryString(b));
		
		return rgb;
	}
	
	/**
	 * @param rgb: a map from rgb colour to its binary string for a pixel
	 * @return the rgb value as a pixel
	 */
	public static int getPixel(Map<String, String> rgb) {
		int r = Integer.parseInt(rgb.get("r"), 2);
		int g = Integer.parseInt(rgb.get("g"), 2);
		int b = Integer.parseInt(rgb.get("b"), 2);
		
		return ((r & 0x0ff) << 16) | ((g & 0x0ff) << 8) | (b & 0x0ff);
	}
}
