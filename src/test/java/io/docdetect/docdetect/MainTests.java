package io.docdetect.docdetect;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class MainTests {
	private class T {
		public int count = 0;
		public void increase() {
			Object o = new Object();
			synchronized (o) {
				count++;
			}
		}
	}
	@Test
	public void TestUnit() {
		T a = new T();
		new Thread() {
			@Override
			public void run() {
				for (int i = 0;i < 100;i++) {
					System.out.println("a=" + a.count);
					a.increase();
				}
			}
		}.start();
		
		new Thread() {
			@Override
			public void run() {
				for (int i = 0;i < 100;i++) {
					System.out.println("b=" + a.count);
					a.increase();
				}
			}
		}.start();
	}
}
