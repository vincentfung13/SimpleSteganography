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
			
			// Convert the payload to array of bytes
			byte[] byteArray = ByteUtility.getBytesToHide(payload);
			
			// Check if the string is too long to hide
			if (w * h < byteLength * byteArray.length) {
				System.err.println("ERROR: String is to long for the cover image.");
				return result;
			}
			
			byte[] rgb;
			int currentByteArrayPosition = 0;
			int currentBitPosition = 0;
			int lsbRed, currentBit;
			for (int i = 0; i < w; i++) {
				for (int j = 0; j < h; j++) {
					// Check if we have already finished the hiding process
					if (currentBitPosition == byteLength - 1) {
						if (currentByteArrayPosition == byteArray.length - 1) {
							// Write the result to disk
							String stegoImageName = "stego_image.bmp";
							ImageIO.write(img, "bmp", new File(stegoImageName));
							result = stegoImageName;
							return result;
						}
						else {
							currentByteArrayPosition++;
						}
					}
					
					// Flip the bits
					currentBitPosition = (i * h + j) % byteLength;
					rgb = ByteUtility.getRGBFromPixel(img.getRGB(i, j));

					lsbRed = rgb[0] & 1;
					currentBit = (byteArray[currentByteArrayPosition] >> (byteLength - currentBitPosition - 1)) & 1;
					if (lsbRed != currentBit) {
						rgb[0] = (byte) (rgb[0] ^ (1 << (byteLength - 1)));
						img.setRGB(i, j, ByteUtility.getPixel(rgb));
					}
				}
			}
		} catch (IOException e) {
			System.err.println("ERROR: Counldn't find image.");
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
		try {
			BufferedImage img = ImageIO.read(new File(stego_image));
			int width = img.getWidth();
			int height = img.getHeight();
			int payloadBitSize = 0;
			List<String> bitBuffer = new ArrayList<String>();
			int pixel;
			byte[] rgb;
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {
					pixel = img.getRGB(i, j);
					if (i * height + j < sizeBitsLength) {
						// The the lsb and put it in the bit buffer
						rgb = ByteUtility.getRGBFromPixel(pixel);
						bitBuffer.add(Integer.toString(rgb[0] >> byteLength - 1 & 1));
					}
					else if (i * height + j == sizeBitsLength) {
						StringBuilder sb = new StringBuilder();
						for (int k = 0; k < bitBuffer.size(); k++) {
							sb.append(bitBuffer.get(k));
						}
						payloadBitSize = Integer.parseInt(sb.toString(), 2);
						// Clear the buffer to to hold the hidden bits in next step
						bitBuffer.clear();
					}
					else if (i * height + j <= sizeBitsLength + payloadBitSize) {
						// Put all the data bit in the bit buffer
						rgb = ByteUtility.getRGBFromPixel(pixel);
						bitBuffer.add(Integer.toString(rgb[0] >> byteLength - 1 & 1));
					}
					else {
						// Convert the bit in the buffer to a string then return
						StringBuilder sb = new StringBuilder();
						for (int k = 0; k < bitBuffer.size(); k++) {
							sb.append(bitBuffer.get(k));
						}
						
						String resultBitString = sb.toString();
						System.out.println(resultBitString);
						byte[] resultByteArray = new byte[payloadBitSize/byteLength];
						for (int k = 0; k < payloadBitSize; k+=byteLength) {
//							System.out.println(resultBitString.substring(k, k + 8));
//							resultByteArray[k/byteLength] = Byte.parseByte(resultBitString.substring(k, k + 8));

						}
						
						return new String(resultByteArray);
					}
				}
			}
			
		} catch (IOException e) {
			System.err.println("Error: Couldn't find stego image.");
			result = "Fail";
		}
		
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