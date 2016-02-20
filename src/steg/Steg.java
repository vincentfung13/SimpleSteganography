package steg;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

import javax.imageio.ImageIO;

import steg.filereading.FileReader;;

public class Steg {
	
	// A constant to hold the number of bits per byte
	private final static int byteLength = 8;
	// A constant to hold the number of bits used to store the size of the file extracted
	protected final static int sizeBitsLength = 32;
	// A constant to hold the number of bits used to store the extension of the file extracted
	protected final static int extBitsLength = 64;
	
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
	public static String hideString(String payload, String cover_filename) {
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
				System.err.println("ERROR: String is too long for the cover image.");
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
							String stegoImageName = "stego_image_string.bmp";
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

					lsbRed = rgb[j%3] & 1;
					currentBit = (byteArray[currentByteArrayPosition] >> (byteLength - currentBitPosition - 1)) & 1;
					
					if (lsbRed != currentBit) {
						rgb[j%3] = (byte) (rgb[j%3] ^ 1);
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
	
	/**
	 * The extractString method should extract a string which has been hidden in the stegoimage
	 * @param the name of the stego image 
	 * @return a string which contains either the message which has been extracted or 'Fail' which indicates the extraction
	 * was unsuccessful
	*/
	public static String extractString(String stego_image) {
		String result = "Fail";
		try {
			BufferedImage img = ImageIO.read(new File(stego_image));
			int width = img.getWidth();
			int height = img.getHeight();
			int payloadBitSize = 0;
			List<String> bitBuffer = new ArrayList<String>();
			int pixel, loopCounter;
			byte[] rgb;
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {
					loopCounter = i * height + j;
					pixel = img.getRGB(i, j);
					
					// Checking for boundaries
					if (loopCounter == sizeBitsLength) {
						StringBuilder sb = new StringBuilder();
						for (int k = 0; k < bitBuffer.size(); k++) {
							sb.append(bitBuffer.get(k));
						}
						payloadBitSize = Integer.parseInt(sb.toString(), 2);
						// Clear the buffer to to hold the hidden bits in next step
						bitBuffer.clear();
					}
					else if (loopCounter == sizeBitsLength + payloadBitSize) {
						// Convert the bit in the buffer to a string then return
						return new String(ByteUtility.getByteArrayFromBuffer(bitBuffer));
					}
					
					rgb = ByteUtility.getRGBFromPixel(pixel);
					bitBuffer.add(Integer.toString(rgb[j%3] & 1));
				}
			}
			
		} catch (IOException e) {
			System.err.println("Error: Couldn't find stego image.");
			result = "Fail";
		}
		
		return result;
	}
	
	/**
	 * The hideFile method hides any file (so long as there's enough capacity in the image file) in a cover image
	 * @param file_payload - the name of the file to be hidden, you can assume it is in the same directory as the program
	 * @param cover_image - the name of the cover image file, you can assume it is in the same directory as the program
	 * @return String - either 'Fail' to indicate an error in the hiding process, or the name of the stego image written out as a
	 * result of the successful hiding process
	*/
	public static String hideFile(String file_payload, String cover_image) {
		String result = "Fail";
		
		FileReader fr = new FileReader(file_payload);
			
		BufferedImage img;
		try {
			img = ImageIO.read(new File(cover_image));
			int w = img.getWidth();
			int h = img.getHeight();
			
			if (fr.getFileSize() > w * h) {
				System.err.println("ERROR: File size is too large");
				return result;
			}
			
			byte[] rgb;
			int currentBit;
			for (int i = 0; i < w; i++) {
				for (int j = 0; j < h; j++) {
					if (fr.hasNextBit()) {
						currentBit = fr.getNextBit();
						rgb = ByteUtility.getRGBFromPixel(img.getRGB(i, j));
						rgb[j%3] = (byte) swapLsb(currentBit, rgb[j%3]);
						img.setRGB(i, j, ByteUtility.getPixel(rgb));
					}
					else {
						// Write the image and return the result
						String stegoImageName = "stego_image_file.bmp";
						ImageIO.write(img, "bmp", new File(stegoImageName));
						result = stegoImageName;
						return result;
					}
				}
			}
			
		} catch (IOException e) {
			System.err.println("ERROR: Cover image does not exist");
		}	

		return result;
	}
	
	/**
	 * The extractFile method hides any file (so long as there's enough capacity in the image file) in a cover image
	 *
	 * @param stego_image - the name of the file to be hidden, you can assume it is in the same directory as the program
	 * @return String - either 'Fail' to indicate an error in the extraction process, or the name of the file written out as a
	 * result of the successful extraction process
	*/
	public static String extractFile(String stego_image) {
		String result = "Fail";
		try {
			BufferedImage img = ImageIO.read(new File(stego_image));
			int width = img.getWidth();
			int height = img.getHeight();
			String extension = null;
			List<String> bitBuffer = new ArrayList<String>();
			int pixel, loopCounter, payloadBitSize = 0;
			byte[] rgb;
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {
					pixel = img.getRGB(i, j);
					loopCounter = i * height + j;
					
					// Checking for boundaries
					if (loopCounter == sizeBitsLength) {
						StringBuilder sb = new StringBuilder();
						for (int k = 0; k < bitBuffer.size(); k++) {
							sb.append(bitBuffer.get(k));
						}
						payloadBitSize = Integer.parseInt(sb.toString(), 2);
						// Clear the buffer to to hold the hidden bits in next step
						bitBuffer.clear();
					}
					else if (loopCounter == sizeBitsLength + extBitsLength) {
						byte[] extensionRawByteArray = ByteUtility.getByteArrayFromBuffer(bitBuffer);
						int zeroCount = 0;
						for (int k = 0; k < extensionRawByteArray.length; k++) {
							if (extensionRawByteArray[k] == 0)
								zeroCount++;
						}
						byte[] extensionByteArray = Arrays.copyOfRange(extensionRawByteArray, zeroCount, extensionRawByteArray.length);
						extension = new String(extensionByteArray);
						
						// Clear the buffer to to hold the hidden bits in next step
						bitBuffer.clear();
					}
					else if (loopCounter == sizeBitsLength + extBitsLength + payloadBitSize) {
						// Write to file
						File file = new File("recovered" + extension);
						byte[] resultByteArray = ByteUtility.getByteArrayFromBuffer(bitBuffer);
						FileOutputStream out = new FileOutputStream(file);
						out.write(resultByteArray);
						out.close();
						
						return "recovered" + extension;
					}
					
					rgb = ByteUtility.getRGBFromPixel(pixel);
					bitBuffer.add(Integer.toString(rgb[j%3] & 1));
				}
			}
			
		} catch (IOException e) {
			System.err.println("Error: Couldn't find stego image.");
			result = "Fail";
		}
		
		return result;
	}
	
	/**
	 * This method swaps the least significant bit with a bit from the filereader
	 * @param bitToHide - the bit which is to replace the lsb of the byte of the image
	 * @param byt - the current byte
	 * @return the altered byte
	 */
	public static int swapLsb(int bitToHide, int byt) {
		int lsb = byt & 1;
		if (lsb == bitToHide)
			return byt;
		else
			return byt ^ 1;
	}
}