package jp.dip.fission.utility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.codec.binary.Base64InputStream;
import org.apache.commons.codec.binary.Base64OutputStream;

public class Utility {
	private static final String BLANK = "";
	public static boolean isBlank(String data) {
		if (data == null || data.equals(BLANK)) {
			return false;
		} else {
			return true;
		}
	}

	public static byte[] getObjectToByteArray(Object object) {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		GZIPOutputStream zipStream = null;
		ObjectOutputStream objectStream = null;
		try {
			zipStream = new GZIPOutputStream(outStream);
			objectStream = new ObjectOutputStream(zipStream);
			objectStream.writeObject(object);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (objectStream != null) {
				try {
					objectStream.reset();
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					objectStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (zipStream != null) {
				try {
					zipStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (outStream != null) {
				try {
					outStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return outStream.toByteArray();
	}

	public static Object getByteArrayToObject(byte[] data) {
		Object result = null;
		ByteArrayInputStream inStream = new ByteArrayInputStream(data);
		GZIPInputStream zipStream = null;
		ObjectInputStream objectStream = null;
		try {
			zipStream = new GZIPInputStream(inStream);
			objectStream = new ObjectInputStream(inStream);
			result = objectStream.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (objectStream != null) {
				try {
					objectStream.reset();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				try {
					objectStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (zipStream != null) {
				try {
					zipStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (inStream != null) {
				try {
					inStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	public static OutputStream encodeBase64(OutputStream outStream) {
		return new Base64OutputStream(outStream,true);
	}

	public static InputStream encodeBase64(InputStream inStream) {
		return new Base64InputStream(inStream,true);
	}

	public static OutputStream decodeBase64(OutputStream outStream) {
		return new Base64OutputStream(outStream,false);
	}

	public static InputStream decodeBase64(InputStream inStream) {
		return new Base64InputStream(inStream,false);
	}
}
