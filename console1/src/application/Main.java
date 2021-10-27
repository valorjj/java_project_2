package application;

import java.util.Scanner;

import controller.Customer;
import controller.Manager;
import model.Karoke_Interface;

public class Main implements Karoke_Interface {

	// 내려받은 파일 수정해도 올릴 수 있나??

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		boolean flag = true;

		final int STAR_PRINT = 60;

		while (flag) {
			STAR(STAR_PRINT);
			System.out.println("노래방에 오신걸 환영합니다. ");
			STAR(STAR_PRINT);
			System.out.println("발라드 | 댄스 | 힙합");
			STAR(STAR_PRINT);
			System.out.println("1. 시작 2. 종료 ");
			STAR(STAR_PRINT);
			System.out.print("선택 : ");

			try {
				Customer customer = new Customer();
				Manager manager = new Manager();

				int user_select = scanner.nextInt();

				STAR(STAR_PRINT);

				if (user_select == 1) {
					// 1. 노래 목록을 출력하는 메소드 호출
					customer.customer_menu();

				} else if (user_select == 2) {
					// 2. 프로그램 종료
					System.out.println("[알림] 프로그램 종료");
					break;

				} else if (user_select == 9999) {
					// 3. 관리자모드 실행
					manager.manager_menu();

				}

			} catch (Exception e) {
				System.err.println("[알림] 유효하지 않은 입력값입니다. ");
				scanner = new Scanner(System.in);
			}

		}

	}

	public static void STAR(int STAR_PRINT) {
		for (int i = 0; i < STAR_PRINT; i++) {
			System.out.print("*");
		}
		System.out.println();
	}

	@Override
	public void data_input() {
		// TODO Auto-generated method stub

	}

	@Override
	public void data_print() {
		// TODO Auto-generated method stub

	}

	@Override
	public void data_modify() {
		// TODO Auto-generated method stub

	}

	@Override
	public void data_delete() {
		// TODO Auto-generated method stub

	}

}
