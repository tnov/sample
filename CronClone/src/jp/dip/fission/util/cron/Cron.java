package jp.dip.fission.util.cron;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Cron {
	// 仕様
	// スケジュール
	// 秒(必須/0～60):分(必須/0～60):時(必須/0～23):日(必須/1～31):月(必須/1～12):曜日(必須/0:日～6:土) コマンド(必須)
	// *:ワイルドカード(未指定にしたいときに使用)
	// 空白:各項目の区切り
	// -:範囲
	// ,:複数指定
	// /:間隔
	private int wait = 10;
	private boolean isCancel = false;

	private ScheduledExecutorService service;
	private CronCallback callback;
	private CronResult result;
	private String schedule;
	private int pool = 1;
	private long timeout = -1;

	private enum ELEMENT {SECONDS,MINUTES,HOURS,DAYS,MONTHS,WEEKS};

	public Cron(String schedule,CronCallback callback,CronResult result,long timeout,int pool) {
		this.callback = callback;
		this.result = result;
		this.timeout = timeout;
		this.schedule = schedule;
		this.pool = pool;
	}

	public void start() {
		System.out.println("[Service start]");
		service = Executors.newScheduledThreadPool(pool);
		service.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				System.out.println("[Thread start]");
				if (!isCancel) {
					if (onTime(schedule)) {
						System.out.println("[Task begin]");
						Timer timer = null;
						if (timeout > 0) {
							timer = new Timer();
							timer.schedule(new TimerTask() {
								@Override
								public void run() {
									if (result != null) {
										result.cancel();
									}
								}
							}, timeout);
						}
						if(callback.execute()) {
							if (result != null) {
								result.success();
							}
						} else {
							if (result != null) {
								result.fail();
							}
						}
						if (timer != null) {
							timer.cancel();
						}
						System.out.println("[Task end]");
					}
				} else {
					if (result != null) {
						result.cancel();
					}
				}
				System.out.println("[Thread stop]");
			}
		} , 0 , 1 , TimeUnit.SECONDS);
	}

	public void stop() {
		if(!service.isShutdown()) {
			service.shutdown();
		}
		try {
			if (!service.awaitTermination(wait, TimeUnit.SECONDS)) {
				service.shutdownNow();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			service.shutdownNow();
		}
		System.out.println("[Service stop]");
		schedule = null;
		callback = null;
	}

	public void cancel() {
		isCancel = true;
	}

	private boolean onTime(String data) {
		System.out.println("[onTime]");
		Calendar cal = Calendar.getInstance();
		String[] params = data.split(" ");
		boolean isCurrent = false;
		if (0 < params.length && params.length < 7 ) {
			for (ELEMENT element : ELEMENT.values()) {
				// *は判定しない。
				if (!params[element.ordinal()].equals("*")) {
					isCurrent = compare(cal,params[element.ordinal()],element.ordinal());
					// 判定が1つでもNGの場合、実行しない
					if (!isCurrent) {
						break;
					}
				}
			}
		}
		return isCurrent;
	}

	private boolean compare(Calendar cal,String param,int ordinal) {
		System.out.println("[compare]");
		String[] splitInterval = param.split("/");
		// インターバル値
		int interval = -1;
		if (splitInterval.length == 2) {
			interval = Integer.parseInt(splitInterval[1]);
		}
		// 判定用の値
		List<Integer> timeList = new ArrayList<>();
		String[] splitValue = splitInterval[0].split(",");
		for (String value : splitValue) {
			if (value.equals("*")) {
				System.out.println("[*]");
				int size = 0;
				if (ordinal==ELEMENT.SECONDS.ordinal()) {
					size = 60;
				} else if (ordinal==ELEMENT.MINUTES.ordinal()) {
					size = 60;
				} else if (ordinal==ELEMENT.HOURS.ordinal()) {
					size = 24;
				} else if (ordinal==ELEMENT.DAYS.ordinal()) {
					size = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
				} else if (ordinal==ELEMENT.MONTHS.ordinal()) {
					size = 12;
				} else if (ordinal==ELEMENT.WEEKS.ordinal()) {
					size = 7;
				}
				System.out.println("[size]" + size + "[interval]" + interval);
				for (int i = 0 ; i < size ; i=i+interval) {
					timeList.add(i);
				}
			} else {
				System.out.println("[-]");
				String[] splitTime = value.split("-");
				if (splitTime.length == 1) {
					if (splitTime[0].toLowerCase().equals("e")) {
						timeList.add(cal.getActualMaximum(Calendar.DAY_OF_MONTH));
					} else {
						timeList.add(Integer.parseInt(splitTime[0]));
					}
				} else if (splitTime.length == 2) {
					int start = 0;
					if (splitTime[0].toLowerCase().equals("e")) {
						start = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
					} else {
						start = Integer.parseInt(splitTime[0]);
					}
					int end = 0;
					if (splitTime[1].toLowerCase().equals("e")) {
						end = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
					} else {
						end = Integer.parseInt(splitTime[1]);
					}
					for (int i = start ; i <= end ; i++) {
						timeList.add(i);
					}
				}
			}
		}
		// 現在の値
		int value = -1;
		if (ordinal==ELEMENT.SECONDS.ordinal()) {
			value = cal.get(Calendar.SECOND);
		} else if (ordinal==ELEMENT.MINUTES.ordinal()) {
			value = cal.get(Calendar.MINUTE);
		} else if (ordinal==ELEMENT.HOURS.ordinal()) {
			value = cal.get(Calendar.HOUR_OF_DAY);
		} else if (ordinal==ELEMENT.DAYS.ordinal()) {
			value = cal.get(Calendar.DATE);
		} else if (ordinal==ELEMENT.MONTHS.ordinal()) {
			value = cal.get(Calendar.MONTH);
		} else if (ordinal==ELEMENT.WEEKS.ordinal()) {
			value = cal.get(Calendar.DAY_OF_WEEK);
		}
		// 値を比較して一致すればtrue
		for (Integer time : timeList) {
			if (time.equals(value)) {
				return true;
			}
		}
		return false;
	}
}