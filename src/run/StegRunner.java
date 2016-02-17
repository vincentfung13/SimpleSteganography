package run;

import java.io.IOException;
import steg.Steg;

public class StegRunner {
	public static void main(String[] args) throws IOException {
		Steg steg = new Steg();
		System.out.println(steg.hideString("How you doooiinnng?", "minions_freeze.bmp"));
		System.out.println(steg.extractString("stego_image.bmp"));
	}
}
