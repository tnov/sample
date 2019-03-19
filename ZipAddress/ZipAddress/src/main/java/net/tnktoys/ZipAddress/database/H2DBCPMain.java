package net.tnktoys.ZipAddress.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSourceFactory;

public class H2DBCPMain {
	private static String driver = "org.h2.Driver";
	private static String protocol = "jdbc:h2:";

	public static void main(String[] args) {
		System.out.println("h2db start");

		try {
			Class.forName(driver).newInstance();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			System.exit(-1);
		}
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
		try {
			DataSource source = BasicDataSourceFactory.createDataSource(prop);
			try (Connection conn = source.getConnection()){
				conn.setAutoCommit(false);
				try (Statement statement = conn.createStatement()) {
					try (ResultSet rs = statement.executeQuery("select * from csvread("
							+ " '" + H2DBCPMain.class.getResource("/data/KEN_ALL.CSV").getPath().substring(1) + "',"
							+ " 'LOCAL_GOVERNMENT_CODE,OLD_ZIP_CODE,ZIP_CODE,KANA1,KANA2,KANA3,ADDRESS1,ADDRESS2,ADDRESS3,PREF_CODE',"
							+ " 'Shift-JIS'"
							+ " )"
							+ " where "
							+ " ADDRESS3 regexp '（.*階）$' AND ZIP_CODE='5316036'")) {
						while(rs.next()) {
							System.out.println("LOCAL_GOVERNMENT_CODE="+rs.getString("LOCAL_GOVERNMENT_CODE") + "ZIP_CODE="+rs.getString("ZIP_CODE") + "ADDRESS1="+rs.getString("ADDRESS1") + "ADDRESS2="+rs.getString("ADDRESS2") + " ADDRESS3=" + rs.getString("ADDRESS3"));
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				conn.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			while (DriverManager.getDrivers().hasMoreElements()) {
				DriverManager.deregisterDriver(DriverManager.getDrivers().nextElement());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("h2db end");
	}

}
