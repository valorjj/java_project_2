package controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.TreeSet;

import model.Song;
import model.Song_AscendingOrder;

public class Customer {

	private String filepath_song_list = "./src/file/song_list_info.txt";
	private String filepath_reverve_list = "./src/file/reserve_list.txt";

	public Scanner scanner = new Scanner(System.in);

	public TreeSet<Song> customer_song_info_list = new TreeSet<Song>(new Song_AscendingOrder());
	public TreeSet<Song> customer_reserve_list = new TreeSet<Song>(new Song_AscendingOrder());

	private final int STAR_PRINT = 60;

	public void customer_menu() {

		while (true) {

			try {
				STAR(STAR_PRINT);
				System.out.println("1. 발라드 | 2. 댄스 | 3. 힙합 | 4. 예약확인 | 5. 뒤로가기 ");
				STAR(STAR_PRINT);
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
					customer_print_reserve_list();

				}
				if (user_input1 == 5) {
					// 5. while 문 탈출
					break;
				}

			} catch (Exception e) {
				scanner = new Scanner(System.in);
				STAR(STAR_PRINT);
				System.out.println("[알림] 오류 발생 " + e);
			}
		}
	}

	private void customer_print_reserve_list() {
		// 1. 예약 목록을 출력합니다.
		STAR(STAR_PRINT);
		for (Song song : customer_reserve_list) {
			System.out.println(
					"제목 : " + song.getTitle() + " | 가수 : " + song.getSinger() + " | 예약 번호 : " + song.getNumber());
		}

		try {
			STAR(STAR_PRINT);
			System.out.println("예약은 첫곡부터 연속으로 재생됩니다. ");
			while (true) {
				STAR(STAR_PRINT);
				System.out.print("1. 예약목록 시작 | 2. 예약 취소 | 3.  예약 초기화 | 4. 뒤로 가기 : ");
				int user_input1 = scanner.nextInt();
				if (user_input1 == 1) {
					System.out.println("[예약목록]");
					Thread4 thread4 = new Thread4();
					thread4.start();
					thread4.join();
				} else if (user_input1 == 2) {
					System.out.println("취소할 곡 번호 : ");
					int user_input2 = scanner.nextInt();
					for (Song song : customer_reserve_list) {
						if (Integer.parseInt(song.getNumber()) == user_input2) {
							customer_reserve_list.remove(song);
							break;
						}
					}

					customer_write_reserve_list();

				} else if (user_input1 == 3) {
					customer_reserve_list.clear();
					break;
				} else if (user_input1 == 4) {
					// 
					break;
				} else {
					STAR(STAR_PRINT);
					System.err.println("[알림] 유효한 입력이 아닙니다. ");
				}
			}

		} catch (Exception e) {
			scanner = new Scanner(System.in);
			STAR(STAR_PRINT);
			System.err.println("[알림] 오류발생 " + e);
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
		STAR(STAR_PRINT);

		while (true) {
			try {
				STAR(STAR_PRINT);
				for (Song song : customer_song_info_list) {
					if (Integer.parseInt(song.getNumber()) < 2000 && Integer.parseInt(song.getNumber()) > 1000) {
						// 1. 발라드 리스트를 출력합니다.
						System.out.println("제목 : " + song.getTitle() + " | 가수 : " + song.getSinger() + " | 번호 : "
								+ song.getNumber());
					}
				}
				STAR(STAR_PRINT);
				System.out.print("1. 시작 | 2. 예약 | 3. 뒤로가기: ");
				int user_input2 = scanner.nextInt();

				if (user_input2 == 1) {
					// 1. 가사 출력 시작
					Thread2 thread2 = new Thread2();
					thread2.start();
					thread2.join();

				}

				else if (user_input2 == 2) {
					// 2. 예약 화면 출력
					STAR(STAR_PRINT);
					System.out.println("[알림] 예약하실 곡 번호 : ");
					try {
						int user_input3 = scanner.nextInt();
						for (Song song : customer_song_info_list) {
							if (Integer.parseInt(song.getNumber()) == user_input3) {
								// 1. 일치하는 객체를 리스트에 집어넣습니다.
								customer_reserve_list.add(song);
								customer_write_reserve_list();
								break;
							}
						}
					}

					catch (Exception e) {
						STAR(STAR_PRINT);
						scanner = new Scanner(System.in);
						System.err.println("[알림] 오류 발생" + e);
					}

				}

				else if (user_input2 == 3) {
					break;
				}

				else {
					STAR(STAR_PRINT);
					System.err.println("[알림] 유효하지 않은 입력입니다. ");
				}
			}

			catch (Exception e) {
				STAR(STAR_PRINT);
				scanner = new Scanner(System.in);
				System.err.println("[알림] 오류 발생" + e);
			}
		}
	}

	public void customer_print_dance_list() {
		STAR(STAR_PRINT);

		while (true) {
			try {
				STAR(STAR_PRINT);
				for (Song song : customer_song_info_list) {
					if (Integer.parseInt(song.getNumber()) < 3000 && Integer.parseInt(song.getNumber()) > 2000) {
						// 1. 댄스 리스트를 출력합니다.
						System.out.println("제목 : " + song.getTitle() + " | 가수 : " + song.getSinger() + " | 번호 : "
								+ song.getNumber());
					}
				}
				STAR(STAR_PRINT);
				System.out.print("1. 시작 | 2. 예약 | 3. 뒤로가기: ");
				int user_input2 = scanner.nextInt();

				if (user_input2 == 1) {
					// 1. 가사 출력 시작
					Thread2 thread2 = new Thread2();
					thread2.start();
					thread2.join();

				} else if (user_input2 == 2) {
					// 2. 예약 화면 출력
					STAR(STAR_PRINT);
					System.out.println("예약하실 곡 번호 : ");
					try {
						int user_input3 = scanner.nextInt();
						for (Song song : customer_song_info_list) {
							if (Integer.parseInt(song.getNumber()) == user_input3) {
								// 1. 일치하는 객체를 리스트에 집어넣습니다.
								customer_reserve_list.add(song);
								customer_write_reserve_list();
								break;
							}
						}

					} catch (Exception e) {
						scanner = new Scanner(System.in);
					}

				} else if (user_input2 == 3) {
					break;
				} else {
					STAR(STAR_PRINT);
					System.err.println("[알림] 유효하지 않은 입력입니다. ");
				}

			} catch (Exception e) {
				STAR(STAR_PRINT);
				scanner = new Scanner(System.in);
				System.err.println("[알림] 오류 발생" + e);
			}

		}
	}

	public void customer_print_hiphop_list() {
		STAR(STAR_PRINT);

		while (true) {
			try {
				STAR(STAR_PRINT);
				for (Song song : customer_song_info_list) {
					if (Integer.parseInt(song.getNumber()) < 4000 && Integer.parseInt(song.getNumber()) > 3000) {
						// 1. 힙합 리스트를 출력합니다.
						System.out.println("제목 : " + song.getTitle() + " | 가수 : " + song.getSinger() + " | 번호 : "
								+ song.getNumber());
					}
				}
				STAR(STAR_PRINT);
				System.out.print("1. 시작 | 2. 예약 | 3. 뒤로가기: ");
				int user_input2 = scanner.nextInt();

				if (user_input2 == 1) {
					// 1. 가사 출력 시작
					Thread2 thread2 = new Thread2();
					thread2.start();
					thread2.join();
				} else if (user_input2 == 2) {
					// 2. 예약 화면 출력
					STAR(STAR_PRINT);
					System.out.print("예약하실 곡 번호 : ");
					try {
						int user_input3 = scanner.nextInt();
						for (Song song : customer_song_info_list) {
							if (Integer.parseInt(song.getNumber()) == user_input3) {
								// 1. 일치하는 객체를 리스트에 집어넣습니다.
								customer_reserve_list.add(song);
								customer_write_reserve_list();
								break;
							}
						}
					} catch (Exception e) {
						scanner = new Scanner(System.in);
					}

				} else if (user_input2 == 3) {
					break;
				} else {
					STAR(STAR_PRINT);
					System.err.println("[알림] 유효하지 않은 입력입니다. ");
				}

			} catch (Exception e) {
				STAR(STAR_PRINT);
				scanner = new Scanner(System.in);
				System.err.println("[알림] 오류 발생" + e);
			}
		}
	}

	private void customer_write_reserve_list() throws IOException {

		// 1. 리스트에 저장된 데이터를 revserve_list.txt 에 담습니다.

		FileOutputStream fos = new FileOutputStream(filepath_reverve_list);
		String str = "";
		for (Song song : customer_reserve_list) {
			str += song.getTitle() + "," + song.getSinger() + "," + song.getNumber() + "," + song.getCategory() + "\n";
		}
		fos.write(str.getBytes());
		fos.flush();
		fos.close();

	}

	public static void STAR(int STAR_PRINT) {
		for (int i = 0; i < STAR_PRINT; i++) {
			System.out.print("*");
		}
		System.out.println();
	}

}
