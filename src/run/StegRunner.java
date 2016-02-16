package run;

import java.io.IOException;
import java.util.Arrays;

import steg.RGBUtility;
import steg.Steg;

public class StegRunner {
	public static void main(String[] args) throws IOException {
		Steg steg = new Steg();
//		String str = "Hellloo world";
//		byte[] byteArray = str.getBytes();
//		System.out.println(Arrays.toString(byteArray));
		
		System.out.println(steg.hideString("Hellllooo world", "minions_freeze.bmp"));
//		String[] byteArray = ByteUtility.getBinaryArray("Hellllooo world");
//		for (int i = 0; i < byteArray.length; i++) {
//			System.out.println(byteArray[i]);
//		}
	}
}
