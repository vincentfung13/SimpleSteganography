package steg;

public class ByteUtility {
	
	/**
	 * Given the string the hide, return a byte array consist of the size followed by the payload in bytes
	 * @param payload: string to hide
	 * @return array of bytes to hide
	 */
	public static byte[] getBytesToHide(String payload) {
		byte[] payloadInBytes = payload.getBytes();
		
		int numOfBits = payloadInBytes.length * 8;
		byte[] sizeInBytes = new byte[] {
				(byte) (numOfBits >>> 24), 
				(byte) (numOfBits >>> 16), 
				(byte) (numOfBits >>> 8), 
				(byte) (numOfBits)
		};
		
		byte[] bytesToHide = new byte[sizeInBytes.length + payloadInBytes.length];
		for (int i = 0; i < sizeInBytes.length; i++) {
			bytesToHide[i] = sizeInBytes[i];
		}
		
		for (int i = sizeInBytes.length; i < bytesToHide.length; i++) {
			bytesToHide[i] = payloadInBytes[i - sizeInBytes.length];
		}
		
		return bytesToHide;
	}
	
	/**
	 * Convert a four byte array to an integer
	 * @param byteArray
	 * @return
	 */
	public static int getBitNum(byte[] byteArray) {
		return (byteArray[0] << 24) | (byteArray[1] << 16) | (byteArray[2] << 8) | byteArray[3];
	}
	
	/**
	 * @param pixel
	 * @return a map from rgb colour to its binary string for that pixel
	 */
	public static byte[] getRGBFromPixel(int pixel) {
		byte r = (byte) ((pixel >> 16) & 0xff);
		byte g = (byte) ((pixel >> 8) & 0xff);
		byte b = (byte) ((pixel) & 0xff);
		
		return new byte[] {r, g, b};
	}
	
	/**
	 * @param rgb: a map from rgb colour to its binary string for a pixel
	 * @return the rgb value as a pixel
	 */
	public static int getPixel(byte[] rgb) {	
		return ((rgb[0] & 0x0ff) << 16) | ((rgb[1] & 0x0ff) << 8) | (rgb[2] & 0x0ff);
	}
}
