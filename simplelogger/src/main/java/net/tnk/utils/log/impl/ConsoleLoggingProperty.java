/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.tnk.utils.log.impl;

public class ConsoleLoggingProperty extends LoggingProperty {

	public static final String HANDLER = "java.util.logging.ConsoleHandler";

	public ConsoleLoggingProperty () {
		this.handler = HANDLER;
		this.encoding = ENCODING + DEFAULT_ENCODING;
		this.level = LEVEL + DEFAULT_LEVEL;
		this.filter = null;
		this.formatter = FORMATTER + DEFAULT_FORMATTER;

		this.append = null;
		this.count = null;
		this.limit = null;
		this.pattern = null;

		this.host = null;
		this.port = null;
	}

	/*
	 * Handler specific properties.
	 * Describes specific configuration info for Handlers.
	 */
	// logging.properties key java.util.logging.FileHandler.formatter
	private static final String LEVEL = "java.util.logging.ConsoleHandler.level=";
	// logging.properties key java.util.logging.FileHandler.formatter
	private static final String FILTER = "java.util.logging.ConsoleHandler.filter=";
	// logging.properties key java.util.logging.FileHandler.formatter
	private static final String FORMATTER = "java.util.logging.ConsoleHandler.formatter=";
	// logging.properties key java.util.logging.FileHandler.formatter
	private static final String ENCODING = "java.util.logging.ConsoleHandler.encoding=";

	// logging.properties default value java.util.logging.ConsoleHandler.level
	private static final String DEFAULT_LEVEL = "ALL";
	// logging.properties default value java.util.logging.ConsoleHandler.filter
	private static final String DEFAULT_FILTER = "";
	// logging.properties default value java.util.logging.ConsoleHandler.formatter
	private static final String DEFAULT_FORMATTER = "java.util.logging.SimpleFormatter";
	// logging.properties default value java.util.logging.ConsoleHandler.encoding
	private static final String DEFAULT_ENCODING = System.getProperty("file.encoding");

	public static void main (String[] args) {
		LoggingProperty property = new ConsoleLoggingProperty();
		System.out.println(property.getLoggingProperty());
	}
}
