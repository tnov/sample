package net.tnktoys.ZipAddress.tools;

import java.io.IOException;

public class PreferenceMain {

	public static void main(String[] args) {
		Preference preference = new Preference();
		Thread thread1 = new Thread(() -> {
			try {
				preference.startWatch();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		thread1.start();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Thread thread2 = new Thread(() -> {
			preference.endWatch();
		});
		thread2.start();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Thread thread3 = new Thread(() -> {
			preference.cancelWatch();
		});
		thread3.start();
	}

}
