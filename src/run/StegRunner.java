package run;

import steg.ByteUtility;

public class StegRunner {
	public static void main(String[] args) {
		String str = "Hellooo world";
		
		String[] binaryStringArray = ByteUtility.getBinaryArray(str);
		
		for (int i = 0; i < binaryStringArray.length; i++) {
			System.out.println(binaryStringArray[i]);
		}
		
		System.out.println(ByteUtility.getOriginalString(binaryStringArray));
	}
}
