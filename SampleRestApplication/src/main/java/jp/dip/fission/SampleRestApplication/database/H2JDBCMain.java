package jp.dip.fission.SampleRestApplication.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class H2JDBCMain {
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
		Properties prop = new Properties();
		prop.setProperty("user", "sa");
		prop.setProperty("password", "");

		String dbName = "./h2DB";
		try (Connection conn = DriverManager.getConnection(protocol+dbName,prop)) {
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
