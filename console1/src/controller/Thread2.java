package controller;

import java.util.Scanner;

public class Thread2 extends Thread {

	public boolean stop;
	public boolean work = true;

	public Scanner scanner = new Scanner(System.in);

	@Override
	public void run() {

		System.out.println("[알림] 10초 내 입력하세요. ");
		Thread1 thread1 = new Thread1(); // 1. Thread1 은 가사를 출력하는 스레드입니다.
		thread1.start();

		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}

		while (!stop) {
			try {
				// System.out.println("1. 일시정지 2. 재생 3. 종료 ");
				int user_input1 = scanner.nextInt();

				if (thread1.isStop()) {
					System.out.println("스레드1,2 종료 ");
					setStop(true);
					return;
				}

				if (user_input1 == 1) {
					// 1. 스레드1 일시정지 시킵니다.
					thread1.setWork(false);

				} else if (user_input1 == 2) {
					// 2. 스레드2 일시정지 해제
					thread1.setWork(true);

				} else if (user_input1 == 3) {
					// 3. 스레드1, 2 종료
					thread1.setWork(false);
					thread1.setStop(true);
					setStop(true);
					
				}

			} catch (Exception e) {
				scanner = new Scanner(System.in);
				System.err.println("[알림] 유효한 입력이 아닙니다. " + e);
				e.printStackTrace();
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
