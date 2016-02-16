package steg;

public class RGBUtility {
	/**
	 * @param pixel
	 * @return a map from rgb colour to its binary string for that pixel
	 */
	public static int[] getRGBFromPixel(int pixel) {
		int r = (pixel >> 16) & 0xff;
		int g = (pixel >> 8) & 0xff;
		int b = (pixel) & 0xff;
		
		return new int[] {r, g, b};
	}
	
	/**
	 * @param rgb: a map from rgb colour to its binary string for a pixel
	 * @return the rgb value as a pixel
	 */
	public static int getPixel(int[] rgb) {	
		return ((rgb[0] & 0x0ff) << 16) | ((rgb[1] & 0x0ff) << 8) | (rgb[2] & 0x0ff);
	}
}
