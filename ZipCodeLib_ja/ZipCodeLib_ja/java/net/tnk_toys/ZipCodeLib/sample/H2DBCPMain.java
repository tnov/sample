package net.tnk_toys.ZipCodeLib.sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.tnk_toys.ZipCodeLib.entity.ZipEntity;

public class H2DBCPMain {
	private static Logger logger = LoggerFactory.getLogger(H2DBCPMain.class);
	private static String driver = "org.h2.Driver";
	private static String protocol = "jdbc:h2:";
	private static String csvPath = "./data/KEN_ALL.CSV";
	// 1行目がヘッダ行でない場合のみ指定
	private static String csvHeader = "CODE_LOCAL_GOVERNMENT,CODE_OLD_ZIP,CODE_ZIP,NAME_PREFECTURES_KANA,NAME_CITY_KANA,NAME_TOWN_AREA_KANA,NAME_PREFECTURES_KANJI,NAME_CITY_KANJI,NAME_TOWN_AREA_KANJI,FLG_MULTIPLE_ZIP_CODE,FLG_SUBLOCALITY,FLG_CHOME,FLG_MULTIPLE_TOWN_AREA,FLG_UPDATE,FLG_UPDATE_REASON";
	private static String csvCondition = "NAME_TOWN_AREA_KANJI regexp '（.*階）$' AND CODE_ZIP='5316036'";
//	private static String csvCondition = null;
	private static String csvOrderby = "CODE_LOCAL_GOVERNMENT DESC";
	private static String csvEncode = "Shift-JIS";
	public static void main(String[] args) throws Exception {
		logger.debug("h2db start");

		initialize();

		try (Connection conn = getConnection()){
			conn.setAutoCommit(false);
			int count = getZipEntityCount(conn,csvPath,csvHeader,csvCondition,csvOrderby,csvEncode);
			List<ZipEntity> entityList = getZipEntityList(conn,csvPath,csvHeader,csvCondition,csvOrderby,csvEncode);
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		destroy();
		logger.debug("h2db end");
	}

	private static void initialize() {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	private static void destroy() {
		try {
			while (DriverManager.getDrivers().hasMoreElements()) {
				DriverManager.deregisterDriver(DriverManager.getDrivers().nextElement());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static int getZipEntityCount(Connection conn,String path,String header,String condition,String orderby,String encode) {
		int zipEntityCount = 0;
		try (Statement statement = conn.createStatement()) {
			logger.debug("select count(*) as COUNT from csvread("
					+ " '" + path + "',"
					+ (header==null?null:" '" + header + "',")
					+ " '" + encode + "'"
					+ " ) "
					+ (condition==null?"":" where " + condition));
			try (ResultSet rs = statement.executeQuery("select count(*) as COUNT from csvread("
					+ " '" + path + "',"
					+ (header==null?null:" '" + header + "',")
					+ " '" + encode + "'"
					+ " ) "
					+ (condition==null?"":" where " + condition))) {
				while(rs.next()) {
					logger.debug("zipEntityCount="+rs.getInt("COUNT"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return zipEntityCount;
	}

	private static List<ZipEntity> getZipEntityList(Connection conn,String path,String header,String condition,String orderby,String encode) {
		List<ZipEntity> entityList = new ArrayList<ZipEntity>();
		try (Statement statement = conn.createStatement()) {
			logger.debug("select * from csvread("
					+ " '" + path + "',"
					+ (header==null?null:" '" + header + "',")
					+ " '" + encode + "'"
					+ " ) "
					+ (condition==null?"":" where " + condition)
					+ (orderby==null?"":" order by " + orderby));
			try (ResultSet rs = statement.executeQuery("select * from csvread("
					+ " '" + path + "',"
					+ (header==null?null:" '" + header + "',")
					+ " '" + encode + "'"
					+ " ) "
					+ (condition==null?"":" where "+ condition)
					+ (orderby==null?"":" order by "+ orderby))) {
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
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return entityList;
	}

	private static Connection getConnection() throws SQLException, Exception {
		return getDataSource().getConnection();
	}

	private static DataSource getDataSource() throws Exception {
		return BasicDataSourceFactory.createDataSource(getJdbcProperties());
	}

	private static Properties getJdbcProperties() {
		String dbName = "./h2DB";
		Properties prop = new Properties();
		prop.setProperty("driverClassName", driver);
		prop.setProperty("url", protocol+dbName);
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
