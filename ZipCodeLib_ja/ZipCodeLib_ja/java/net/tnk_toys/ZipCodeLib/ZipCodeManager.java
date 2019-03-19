package net.tnk_toys.ZipCodeLib;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.zip.ZipInputStream;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.tnk_toys.ZipCodeLib.entity.ZipEntity;

public class ZipCodeManager {
	
	public enum SearchType {
		CODE
		,NAME
	}
	
	private Logger logger = LoggerFactory.getLogger(ZipCodeManager.class);

	private static String urlAddress = "http://www.post.japanpost.jp/zipcode/dl/kogaki/zip/ken_all.zip";
	private static String savePath = "/data/KEN_ALL.CSV";
	private static String charset = "Shift_JIS";
	private static String proxyAddress = null;
	private static int proxyPort = 0;

	private String dbName = "./h2DB";
	private String driver = "org.h2.Driver";
	private String protocol = "jdbc:h2:";
	private static String csvPath = "/data/KEN_ALL.CSV";
	// 1行目がヘッダ行でない場合のみ指定
	private static String csvHeader = "CODE_LOCAL_GOVERNMENT,CODE_OLD_ZIP,CODE_ZIP,NAME_PREFECTURES_KANA,NAME_CITY_KANA,NAME_TOWN_AREA_KANA,NAME_PREFECTURES_KANJI,NAME_CITY_KANJI,NAME_TOWN_AREA_KANJI,FLG_MULTIPLE_ZIP_CODE,FLG_SUBLOCALITY,FLG_CHOME,FLG_MULTIPLE_TOWN_AREA,FLG_UPDATE,FLG_UPDATE_REASON";
	private static String csvEncode = "Shift-JIS";
	
	private boolean isAlive = false;
	private boolean isUpdating = false;
	private DataSource ds = null;
	
	
	public ZipCodeManager() {
		logger.debug("ZipCodeTools.initialize");
		try {
			initialize();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<ZipEntity> searchZipCode(String key,SearchType type) throws ZipCodeException {
		checkAlive();
//		zipCode = sanitizeZipCode(zipCode);
		List<ZipEntity> entityList = new ArrayList<>();
		try (Connection conn = getConnection()) {
			try (PreparedStatement statement = conn.prepareStatement("select * from csvread("
					+ " '" + csvPath + "',"
					+ (csvHeader==null?null:" '" + csvHeader + "',")
					+ " '" + csvEncode + "'"
					+ " ) "
					+ (SearchType.CODE.equals(type)?" where CODE_ZIP like ? {escape '\\'}":"")
					+ (SearchType.NAME.equals(type)?" where NAME_PREFECTURES_KANA || NAME_CITY_KANA || NAME_TOWN_AREA_KANA LIKE ? {escape '\\'} or NAME_PREFECTURES_KANJI || NAME_CITY_KANJI || NAME_TOWN_AREA_KANJI LIKE ? {escape '\\'}":"")
					+ "order by CODE_ZIP")) {
				switch (type) {
					case CODE:
						statement.setString(1, key + "%");
						break;
					case NAME:
						statement.setString(1,"%" + key + "%");
						statement.setString(2,"%" + key + "%");
						break;
					default:
						break;
				}
				try (ResultSet rs = statement.executeQuery()) {
					while(rs.next()) {
						ZipEntity entity = new ZipEntity();
						entity.setCodeLocalGovernment(rs.getString("CODE_LOCAL_GOVERNMENT"));
						entity.setCodeOldZip(rs.getString("CODE_OLD_ZIP"));
						entity.setCodeZip(rs.getString("CODE_ZIP"));
						entity.setNamePrefecturesKana(rs.getString("NAME_PREFECTURES_KANA"));
						entity.setNameCityKana(rs.getString("NAME_CITY_KANA"));
						entity.setNameTownAreaKana(rs.getString("NAME_TOWN_AREA_KANA"));
						entity.setNamePrefecturesKanji(rs.getString("NAME_PREFECTURES_KANJI"));
						entity.setNameCityKanji(rs.getString("NAME_CITY_KANJI"));
						entity.setNameTownAreaKanji(rs.getString("NAME_TOWN_AREA_KANJI"));
						entity.setFlgMultipleZipCode(rs.getString("FLG_MULTIPLE_ZIP_CODE"));
						entity.setFlgSublocality(rs.getString("FLG_SUBLOCALITY"));
						entity.setFlgChome(rs.getString("FLG_CHOME"));
						entity.setFlgMultipleTownArea(rs.getString("FLG_MULTIPLE_TOWN_AREA"));
						entity.setFlgUpdate(rs.getString("FLG_UPDATE"));
						entity.setFlgUpdateReason(rs.getString("FLG_UPDATE_REASON"));
						logger.debug("CODE_LOCAL_GOVERNMENT="+rs.getString("CODE_LOCAL_GOVERNMENT") + "CODE_ZIP="+rs.getString("CODE_ZIP") + "NAME_PREFECTURES_KANJI="+rs.getString("NAME_PREFECTURES_KANJI") + "NAME_CITY_KANJI="+rs.getString("NAME_CITY_KANJI") + " NAME_TOWN_AREA_KANJI=" + rs.getString("NAME_TOWN_AREA_KANJI"));
						entityList.add(entity);
					}
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				statement.close();
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ZipCodeException(e);
		}
		return entityList;
	}
	
	@Override
	protected void finalize() throws Throwable {
		logger.debug("ZipCodeTools.finalize");
		try {
			super.finalize();
		} finally {
			if (isAlive) {
				destroy();
			}
		}
	}
	
	private void initialize() throws Exception  {
		Class.forName(driver).newInstance();
		ds = createDataSource();
	}
	
	public void destroy() throws SQLException  {
		logger.debug("ZipCodeTools.destroy");
		ds = null;
		while (DriverManager.getDrivers().hasMoreElements()) {
			DriverManager.deregisterDriver(DriverManager.getDrivers().nextElement());
		}
	}
	
	private void checkAlive() throws ZipCodeException {
		if(isAlive || ds == null) {
			throw new ZipCodeException(ZipCodeException.CODE_INITIALIZE_FAILURE);
		} else if (isUpdating) {
			throw new ZipCodeException(ZipCodeException.CODE_ZIP_CODE_UPDATING);
		}
	}
	
	public String sanitizeZipCode(String zipCode) throws ZipCodeException {
		if (zipCode == null || zipCode.isEmpty()) {
			throw new ZipCodeException(ZipCodeException.CODE_EMPTY_ZIP_CODE);
		} else {
			String[] splitZipCode = zipCode.trim().split("-");
			if (splitZipCode[0].length() < 3) {
				throw new ZipCodeException(ZipCodeException.CODE_LESS_MIN_LENGTH);
			}
			zipCode = zipCode.replace("-", "");
			if (zipCode.length() > 7) {
				throw new ZipCodeException(ZipCodeException.CODE_OVER_MAX_LENGTH);
			} else if (!NumberUtils.isNumber(zipCode)) {
				throw new ZipCodeException(ZipCodeException.CODE_CONTAIN_NON_NUMERIC_ZIP_CODE);
			}
//			if (zipCode.length() == 3) {
//				zipCode = StringUtils.rightPad(zipCode, 7, "0");
//			}
		}
		return zipCode;
	}
	
	public String sanitizeZipName(String zipName) throws ZipCodeException {
		if (zipName == null || zipName.isEmpty()) {
			throw new ZipCodeException(ZipCodeException.CODE_EMPTY_ZIP_CODE);
		} else {
			zipName = zipName.replace("　", " ");
			zipName = zipName.replace(" ", "%");
		}
		return zipName;
	}
	
	public void updateZipCode() {
		logger.debug("start");
		if (isUpdating) {
			return;
		} else {
			logger.debug("updateZipCode");
			isUpdating = true;
		}
		try {
			URL url = new URL(urlAddress);
			URLConnection conn = null;
			proxyAddress = System.getProperty("http.proxyHost");
			proxyPort = System.getProperty("http.proxyPort") == null?0:Integer.parseInt(System.getProperty("http.proxyPort"));
			try {
				// プロキシ設定がある場合はプロキシ経由でコネクションを作成
				if (proxyAddress != null && !proxyAddress.isEmpty()) {
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
					logger.debug(dst.toAbsolutePath().toString());
					try(ZipInputStream zis = new ZipInputStream(conn.getInputStream(),Charset.forName(charset))) {
						zis.getNextEntry();
						if (Files.notExists(dst, LinkOption.NOFOLLOW_LINKS)) {
							Files.createFile(dst);
						}
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
		isUpdating = false;
		logger.debug("end");
	}
	
	private Connection getConnection() throws SQLException {
		return ds.getConnection();
	}
	
	private DataSource createDataSource() throws Exception {
		return BasicDataSourceFactory.createDataSource(getJdbcProperties());
	}
	
	private Properties getJdbcProperties() {
		Properties prop = new Properties();
		prop.setProperty("driverClassName", driver);
		prop.setProperty("url", protocol + dbName + ";MV_STORE=FALSE;MVCC=FALSE;DB_CLOSE_ON_EXIT=TRUE");
		prop.setProperty("username", "sa");
		prop.setProperty("password", "");
		prop.setProperty("initialSize", "30");
		prop.setProperty("maxActive", "100");
		prop.setProperty("maxIdle", "30");
		prop.setProperty("maxWait", "5000");
		prop.setProperty("validationQuery", "select count(*) from dual");
		return prop;
	}
}
