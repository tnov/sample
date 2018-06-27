/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.tnk.utils.log.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LoggingProperties {

	public LoggingProperties() {
		this.level = GROBAL_LEVEL + DEFAULT_GROBAL_LEVEL;
		this.formatter = SIMPLE_FORMATTER_FORMAT + DEFAULT_SIMPLE_FORMATTER_FORMAT;
		this.properties = new ArrayList<>();
		this.properties.add(new ConsoleLoggingProperty());
		this.properties.add(new FileLoggingProperty());
		this.handlers = HANDLERS + this.properties.stream().map(prop->prop.getHandler()).collect(Collectors.joining(","));
	}

	public LoggingProperties(List<LoggingProperty> properties) {
		this.level = GROBAL_LEVEL + DEFAULT_GROBAL_LEVEL;
		this.formatter = SIMPLE_FORMATTER_FORMAT + DEFAULT_SIMPLE_FORMATTER_FORMAT;
		this.properties = new ArrayList<>();
		this.properties.addAll(properties);
		this.handlers = HANDLERS + this.properties.stream().map(prop->prop.getHandler()).collect(Collectors.joining(","));
	}

	/*
	 * Global properties
	 */
	// logging.properties key .level
	private static final String GROBAL_LEVEL = ".level=";

	// logging.properties default value .level
	private static final String DEFAULT_GROBAL_LEVEL = "ALL";

	// logging.properties key handlers
	private static final String HANDLERS = "handlers=";

	// SimpleFormatter format
	private static final String SIMPLE_FORMATTER_FORMAT = "java.util.logging.SimpleFormatter.format=";
	// SimpleFormatter default format
	private static final String DEFAULT_SIMPLE_FORMATTER_FORMAT = "%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS.%1$tL %4$s %2$s %5$s%6$s%n";

	private String level = null;
	private String handlers = null;
	private String formatter = null;

	private List<LoggingProperty> properties = null;

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getHandlers() {
		return handlers;
	}

	public void setHandlers(String handlers) {
		this.handlers = handlers;
	}

	public String getFormatter() {
		return formatter;
	}

	public void setFormatter(String formatter) {
		this.formatter = formatter;
	}

	public List<LoggingProperty> getProperties() {
		if (properties == null) {
			return null;
		}
		return properties;
	}

	public void setProperties(List<LoggingProperty> properties) {
		this.properties.clear();
		this.properties.addAll(properties);
		this.handlers = HANDLERS + this.properties.stream().map(prop->prop.getHandler()).collect(Collectors.joining(","));
	}

	public void addProperty(LoggingProperty property) {
		this.properties.add(property);
		this.handlers = HANDLERS + this.properties.stream().map(prop->prop.getHandler()).collect(Collectors.joining(","));
	}

	public void clearProperties() {
		this.properties.clear();
	}

	public String getLoggingProperties() {
		StringBuilder sb = new StringBuilder(200);
		sb.append(getLevel()==null?"":getLevel()+System.lineSeparator());
		sb.append(getFormatter()==null?"":getFormatter()+System.lineSeparator());
		sb.append(handlers==null?"":getHandlers()+System.lineSeparator());
		properties.stream().forEach(property->sb.append(property.getLoggingProperty()+System.lineSeparator()));
		return sb.toString();
	}

	public static void main (String[] args) {
		LoggingProperties properteis = new LoggingProperties();
		System.out.println(properteis.getLoggingProperties());
	}
}
