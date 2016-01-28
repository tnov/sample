package jp.dip.fission.utility;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Compressor {
	private static int BUFFER_SIZE = 8192;

	public static boolean compress(InputStream inStream,OutputStream outStream) {
		boolean result = false;
		byte[] buf = new byte[BUFFER_SIZE];
		try {
			GZIPOutputStream zipStream = new GZIPOutputStream(outStream);
			BufferedInputStream bufferedStream = new BufferedInputStream(inStream);
			while (bufferedStream.read(buf) > 0 ) {
				zipStream.write(buf);
			}
			result = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static boolean uncompress(InputStream inStream,OutputStream outStream) {
		boolean result = false;
		byte[] buf = new byte[BUFFER_SIZE];
		try {
			GZIPInputStream zipStream = new GZIPInputStream(inStream);
			while (zipStream.read(buf) > 0 ) {
				outStream.write(buf);
			}
			result = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
