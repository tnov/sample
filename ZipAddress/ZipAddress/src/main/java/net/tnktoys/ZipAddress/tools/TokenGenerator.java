package net.tnktoys.ZipAddress.tools;

import org.apache.commons.lang3.RandomStringUtils;

public class TokenGenerator {

	private static final int    DEFAULT_TOKEN_LENGTH        = 20;
	private static final String CHARACTER_ALPHANUMERIC_EXT  = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ+-_";

	public static final String FORMAT_TYPE_ASCII            = "ascii";
	public static final String FORMAT_TYPE_ALPHABETIC       = "alphabetic";
	public static final String FORMAT_TYPE_ALPHANUMERIC     = "Alphanumeric";
	public static final String FORMAT_TYPE_NUMERIC          = "Numeric";
	public static final String FORMAT_TYPE_ALPHANUMERIC_EXT = "AlphanumericExt";

	public static final String DEFAULT_FORMAT_TYPE = FORMAT_TYPE_ALPHANUMERIC_EXT;

	public static String createToken() {
		return createToken(null,DEFAULT_TOKEN_LENGTH);
	}

	public static String createToken(int length) {
		return createToken(null,length);
	}

	public static String createToken(String format,int length) {
		String token = null;
		if (format == null) {
			format = DEFAULT_FORMAT_TYPE;
		}
		switch (format) {
		case FORMAT_TYPE_ASCII:
			token = RandomStringUtils.randomAscii(length);
			break;
		case FORMAT_TYPE_ALPHABETIC:
			token = RandomStringUtils.randomAlphabetic(length);
			break;
		case FORMAT_TYPE_ALPHANUMERIC:
			token = RandomStringUtils.randomAlphanumeric(length);
			break;
		case FORMAT_TYPE_NUMERIC:
			token = RandomStringUtils.randomNumeric(length);
			break;
		default:
			token = RandomStringUtils.random(length,CHARACTER_ALPHANUMERIC_EXT);
		}
		return token;
	}
}
