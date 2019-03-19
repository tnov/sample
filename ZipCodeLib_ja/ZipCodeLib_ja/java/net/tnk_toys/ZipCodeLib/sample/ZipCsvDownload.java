package net.tnk_toys.ZipCodeLib.sample;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.util.zip.ZipInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZipCsvDownload {

	private static String urlAddress = "http://www.post.japanpost.jp/zipcode/dl/kogaki/zip/ken_all.zip";
	private static String savePath = "./data/KEN_ALL.CSV";
	private static String charset = "Shift_JIS";
//	private static String proxyAddress = "192.168.1.33";
//	private static int proxyPort = 8080;
	private static String proxyAddress = null;
	private static int proxyPort = 0;
	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(ZipCsvDownload.class);
		logger.debug("start");
		try {
			URL url = new URL(urlAddress);
			URLConnection conn = null;
			try {
				// プロキシ設定がある場合はプロキシ経由でコネクションを作成
				if (proxyAddress != null) {
					Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyAddress, proxyPort));
					conn = url.openConnection(proxy);
				} else {
					conn = url.openConnection();
				}
				Timestamp remote = new Timestamp(conn.getLastModified());
				Path dst = Paths.get(savePath);
				Timestamp local = new java.sql.Timestamp(dst.toFile().lastModified());
				logger.debug("remote=" + remote + "/local=" + local);
				// ローカルファイルの更新日時(取得日時)とリモートファイルの更新日時を比較
				// リモートファイルの更新日付が新しい場合、リモートファイルを取得
				long size = 0L;
				if (remote.after(local)) {
					logger.debug("get zip csv");
					// 取得と同時にZIPを解凍する。
					try(ZipInputStream zis = new ZipInputStream(conn.getInputStream(),Charset.forName(charset))) {
						zis.getNextEntry();
						size = Files.copy(zis, dst, StandardCopyOption.REPLACE_EXISTING);
						zis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				// ファイル取得に失敗
				if (size <= 0L) {
					
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		logger.debug("end");
	}

}
