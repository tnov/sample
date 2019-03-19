package net.tnktoys.ZipAddress.tools;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HashGenerator {

	private static String DEFAULT_SECRET = "1234567890";
	private static String DEFAULT_ALGORITHM = "HmacSHA256";

	public static String createHash(String key) {
		String secret = DEFAULT_SECRET;
		SecretKeySpec keySpec = new SecretKeySpec(secret.getBytes(),DEFAULT_ALGORITHM);
		Mac mac = null;;
		try {
			mac = Mac.getInstance(DEFAULT_ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
		try {
			mac.init(keySpec);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
			return null;
		}
		byte[] mac_bytes = mac.doFinal(key.getBytes());
		StringBuilder sb = new StringBuilder(2*mac_bytes.length);
		for (byte b:mac_bytes) {
			sb.append(String.format("%02x", b));
		}
		return sb.toString();
	}

}
