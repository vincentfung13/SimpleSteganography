package run;

import java.io.IOException;

import steg.Steg;

public class StegRunner {
	public static void main(String[] args) throws IOException {
		System.out.println(Steg.hideFile("test.txt", "minions_freeze.bmp"));
		System.out.println(Steg.extractFile("stego_image_file.bmp"));
		System.out.println(Steg.hideString("How you doooiinnng?", "minions_freeze.bmp"));
		System.out.println(Steg.extractString("stego_image_string.bmp"));
	}
}
