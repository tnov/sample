/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.tnk.utils.log.impl;

import net.tnk.utils.log.DateTimeUtil;

public class FileLoggingProperty extends LoggingProperty {

	public static final String HANDLER = "java.util.logging.FileHandler";

	public FileLoggingProperty () {
		this.handler = HANDLER;
		this.encoding = ENCODING + DEFAULT_ENCODING;
		this.level = LEVEL + DEFAULT_LEVEL;
		this.filter = null;
		this.formatter = FORMATTER + DEFAULT_FORMATTER;

		this.append = APPEND + DEFAULT_APPEND;
		this.count = COUNT + DEFAULT_COUNT;
		this.limit = LIMIT + DEFAULT_LIMIT;
		this.pattern = PATTERN + DEFAULT_PATTERN;

		this.host = null;
		this.port = null;
	}

	/*
	 * Handler specific properties.
	 * Describes specific configuration info for Handlers.
	 */
	// logging.properties key java.util.logging.FileHandler.level
	private static final String LEVEL = "java.util.logging.FileHandler.level=";
	// logging.properties key java.util.logging.FileHandler.filter
	private static final String FILTER = "java.util.logging.FileHandler.filter=";
	// logging.properties key java.util.logging.FileHandler.pattern
	private static final String PATTERN = "java.util.logging.FileHandler.pattern=";
	// logging.properties key java.util.logging.FileHandler.limit
	private static final String LIMIT = "java.util.logging.FileHandler.limit=";
	// logging.properties key java.util.logging.FileHandler.count
	private static final String COUNT = "java.util.logging.FileHandler.count=";
	// logging.properties key java.util.logging.FileHandler.formatter
	private static final String FORMATTER = "java.util.logging.FileHandler.formatter=";
	// logging.properties key java.util.logging.FileHandler.formatter
	private static final String ENCODING = "java.util.logging.FiileHandler.encoding=";
	// logging.properties key java.util.logging.FileHandler.append
	private static final String APPEND = "java.util.logging.FileHandler.append=";

	// logging.properties default value java.util.logging.FileHandler.level
	private static final String DEFAULT_LEVEL = "ALL";
	// logging.properties default value java.util.logging.FileHandler.filter
	private static final String DEFAULT_FILTER = "";
	// logging.properties default value java.util.logging.FileHandler.pattern
	// TODO ログファイルの日付表示
	private static final String DEFAULT_PATTERN = "%h/apps%u.log";
	// logging.properties default value java.util.logging.FileHandler.limit
	private static final String DEFAULT_LIMIT = "0";
	// logging.properties default value java.util.logging.FileHandler.count
	private static final String DEFAULT_COUNT = "1";
	// logging.properties default value java.util.logging.FileHandler.formatter
	private static final String DEFAULT_FORMATTER = "java.util.logging.SimpleFormatter";
	// logging.properties default value java.util.logging.FileHandler.encoding
	private static final String DEFAULT_ENCODING = System.getProperty("file.encoding");
	// logging.properties default value java.util.logging.FileHandler.append
	private static final String DEFAULT_APPEND = "false";

	/*
	 * PATTERN
	 * "/" ローカルパス名の区切り文字
     * "%t" システムの一時ディレクトリ
     * "%h" user.home システムプロパティーの値
     * "%g" ログのローテーションを識別する生成番号
     * "%u" 重複を解決する一意の番号
     * "%%" 単一のパーセント符号 % に変換
	 */

//	public static void main (String[] args) {
//		LoggingProperty property = new FileLoggingProperty();
//		System.out.println(property.getLoggingProperty());
//	}


	public static void main (String[] args) {
//		LoggingProperty property = new FileLoggingProperty();
//		System.out.println(property.getLoggingProperty());
		System.out.println("result="+DateTimeUtil.getCurrentLocalDateString(true));
		System.out.println("result="+DateTimeUtil.getCurrentLocalDateTimeString(true));
		System.out.println("result="+DateTimeUtil.getCurrentLocalDateTimeInMillsString(true));
	}

}
