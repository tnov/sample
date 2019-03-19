package net.tnktoys.ZipAddress.log.setting.logback;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;

public class LoggerConfigLoader {
	public static void reload(File resource) throws JoranException, FileNotFoundException {
		LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
		context.reset();
		JoranConfigurator configurator = new JoranConfigurator();
		configurator.setContext(context);
		configurator.doConfigure(resource);
	}
	public static void reload(URL resource) throws JoranException, FileNotFoundException {
		LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
		context.reset();
		JoranConfigurator configurator = new JoranConfigurator();
		configurator.setContext(context);
		configurator.doConfigure(resource);
	}
	public static void reload(String resource) throws JoranException, FileNotFoundException {
		LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
		context.reset();
		JoranConfigurator configurator = new JoranConfigurator();
		configurator.setContext(context);
		configurator.doConfigure(resource);
	}
}
