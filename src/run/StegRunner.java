package run;

public class StegRunner {
	public static void main(String[] args) {
		String test = "Hellooo world";
		byte[] toBytes = test.getBytes();
		byte[] fromBytes = new byte[13];
		
		for (int i = 0; i < toBytes.length; i++) {
			System.out.println(toBytes[i]);
			
			String binaryString = Integer.toBinaryString(toBytes[i] & 255 | 256).substring(1);
			System.out.println(binaryString);
			
			int recoveredVal = Integer.parseInt(binaryString, 2);
			System.out.println(recoveredVal + "\n");
			
			fromBytes[i] = (byte) recoveredVal;
		}
		
		System.out.println(new String(fromBytes));
	}
}
