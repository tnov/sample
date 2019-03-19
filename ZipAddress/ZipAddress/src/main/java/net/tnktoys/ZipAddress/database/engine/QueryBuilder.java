package net.tnktoys.ZipAddress.database.engine;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryBuilder {
	
	Class<?> entityClass = null;
	
	public static <T> SelectQuery<T> select(Class<T> clazz) {
		return new SelectQuery<T>(clazz);
	}
	
	public StringBuilder build() {
		StringBuilder sb = new StringBuilder();
		return sb;
	}
	
	public StringBuilder bind(String str) {
		int index = 0;
		StringBuilder sb = new StringBuilder();
		System.out.println("str="+str);
		Pattern pattern = Pattern.compile("/\\*/?(\n|[^/]|[^*]/)*\\*/",Pattern.DOTALL);
		Matcher matcher = pattern.matcher(str);
		while (matcher.find()) {
			int start = matcher.start();
			int end   = matcher.end();
			sb.append(str.substring(index, start));
			sb.append("?");
			System.out.println("result=" + str.substring(start+2, end-2).trim());
			index = end;
		}
		sb.append(str.substring(index, str.length()));
		return sb;
	}
	
	public QueryBuilder setEntity(Class<?> clazz) {
		entityClass = clazz;
		return this;
	}
}
