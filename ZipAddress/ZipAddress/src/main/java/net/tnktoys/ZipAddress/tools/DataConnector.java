package net.tnktoys.ZipAddress.tools;

import net.tnk_toys.ZipCodeLib.ZipCodeManager;

public class DataConnector {
	private static DataConnector _instance = new DataConnector();
	
	private ZipCodeManager zipcodeManager = new ZipCodeManager();
	
	public static DataConnector getInstance() {
		return _instance;
	}
	
	public ZipCodeManager getZipCodeManager() {
		if (zipcodeManager == null) {
			zipcodeManager = new ZipCodeManager();
		}
		return zipcodeManager;
	}
}
