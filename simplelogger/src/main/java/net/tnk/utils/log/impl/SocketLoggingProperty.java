/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.tnk.utils.log.impl;

public class SocketLoggingProperty extends LoggingProperty {

	public static final String HANDLER = "java.util.logging.SocketHandler";

	public SocketLoggingProperty () {
		this.handler = HANDLER;
		this.encoding = ENCODING + DEFAULT_ENCODING;
		this.level = LEVEL + DEFAULT_LEVEL;
		this.filter = null;
		this.formatter = FORMATTER + DEFAULT_FORMATTER;

		this.append = null;
		this.count = null;
		this.limit = null;
		this.pattern = null;

		this.host = HOST+DEFAULT_HOST;
		this.port = PORT+DEFAULT_PORT;
	}

	/*
	 * Handler specific properties.
	 * Describes specific configuration info for Handlers.
	 */
	// logging.properties key java.util.logging.SocketHandler.level
	private static final String LEVEL = "java.util.logging.SocketHandler.level=";
	// logging.properties key java.util.logging.SocketHandler.filter
	private static final String FILTER = "java.util.logging.SocketHandler.filter=";
	// logging.properties key java.util.logging.SocketHandler.formatter
	private static final String FORMATTER = "java.util.logging.SocketHandler.formatter=";
	// logging.properties key java.util.logging.SocketHandler.formatter
	private static final String ENCODING = "java.util.logging.SocketHandler.encoding=";
	// logging.properties key java.util.logging.SocketHandler.formatter
	private static final String HOST = "java.util.logging.SocketHandler.host=";
	// logging.properties key java.util.logging.SocketHandler.formatter
	private static final String PORT = "java.util.logging.SocketHandler.port=";

	// logging.properties default value java.util.logging.SocketHandler.level
	private static final String DEFAULT_LEVEL = "ALL";
	// logging.properties default value java.util.logging.SocketHandler.filter
	private static final String DEFAULT_FILTER = "";
	// logging.properties default value java.util.logging.SocketHandler.formatter
	private static final String DEFAULT_FORMATTER = "java.util.logging.XMLFormatter";
	// logging.properties default value java.util.logging.SocketHandler.encoding
	private static final String DEFAULT_ENCODING = System.getProperty("file.encoding");
	// logging.properties default value java.util.logging.SocketHandler.encoding
	private static final String DEFAULT_HOST = "127.0.0.1";
	// logging.properties default value java.util.logging.SocketHandler.append
	private static final String DEFAULT_PORT = "8080";

	public static void main (String[] args) {
		LoggingProperty property = new SocketLoggingProperty();
		System.out.println(property.getLoggingProperty());
	}
}
