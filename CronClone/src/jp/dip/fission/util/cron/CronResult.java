package jp.dip.fission.util.cron;

public interface CronResult {
	public void success();
	public void fail();
	public void cancel();
	public void timeout();
}
