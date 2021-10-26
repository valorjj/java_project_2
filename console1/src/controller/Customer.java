package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import model.Song;

public class Customer {

	/*
	 * 문제점 : 예약번호를 입력받은 뒤 목록 출력 실패
	 * 
	 * 
	 */

	private String filepath_song_list = "./src/file/song_list_info.txt";

	public Scanner scanner = new Scanner(System.in);

	public ArrayList<Song> customer_song_info_list = new ArrayList<Song>();
	public ArrayList<Song> customer_reserve_list = new ArrayList<Song>();

	public void customer_menu() {

		while (true) {

			try {
				System.out.println("1. 발라드 | 2. 댄스 | 3. 힙합 | 4. 예약확인 | 5. 뒤로가기 ");
				System.out.println("선택 : ");
				int user_input1 = scanner.nextInt();

				customer_read_song(); // 1. customer_song_info_list 에 인스턴스가 담겼습니다.

				if (user_input1 == 1) {
					// 1. 발라드 목록 출력 후, 선택한 곡 가사 출력
					customer_print_ballad_list();
				}
				if (user_input1 == 2) {
					// 2. 댄스
					customer_print_dance_list();
				}
				if (user_input1 == 3) {
					// 3. 힙합
					customer_print_hiphop_list();
				}
				if (user_input1 == 4) {
					// 4. 예약 곡 확인

				}
				if (user_input1 == 5) {
					// 5. while 문 탈출
					break;
				}

				// 1. 사용이 끝난 리스트는 초기화 시킵니다.
				customer_song_info_list.clear();
				customer_reserve_list.clear();

			} catch (Exception e) {
				scanner = new Scanner(System.in);
				System.out.println("[알림] 오류 발생 " + e);
			}
		}

	}

	private void customer_read_song() throws IOException {
		// 1. txt file ----> list
		FileInputStream fis = new FileInputStream(filepath_song_list);
		int size = fis.available();
		byte[] bytelist = new byte[size];
		fis.read(bytelist);

		String str = new String(bytelist);

		String[] tmp_list1 = str.split("\n");

		for (String s1 : tmp_list1) {
			if (!s1.equals(" ")) {
				String[] tmp_list2 = s1.split(",");
				customer_song_info_list.add(new Song(tmp_list2[0], tmp_list2[1], tmp_list2[2], tmp_list2[3]));
			}
		}

		fis.close();
	}

	public void customer_print_ballad_list() {

		for (Song song : customer_song_info_list) {
			if (Integer.parseInt(song.getNumber()) < 2000 && Integer.parseInt(song.getNumber()) > 1000) {
				// 1. 발라드 리스트를 출력합니다.
				System.out.println(
						"제목 : " + song.getTitle() + " | 가수 : " + song.getSinger() + " | 번호 : " + song.getNumber());
			}
		}

		while (true) {
			try {

				System.out.print("1. 시작 | 2. 예약 | 3. 뒤로가기: ");
				int user_input2 = scanner.nextInt();

				if (user_input2 == 1) {
					// 1. 가사 출력 시작
					Thread2 thread2 = new Thread2();
					thread2.start();
					thread2.join();

				} else if (user_input2 == 2) {
					// 2. 예약 화면 출력
					System.out.println("[알림] 예약 화면입니다. ");
					System.out.println("예약하실 곡 번호 : ");
					try {
						int user_input3 = scanner.nextInt();
						for (Song song : customer_song_info_list) {
							if (Integer.parseInt(song.getNumber()) == user_input3) {
								// 1. 일치하는 객체를 리스트에 집어넣습니다.
								customer_reserve_list.add(song);
							}
						}

					} catch (Exception e) {
						scanner = new Scanner(System.in);
					}

				} else if (user_input2 == 3) {
					break;
				} else {
					System.err.println("[알림] 유효하지 않은 입력입니다. ");
				}

			} catch (Exception e) {
				scanner = new Scanner(System.in);
			}

		}

	}

	public void customer_print_dance_list() {

		for (Song song : customer_song_info_list) {
			if (Integer.parseInt(song.getNumber()) < 3000 && Integer.parseInt(song.getNumber()) > 2000) {
				// 1. 댄스 리스트를 출력합니다.
				System.out.println(
						"제목 : " + song.getTitle() + " | 가수 : " + song.getSinger() + " | 번호 : " + song.getNumber());
			}
		}

		while (true) {
			try {

				System.out.print("1. 시작 | 2. 예약 | 3. 뒤로가기: ");
				int user_input2 = scanner.nextInt();

				if (user_input2 == 1) {
					// 1. 가사 출력 시작
					Thread2 thread2 = new Thread2();
					thread2.start();
					thread2.join();

				} else if (user_input2 == 2) {
					// 2. 예약 화면 출력
					System.out.println("[알림] 예약 화면입니다. ");
					System.out.println("예약하실 곡 번호 : ");
					try {
						int user_input3 = scanner.nextInt();
						for (Song song : customer_song_info_list) {
							if (Integer.parseInt(song.getNumber()) == user_input3) {
								// 1. 일치하는 객체를 리스트에 집어넣습니다.
								customer_reserve_list.add(song);
							}
						}

					} catch (Exception e) {
						scanner = new Scanner(System.in);
					}

				} else if (user_input2 == 3) {
					break;
				} else {
					System.err.println("[알림] 유효하지 않은 입력입니다. ");
				}

			} catch (Exception e) {
				scanner = new Scanner(System.in);
			}

		}
	}

	public void customer_print_hiphop_list() {

		for (Song song : customer_song_info_list) {
			if (Integer.parseInt(song.getNumber()) < 4000 && Integer.parseInt(song.getNumber()) > 3000) {
				// 1. 힙합 리스트를 출력합니다.
				System.out.println(
						"제목 : " + song.getTitle() + " | 가수 : " + song.getSinger() + " | 번호 : " + song.getNumber());
			}
		}

		while (true) {
			try {

				System.out.print("1. 시작 | 2. 예약 | 3. 뒤로가기: ");
				int user_input2 = scanner.nextInt();

				if (user_input2 == 1) {
					// 1. 가사 출력 시작
					Thread2 thread2 = new Thread2();
					thread2.start();
					thread2.join();

				} else if (user_input2 == 2) {
					// 2. 예약 화면 출력
					System.out.println("[알림] 예약 화면입니다. ");
					System.out.println("예약하실 곡 번호 : ");
					try {
						int user_input3 = scanner.nextInt();
						for (Song song : customer_song_info_list) {
							if (Integer.parseInt(song.getNumber()) == user_input3) {
								// 1. 일치하는 객체를 리스트에 집어넣습니다.
								customer_reserve_list.add(song);
							}
						}

					} catch (Exception e) {
						scanner = new Scanner(System.in);
					}

				} else if (user_input2 == 3) {
					break;
				} else {
					System.err.println("[알림] 유효하지 않은 입력입니다. ");
				}

			} catch (Exception e) {
				scanner = new Scanner(System.in);
			}

		}
	}

}
