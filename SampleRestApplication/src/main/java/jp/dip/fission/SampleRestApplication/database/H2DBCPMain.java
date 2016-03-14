package jp.dip.fission.SampleRestApplication.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
					statement.execute("create table location(num int, addr varchar(40))");
					PreparedStatement psInsert = conn.prepareStatement("insert into location values (?, ?)");
					psInsert.setInt(1, 1956);
					psInsert.setString(2, "Webster St.");
					psInsert.executeUpdate();

					try (ResultSet rs = statement.executeQuery("SELECT num, addr FROM location ORDER BY num")) {
						while(rs.next()) {
							System.out.println("num="+rs.getInt("num") + " addr=" + rs.getString("addr"));
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					statement.execute("drop table location");
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
