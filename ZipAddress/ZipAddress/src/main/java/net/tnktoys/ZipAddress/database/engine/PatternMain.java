package net.tnktoys.ZipAddress.database.engine;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternMain {

	public static void main(String[] args) {
		int index = 0;
		StringBuilder sb = new StringBuilder();
		String str = "select * from user where id = /* id */";
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
		System.out.println(sb.toString());
	}
}
