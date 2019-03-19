package net.tnk_toys.ZipCodeLib.sample;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import ch.qos.logback.core.joran.spi.JoranException;
import net.tnk_toys.ZipCodeLib.ZipCodeException;
import net.tnk_toys.ZipCodeLib.ZipCodeManager;
import net.tnk_toys.ZipCodeLib.entity.ZipEntity;
import net.tnk_toys.ZipCodeLib.util.LoggerConfigLoader;

public class Test {

	private static String zipCode1 = "438-0078";
	private static String zipCode2 = "3940091";
	private static String zipCode3 = "9360014";
	private static String zipCode4 = "577-0818";
	private static String zipCode5 = "57-7";
	private static String zipCode6 = "577-";
	private static String zipCode7 = "4300928";
	
	public static void main(String[] args) {
		try {
			LoggerConfigLoader.reload(LoggerConfigLoader.class.getResource("/ZipCodeLib/log/logback.xml"));
		} catch (FileNotFoundException | JoranException e) {
			e.printStackTrace();
		}
		ZipCodeManager tools = new ZipCodeManager();
		String[] arrays = {zipCode1,zipCode2,zipCode3,zipCode4,zipCode5,zipCode6,zipCode7};
		Arrays.stream(arrays).parallel().forEach(zipcode -> {
			try {
				List<ZipEntity> entityList = tools.searchZipCode(zipcode,ZipCodeManager.SearchType.CODE);
				entityList.forEach(entity -> System.out.println(entity.getCodeZip()+"-"+entity.getNamePrefecturesKanji()+entity.getNameCityKanji()+entity.getNameTownAreaKanji()));
			} catch (ZipCodeException e) {
				e.printStackTrace();
			}
		});
		try {
			tools.destroy();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
