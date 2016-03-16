package jp.dip.fission.SampleRestApplication.tools;

public class PeriodGenerator {

	private static long DEFAULT_PERIOD = 1000l*5l;

	public static long createPeriod() {
		return createPeriod(DEFAULT_PERIOD);
	}

	public static long createPeriod(long millis) {
		long currentMillis = System.currentTimeMillis();
		return currentMillis + millis;
	}

	public static long createCurrent() {
		long currentMillis = System.currentTimeMillis();
		return currentMillis;
	}
}
