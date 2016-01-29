package jp.dip.fission.template;

import java.io.File;

public class TempleteObject {

	// TODO Velocityの組み込み
	//       Default値のセット

	private String type;

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ

	}

	public static File getTemplateFile(String filePath) {
		File templateFile = new File(filePath);
		return templateFile;
	}

	public static String parse(String test) {
		return test;
	}

	// TODO やること
	//
	//  パラメータのマッピング→Velociyに任せる。
	//  テキストであれば何でもいける。
	//  他の仕組みとは混ぜない
	//  動的なソース生成時に使えるか？
	//  動的言語を使用するか？
	//  (コスト高い)
}
