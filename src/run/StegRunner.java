package run;

import steg.ByteUtility;
import steg.Steg;

public class StegRunner {
	public static void main(String[] args) {
		Steg steg = new Steg();
		System.out.println(steg.hideString("Hellllooo world", "minions_freeze.bmp"));
//		String[] byteArray = ByteUtility.getBinaryArray("Hellllooo world");
//		for (int i = 0; i < byteArray.length; i++) {
//			System.out.println(byteArray[i]);
//		}
	}
}
