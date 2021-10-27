package controller;

import java.util.Scanner;

public class Thread4 extends Thread {

	public boolean stop;
	public boolean work = true;

	public Scanner scanner = new Scanner(System.in);

	@Override
	public void run() {
		// 1. Thread3 제어

		Thread3 thread3 = new Thread3();
		thread3.start();

		try {
			Thread.sleep(3000);
		} catch (Exception e) {
			e.printStackTrace();
		}

		while (!stop) {
			try {
				int user_input1 = scanner.nextInt();

				if (thread3.isStop()) {
					setStop(true);
					break;
				}

				if (user_input1 == 1) {
					// 1. 스레드3 일시정지 시킵니다.
					thread3.setWork(false);

				} else if (user_input1 == 2) {
					// 2. 스레드3 일시정지 해제
					thread3.setWork(true);

				} else if (user_input1 == 3) {
					// 3. 스레드3 종료 & 스레드4 종료
					thread3.setWork(false);
					thread3.setStop(true);
					setStop(true);
					break;
				}
			} catch (Exception e) {
				scanner = new Scanner(System.in);
			}
		}

		return;
	}

	public boolean isStop() {
		return stop;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}

	public boolean isWork() {
		return work;
	}

	public void setWork(boolean work) {
		this.work = work;
	}

}
