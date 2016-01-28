package jp.dip.fission.util.cron.test;

import jp.dip.fission.util.cron.Cron;
import jp.dip.fission.util.cron.CronCallback;

public class CronTest {

	private static int count = 0;

	public CronTest() {
	}

	public static void main(String[] args) {
		// 仕様
		// スケジュール
		// 秒(必須/0～60):分(必須/0～60):時(必須/0～23):日(必須/1～31):月(必須/1～12):曜日(必須/0:日～6:土) コマンド(必須)
		// *:ワイルドカード(未指定にしたいときに使用)
		// 空白:各項目の区切り
		// -:範囲
		// ,:複数指定
		// /:間隔
		String schedule = "*/5 * * * * *";
		CronCallback callback = new CronCallback() {
			@Override
			public boolean execute() {
				System.out.println("call = " + (count++));
				return false;
			}
		};
		Cron cron = new Cron(schedule, callback,null,-1,2);
		cron.start();
	}
}