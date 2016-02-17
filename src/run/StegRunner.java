package run;

import java.io.IOException;

import steg.Steg;

public class StegRunner {
	public static void main(String[] args) throws IOException {
		
//		byte two = -40;
//		int[] array = ByteUtility.getBits(two);
//		System.out.println(Arrays.toString(array));
		
		Steg steg = new Steg();
		System.out.println(steg.hideFile("test.txt", "minions_freeze.bmp"));
		System.out.println(steg.extractFile("stego_image_file.bmp"));
//		System.out.println(steg.hideString("How you doooiinnng?", "minions_freeze.bmp"));
//		System.out.println(steg.extractString("stego_image_string.bmp"));
	}
}
