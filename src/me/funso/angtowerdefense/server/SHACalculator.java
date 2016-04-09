package me.funso.angtowerdefense.server;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class SHACalculator {
	final protected static char[] hexArray = "0123456789abcdef".toCharArray();
	
	public static String bytesToHex(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    for ( int j = 0; j < bytes.length; j++ ) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}
	
	public static String calculate(byte x[]) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA");
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	    md.update(x);
	    return bytesToHex(md.digest());
	}
	
	public static String generateSalt(int size) {
		final Random r = new SecureRandom();
		byte[] salt = new byte[size];
		r.nextBytes(salt);
		return SHACalculator.bytesToHex(salt);
	}
}
