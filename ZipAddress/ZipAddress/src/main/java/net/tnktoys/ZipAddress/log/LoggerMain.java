package net.tnktoys.ZipAddress.log;

import java.io.FileNotFoundException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.core.joran.spi.JoranException;
import net.tnktoys.ZipAddress.log.setting.logback.LoggerConfigLoader;

public class LoggerMain {
	
	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(LoggerMain.class);
		System.out.println(LoggerConfigLoader.class.getResource("/log/logback.xml").getPath());
		Map<String,String> map = System.getenv();
		map.forEach((key,value) -> System.out.println(key + " = " + value));
		try {
			LoggerConfigLoader.reload(LoggerConfigLoader.class.getResource("/log/logback.xml"));
		} catch (FileNotFoundException | JoranException e) {
			e.printStackTrace();
		}
		logger.debug("debug");
	}
}
