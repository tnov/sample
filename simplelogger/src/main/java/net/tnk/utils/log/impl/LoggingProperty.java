/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.tnk.utils.log.impl;

public abstract class LoggingProperty {

	protected String handler = null;


	protected String level = null;
	protected String filter = null;
	protected String pattern = null;
	protected String limit = null;
	protected String count = null;
	protected String formatter = null;
	protected String encoding = null;
	protected String append = null;
	protected String host = null;
	protected String port =null;

	/*
	 * PATTERN
	 * "/" ローカルパス名の区切り文字
     * "%t" システムの一時ディレクトリ
     * "%h" user.home システムプロパティーの値
     * "%g" ログのローテーションを識別する生成番号
     * "%u" 重複を解決する一意の番号
     * "%%" 単一のパーセント符号 % に変換
	 */

	public String getHandler() {
		return handler;
	}


	public void setHandler(String handler) {
		this.handler = handler;
	}

	public String getLevel() {
		return level;
	}


	public void setLevel(String level) {
		this.level = level;
	}


	public String getFilter() {
		return filter;
	}


	public void setFilter(String filter) {
		this.filter = filter;
	}


	public String getPattern() {
		return pattern;
	}


	public void setPattern(String pattern) {
		this.pattern = pattern;
	}


	public String getLimit() {
		return limit;
	}


	public void setLimit(String limit) {
		this.limit = limit;
	}


	public String getCount() {
		return count;
	}


	public void setCount(String count) {
		this.count = count;
	}


	public String getFormatter() {
		return formatter;
	}


	public void setFormatter(String formatter) {
		this.formatter = formatter;
	}


	public String getEncoding() {
		return encoding;
	}


	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}


	public String getAppend() {
		return append;
	}


	public void setAppend(String append) {
		this.append = append;
	}


	public String getHost() {
		return host;
	}


	public void setHost(String host) {
		this.host = host;
	}


	public String getPort() {
		return port;
	}


	public void setPort(String port) {
		this.port = port;
	}

	protected String getLoggingProperty() {
		StringBuilder sb = new StringBuilder(200);
		sb.append(getLevel()==null?"":getLevel()+System.lineSeparator());
		sb.append(getFilter()==null?"":getFilter()+System.lineSeparator());
		sb.append(getPattern()==null?"":getPattern()+System.lineSeparator());
		sb.append(getLimit()==null?"":getLimit()+System.lineSeparator());
		sb.append(getCount()==null?"":getCount()+System.lineSeparator());
		sb.append(getFormatter()==null?"":getFormatter()+System.lineSeparator());
		sb.append(getEncoding()==null?"":getEncoding()+System.lineSeparator());
		sb.append(getAppend()==null?"":getAppend()+System.lineSeparator());
		sb.append(getHost()==null?"":getHost()+System.lineSeparator());
		sb.append(getPort()==null?"":getPort());
		return sb.toString();
	}

//	public static void main (String[] args) {
//		LoggingProperty property = new LoggingProperty();
//		System.out.println(property.getLoggingProperty());
//	}
}
