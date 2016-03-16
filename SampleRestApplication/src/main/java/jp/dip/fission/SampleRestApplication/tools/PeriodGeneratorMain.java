package jp.dip.fission.SampleRestApplication.tools;

public class PeriodGeneratorMain {

	public static void main(String[] args) {
		for (int i = 1 ; i <= 100 ; i++) {
			System.out.println(PeriodGenerator.createCurrent() + "/" + PeriodGenerator.createPeriod(1000));
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
