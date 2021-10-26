package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

import model.Lyric;
import model.Song;

public class Manager {

	private String manager_id = "admin";
	private String manager_pw = "1234";

	// 파일의 상대경로

	private String filepath_ballad = "./src/file/ballad_lyrics.txt";
	private String filepath_dance = "./src/file/dance_lyrics.txt";
	private String filepath_hiphop = "./src/file/hiphop_lyrics.txt";
	private String filepath_song_list = "./src/file/song_list_info.txt";

	public Scanner scanner = new Scanner(System.in);

	public ArrayList<Lyric> manager_ballad_list = new ArrayList<Lyric>();
	public ArrayList<Lyric> manager_dance_list = new ArrayList<Lyric>();
	public ArrayList<Lyric> manager_hiphop_list = new ArrayList<Lyric>();

	public ArrayList<Song> manager_song_list = new ArrayList<Song>();

	public void manager_menu() {

		try {

			if (manager_logIn()) {

				System.out.println("[관리자] 1. 곡 수정 2. 뒤로가기");
				System.out.print("선택 : ");
				int manager_select = scanner.nextInt();
				if (manager_select == 1) {
					// 1. [관리자] 곡 정보(제목, 가수, 번호, 장르) 수정
					manager_edit();

				} else if (manager_select == 2) {
					// 2. 로그아웃
					return;
				} else {
					System.out.println("[알림] 유효하지 않은 입력입니다. ");
				}

			}

		} catch (Exception e) {
			scanner = new Scanner(System.in);
			System.err.println("[오류발생] 관리자에게 문의바랍니다. " + e);
		}

	}

	private void manager_edit() {

		while (true) {
			System.out.print("1. 추가 | 2. 삭제 | 3. 뒤로가기 : ");
			try {
				int manager_select = scanner.nextInt();
				if (manager_select == 1) {
					// 1. [관리자] 곡 & 가사 추가
					System.out.println("[분류] 발라드 : 1001~1999 | 댄스 : 2001~2999 | 힙합 : 3001~3999");
					System.out.print("제목 : ");
					String title = scanner.next();
					System.out.print("아티스트 : ");
					String artist = scanner.next();
					System.out.print("예약번호 : ");
					String number = scanner.next();
					System.out.print("장르 : ");
					String genre = scanner.next();
					int int_number = Integer.parseInt(number);
					if (int_number < 1000 || int_number > 4000) {
						System.out.println("[경고] 유효한 입력이 아닙니다. ");
					} else {
						manager_read_song(); // 1. 이 부분에서 manager_song_list 생성된 객체가 인수로 계속해서 전달됩니다.

						Song song = new Song(title, artist, number, genre);
						manager_song_list.add(song); // 2. 새롭게 입력된 객체를 리스트에 추가합니다.

						String newString = title + "," + artist + "," + number + "," + genre + "\n";

						manager_write_single_data(newString, manager_song_list); // 3. 새롭게 입력된 객체를 txt 파일에 추가합니다.

						manager_write_ArrayList();

						// 1. idx 에 따른 가사 추가;
						int idx = int_number / 1000; // 1 : 발라드, 2 : 댄스, 3 : 힙합
						scanner.nextLine();

						manager_write_lyrics(idx, int_number);
						manager_song_list.clear();

					}

				} else if (manager_select == 2) {
					// 2. [관리자] 곡 & 가사 삭제

					manager_read_song(); // 1. txt 파일을 읽고 리스트에 저장합니다.
					manager_print_song(); // 2. 목록을 출력합니다.

					System.out.println("삭제할 곡 번호 : ");
					String number = scanner.next();
					int int_number = Integer.parseInt(number);
					int idx = int_number / 1000;
					manager_delete_song(int_number, idx);

				} else if (manager_select == 3) {
					break;

				} else {
					System.err.println("[알림] 유효하지 않은 입력입니다. ");
				}

			} catch (Exception e) {
				scanner = new Scanner(System.in);
			}
		}
	}

	private void manager_print_song() {
		for (Song song : manager_song_list) {
			System.out.println(
					"제목 : " + song.getTitle() + " | 가수 : " + song.getSinger() + "| 예약번호 : " + song.getNumber());
		}
	}

	public void manager_delete_song(int number, int idx) throws IOException {

		// 1. 곡 정보를 삭제합니다.

		String string_number = Integer.toString(number);

		for (Song song : manager_song_list) {
			if (song.getNumber().equals(string_number)) {
				manager_song_list.remove(song);
				break;
			}
		}

		manager_write_ArrayList();

		manager_song_list.clear();

		if (idx == 1) {

			// 1. 발라드 가사 삭제 후 정렬
			manager_read_ballad_lyric();
			for (Lyric lyric : manager_ballad_list) {
				if (lyric.getNumber() == number) {
					manager_ballad_list.remove(lyric);
					break;
				}
			}

			Collections.sort(manager_ballad_list, new Comparator<Lyric>() {

				@Override
				public int compare(Lyric o1, Lyric o2) {
					return o1.getNumber() - o2.getNumber();
				}
			});
			manager_write_ballad();

			manager_ballad_list.clear();
		}
		if (idx == 2) {
			// 2. 댄스 가사 삭제 후 정렬
			manager_read_dance_lyric();
			for (Lyric lyric : manager_dance_list) {
				if (lyric.getNumber() == number) {
					manager_dance_list.remove(lyric);
					break;
				}
			}

			Collections.sort(manager_dance_list, new Comparator<Lyric>() {

				@Override
				public int compare(Lyric o1, Lyric o2) {
					return o1.getNumber() - o2.getNumber();
				}
			});
			manager_write_dance();

			manager_dance_list.clear();
		}
		if (idx == 3) {
			// 3. 힙합 가사 삭제 후 정렬
			manager_read_hiphop_lyric();
			for (Lyric lyric : manager_hiphop_list) {
				if (lyric.getNumber() == number) {
					manager_hiphop_list.remove(lyric);
					break;
				}
			}
			Collections.sort(manager_hiphop_list, new Comparator<Lyric>() {

				@Override
				public int compare(Lyric o1, Lyric o2) {
					return o1.getNumber() - o2.getNumber();
				}
			});
			manager_write_hiphop();

			manager_hiphop_list.clear();
		}
	}

	public void manager_write_lyrics(int idx, int number) {
		// 1. 발라드 2. 댄스 3. 힙합
		try {
			if (idx == 1) {
				// 1. 발라드 가사 추가
				System.out.println("가사 : ");
				String lyrics = scanner.nextLine(); // 0. 가사를 입력받습니다.
				manager_read_ballad_lyric(); // 1. manager_ballad_list 객체에 가사 데이터를 담습니다.

				Lyric lyric = new Lyric(number, lyrics);
				manager_ballad_list.add(lyric); // 2. 새롭게 입력된 데이터를 리스트에 추가시킵니다.
				manager_write_ballad();// 3. 리스트에 있는 인스턴스를 txt 파일에 덮어씌웁니다.
				manager_ballad_list.clear();

			}
			if (idx == 2) {
				// 1. 댄스 가사 추가
				System.out.println("가사 : ");
				String lyrics = scanner.nextLine(); // 0. 가사를 입력받습니다.
				manager_read_dance_lyric(); // 1. manager_ballad_list 객체에 가사 데이터를 담습니다.

				Lyric lyric = new Lyric(number, lyrics);
				manager_dance_list.add(lyric); // 2. 새롭게 입력된 데이터를 리스트에 추가시킵니다.
				manager_write_dance();// 3. 리스트에 있는 인스턴스를 txt 파일에 덮어씌웁니다.
				manager_dance_list.clear();

			}
			if (idx == 3) {
				// 1. 힙합 가사 추가
				System.out.println("가사 : ");
				String lyrics = scanner.nextLine(); // 0. 가사를 입력받습니다.
				manager_read_hiphop_lyric(); // 1. manager_ballad_list 객체에 가사 데이터를 담습니다.

				Lyric lyric = new Lyric(number, lyrics);
				manager_hiphop_list.add(lyric); // 2. 새롭게 입력된 데이터를 리스트에 추가시킵니다.
				manager_write_hiphop();// 3. 리스트에 있는 인스턴스를 txt 파일에 덮어씌웁니다.
				manager_hiphop_list.clear();

			}

		} catch (Exception e) {
			scanner = new Scanner(System.in);
		}

	}

	public void manager_write_ballad() throws IOException {
		// 1. 리스트에 있는 값을 txt 파일로 옮깁니다.
		FileOutputStream fos = new FileOutputStream(filepath_ballad);

		// 2. 오름차순으로 정렬합니다.
		Collections.sort(manager_ballad_list, new Comparator<Lyric>() {
			@Override
			public int compare(Lyric o1, Lyric o2) {
				return o1.getNumber() - o2.getNumber();
			}

		});

		String str = "";

		for (Lyric lyric : manager_ballad_list) {
			str += Integer.toString(lyric.getNumber()) + "&" + lyric.getLyrics() + "%\n";
		}

		fos.write(str.getBytes());
		fos.flush();
		fos.close();

	}

	public void manager_write_dance() throws IOException {
		// 1. 리스트에 있는 값을 txt 파일로 옮깁니다.
		FileOutputStream fos = new FileOutputStream(filepath_dance);

		// 2. 오름차순으로 정렬합니다.
		Collections.sort(manager_dance_list, new Comparator<Lyric>() {
			@Override
			public int compare(Lyric o1, Lyric o2) {
				return o1.getNumber() - o2.getNumber();
			}

		});

		String str = "";

		for (Lyric lyric : manager_dance_list) {
			str += Integer.toString(lyric.getNumber()) + "&" + lyric.getLyrics() + "%\n";
		}

		fos.write(str.getBytes());
		fos.flush();
		fos.close();

	}

	public void manager_write_hiphop() throws IOException {
		// 1. 리스트에 있는 값을 txt 파일로 옮깁니다.
		FileOutputStream fos = new FileOutputStream(filepath_hiphop);

		// 2. 오름차순으로 정렬합니다.
		Collections.sort(manager_hiphop_list, new Comparator<Lyric>() {
			@Override
			public int compare(Lyric o1, Lyric o2) {
				return o1.getNumber() - o2.getNumber();
			}
		});

		String str = "";

		for (Lyric lyric : manager_hiphop_list) {
			str += Integer.toString(lyric.getNumber()) + "&" + lyric.getLyrics() + "%\n";
		}

		fos.write(str.getBytes());
		fos.flush();
		fos.close();

	}

	public void manager_read_ballad_lyric() throws IOException {
		// 1. txt file ---> manager_ballad_list 데이터 옮겨 담기
		FileInputStream fis = new FileInputStream(filepath_ballad);
		int size = fis.available();
		byte[] bytelist = new byte[size];
		fis.read(bytelist);

		String str = new String(bytelist);

		String[] lyric_list1 = str.split("%\n");

		for (String s1 : lyric_list1) {
			if (!s1.equals(" ")) {
				String[] lyric_list2 = s1.split("&");
				int number = Integer.parseInt(lyric_list2[0]);
				String lyrics = lyric_list2[1];
				manager_ballad_list.add(new Lyric(number, lyrics)); // 1. 리스트에 옮겨담는다.
			}
		}
		fis.close();
	}

	public void manager_read_dance_lyric() throws IOException {
		// 1. txt file ---> manager_ballad_list 데이터 옮겨 담기
		FileInputStream fis = new FileInputStream(filepath_dance);
		int size = fis.available();
		byte[] bytelist = new byte[size];
		fis.read(bytelist);

		String str = new String(bytelist);

		String[] lyric_list1 = str.split("%\n");

		for (String s1 : lyric_list1) {
			if (!s1.equals(" ")) {
				String[] lyric_list2 = s1.split("&");
				int number = Integer.parseInt(lyric_list2[0]);
				String lyrics = lyric_list2[1];
				manager_dance_list.add(new Lyric(number, lyrics)); // 1. 리스트에 옮겨담는다.
			}
		}
		fis.close();
	}

	public void manager_read_hiphop_lyric() throws IOException {
		// 1. txt file ---> manager_ballad_list 데이터 옮겨 담기
		FileInputStream fis = new FileInputStream(filepath_hiphop);
		int size = fis.available();
		byte[] bytelist = new byte[size];
		fis.read(bytelist);

		String str = new String(bytelist);

		String[] lyric_list1 = str.split("%\n");

		for (String s1 : lyric_list1) {
			if (!s1.equals(" ")) {
				String[] lyric_list2 = s1.split("&");
				int number = Integer.parseInt(lyric_list2[0]);
				String lyrics = lyric_list2[1];
				manager_hiphop_list.add(new Lyric(number, lyrics)); // 1. 리스트에 옮겨담는다.
			}
		}
		fis.close();
	}

	public void manager_write_ArrayList() throws IOException {

		// 1. ArrayList ---> txt file

		FileOutputStream fos = new FileOutputStream(filepath_song_list);

		String str = "";

		for (Song song : manager_song_list) {
			str += song.getTitle() + "," + song.getSinger() + "," + song.getNumber() + "," + song.getCategory() + "\n";
		}

		fos.write(str.getBytes());
		fos.flush();
		fos.close();

	}

	public void manager_read_song() throws IOException {
		// 1. txt file ---> ArrayList<Song>
		FileInputStream fis = new FileInputStream(filepath_song_list);

		int size = fis.available();
		byte[] list = new byte[size];
		fis.read(list);

		String str = new String(list);

		String[] list_1 = str.split("\n");

		for (String s : list_1) {
			String[] list_2 = s.split(",");
			manager_song_list.add(new Song(list_2[0], list_2[1], list_2[2], list_2[3]));

		}
		fis.close();
	}

	public void manager_write_single_data(String newString, ArrayList<Song> list) throws IOException {

		Collections.sort(list, new Comparator<Song>() {
			@Override
			public int compare(Song o1, Song o2) {
				int a1 = Integer.parseInt(o1.getNumber());
				int a2 = Integer.parseInt(o2.getNumber());
				return a1 - a2;
			}
		});

		FileOutputStream fos = new FileOutputStream(filepath_song_list);
		fos.write(newString.getBytes());
		fos.flush();
		fos.close();
	}

	public boolean manager_logIn() {
		try {
			System.out.println("[관리자] 아이디 : ");
			String id = scanner.next();
			System.out.println("[관리자] 비밀번호 : ");
			String password = scanner.next();
			if (id.equals(manager_id) && password.equals(manager_pw)) {
				System.out.println("[관리자] 로그인 성공");
				return true;
			}

		} catch (Exception e) {
			scanner = new Scanner(System.in);
			System.err.println("[오류발생] 관리자에게 문의바랍니다. " + e);
		}
		return false;
	}

}
