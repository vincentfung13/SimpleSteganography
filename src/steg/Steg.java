package steg;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

import javax.imageio.ImageIO;

public class Steg {
	
	// A constant to hold the number of bits per byte
	private final int byteLength = 8;
	// A constant to hold the number of bits used to store the size of the file extracted
	protected final int sizeBitsLength = 32;
	// A constant to hold the number of bits used to store the extension of the file extracted
	protected final int extBitsLength = 64;
	
	/**
	 * Default constructor to create a steg object
	 */
	public Steg() {
	
	}
	
	/**
	 * A method for hiding a string in an uncompressed image file such as a .bmp or .png
	 * You can assume a .bmp will be used
	 * @param cover_filename - the filename of the cover image as a string 
	 * @param payload - the string which should be hidden in the cover image.
	 * @return a string which either contains 'Fail' or the name of the stego image which has been 
	 * written out as a result of the successful hiding operation. 
	 * You can assume that the images are all in the same directory as the java files
	*/
	//TODO you must write this method
	public String hideString(String payload, String cover_filename) {
		BufferedImage img;
		String result = "Fail";
		try {
			img = ImageIO.read(new File(cover_filename));
			int w = img.getWidth();
			int h = img.getHeight();
			
			// Convert the payload to string of bits
			String[] binaryArray = ByteUtility.getBinaryArray(payload);
			StringBuilder sb = new StringBuilder();
			for (String str: binaryArray) {
				sb.append(str);
			}
			char[] wholeBytes = sb.toString().toCharArray();
			
			// Check if the string is too long to hide
			if (w * h < wholeBytes.length) {
				return result;
			}
			
			// Flipping bit in the read-in image
			Map<String, String> rgb;
			for (int i = 0; i < w; i++) {
				for (int j = 0; j < h; j++) {
					// Check if we have hidden the whole string
					if ((i * h + j) > wholeBytes.length - 1)
						break;
					
					int pixel = img.getRGB(i, j);
					rgb = ByteUtility.getRGBFromPixel(pixel);
					
					char[] r = rgb.get("r").toCharArray();
					if (r[r.length - 1] != wholeBytes[i * h + j]) {
						r[r.length - 1] = wholeBytes[i * h + j];
						rgb.put("r", new String(r));
						img.setRGB(i, j, ByteUtility.getPixel(rgb));
					}
				}
			}
			
			// Write the result to disk
			String stegoImageName = "stego_image.bmp";
			ImageIO.write(img, "bmp", new File(stegoImageName));
			result = stegoImageName;
		} catch (IOException e) {
			result = "Fail";
		}
				
		return result;
	} 
	
	//TODO you must write this method
	/**
	 * The extractString method should extract a string which has been hidden in the stegoimage
	 * @param the name of the stego image 
	 * @return a string which contains either the message which has been extracted or 'Fail' which indicates the extraction
	 * was unsuccessful
	*/
	public String extractString(String stego_image) {
		String result = "Fail";
		return result;
	}
	
	//TODO you must write this method
	/**
	 * The hideFile method hides any file (so long as there's enough capacity in the image file) in a cover image
	 * @param file_payload - the name of the file to be hidden, you can assume it is in the same directory as the program
	 * @param cover_image - the name of the cover image file, you can assume it is in the same directory as the program
	 * @return String - either 'Fail' to indicate an error in the hiding process, or the name of the stego image written out as a
	 * result of the successful hiding process
	*/
	public String hideFile(String file_payload, String cover_image) {
		return null;
	}
	
	//TODO you must write this method
	/**
	 * The extractFile method hides any file (so long as there's enough capacity in the image file) in a cover image
	 *
	 * @param stego_image - the name of the file to be hidden, you can assume it is in the same directory as the program
	 * @return String - either 'Fail' to indicate an error in the extraction process, or the name of the file written out as a
	 * result of the successful extraction process
	*/
	public String extractFile(String stego_image){
		return null;
	}
	
	//TODO you must write this method
	/**
	 * This method swaps the least significant bit with a bit from the filereader
	 * @param bitToHide - the bit which is to replace the lsb of the byte of the image
	 * @param byt - the current byte
	 * @return the altered byte
	 */
	public int swapLsb(int bitToHide, int byt) {		
		return 0;
	}

}