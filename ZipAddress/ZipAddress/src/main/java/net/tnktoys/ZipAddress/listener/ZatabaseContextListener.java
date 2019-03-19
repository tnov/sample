package net.tnktoys.ZipAddress.listener;

import java.sql.SQLException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import net.tnktoys.ZipAddress.tools.DataConnector;

@WebListener
public class ZatabaseContextListener implements ServletContextListener {
	
	private static DataConnector dataConnector = null;
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("databaseContextInitialized");
		dataConnector = DataConnector.getInstance();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("databaseContextDestroyed");
		try {
			dataConnector.getZipCodeManager().destroy();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
