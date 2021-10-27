package controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.TreeSet;

import model.Lyric;
import model.Lyric_AscendingOrder;
import model.Song;
import model.Song_AscendingOrder;

public class Thread1 extends Thread {

	public boolean stop;
	public boolean work = true;

	private String filepath_ballad = "./src/file/ballad_lyrics.txt";
	private String filepath_dance = "./src/file/dance_lyrics.txt";
	private String filepath_hiphop = "./src/file/hiphop_lyrics.txt";
	private String filepath_song_list = "./src/file/song_list_info.txt";

	public Scanner scanner = new Scanner(System.in);

	public TreeSet<Lyric> thread1_lyrics_ballad = new TreeSet<Lyric>(new Lyric_AscendingOrder());
	public TreeSet<Lyric> thread1_lyrics_dance = new TreeSet<Lyric>(new Lyric_AscendingOrder());
	public TreeSet<Lyric> thread1_lyrics_hiphop = new TreeSet<Lyric>(new Lyric_AscendingOrder());
	public TreeSet<Song> thread1_song_list = new TreeSet<Song>(new Song_AscendingOrder());

	// 1. 사용자가 원하는 가사 출력 사이의 간격
	// default : 1000ms
	private final int STEP = 1000;

	@Override
	public void run() {
		while (true) {
			try {
				int count = 0; // 1. 가사가 다 출력되면 스레드를 종료시킬 카운터
				System.out.print("[알림] 예약 번호 : ");
				int customer_select_song = scanner.nextInt();
				int idx = customer_select_song / 1000;
				thread1_file_read_song();

				if (idx == 1) {
					// 0. count = 0
					// 1. 발라드 가사 목록을 출력합니다.
					// 1. text file ----> TreeSet 으로 전달
					thread1_file_read_ballad();

					for (Song song : thread1_song_list) {
						System.out.println("제목 : " + song.getTitle() + " | 가수 : " + song.getSinger());
						break;
					}
					
					// int count = 0;

					for (Lyric lyric : thread1_lyrics_ballad) {
						// 1. 고객이 입력한 곡과 일치한 곡이 존재하면 가사를 출력합니다.
						if (lyric.getNumber() == customer_select_song) {
							String[] thread1_lyric_byLine = lyric.getLyrics().split("\n");
							// 1. 가사가 한줄 단위로 저장됩니다.
							int thread1_list_size = thread1_lyric_byLine.length;
							// 2. 가사가 한줄 단위로 출력됩니다.
								// 1. stop flag 를 이용해서 제어합니다.
									// 1. Thread2 에서 thread1 의 상태를 제어하고 있습니다.
									// 2. Thread2 에서 입력에 따라서 work, stop 을 true, false 로 상태변화시킵니다.
									// 3. stop flag 이용한 상태제어 (at Thread2)
							while (!stop) {
								for (String byLine : thread1_lyric_byLine) {
									if (work) {
										if (count == thread1_list_size) {

											setStop(true);
											break;
										}
										// 1. 가사가 한 줄 출력되면 카운터를 증가시킵니다.
											// 1. 카운트가 전체 가사 길이 만큼 증가된다면 Thread1 을 종료시킵니다.
										count++;
										System.out.println(byLine);
										Thread.sleep(STEP);

									} else {
										Thread.yield();
									}
								}
							}

							break;
						} 
					}
				}

				else if (idx == 2) {
					// 1. 댄스 가사 목록을 출력합니다.
					thread1_file_read_dance();
					for (Lyric lyric : thread1_lyrics_dance) {
						if (lyric.getNumber() == customer_select_song) {
							// 1. 고객이 입력한 곡과 일치한 곡이 존재하면 가사를 출력합니다.
							String[] thread1_lyric_byLine = lyric.getLyrics().split("\n");
							int thread1_list_size = thread1_lyric_byLine.length;
							// 1. 가사가 한줄 단위로 저장됩니다.

							while (!stop) {
								for (String byLine : thread1_lyric_byLine) {
									if (work) {
										if (count == thread1_list_size) {
											stop = true;
											break;
										}
										count++;
										System.out.println(byLine);
										Thread.sleep(STEP);

									} else {
										Thread.yield();
									}
								}
							}

							break;
						}
					}
				}

				else if (idx == 3) {
					// 1. 힙합 가사 목록을 출력합니다.
					thread1_file_read_hiphop();
					for (Lyric lyric : thread1_lyrics_hiphop) {
						if (lyric.getNumber() == customer_select_song) {
							// 1. 고객이 입력한 곡과 일치한 곡이 존재하면 가사를 출력합니다.
							String[] thread1_lyric_byLine = lyric.getLyrics().split("\n");
							int thread1_list_size = thread1_lyric_byLine.length;
							// 1. 가사가 한줄 단위로 저장됩니다.

							while (!stop) {
								for (String byLine : thread1_lyric_byLine) {
									if (work) {
										if (count == thread1_list_size) {
											stop = true;
											break;
										}
										count++;
										System.out.println(byLine);
										Thread.sleep(STEP);

									} else {
										Thread.yield();
									}
								}
							}

							break;
						}
					}
				}

				return;

			}

			catch (Exception e) {
				System.out.println("[알림] 오류발생. 관리자에게 문의바람." + e);
				scanner = new Scanner(System.in);
			}
		}
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

	public void thread1_file_read_ballad() throws IOException {
		FileInputStream fis = new FileInputStream(filepath_ballad);
		int size = fis.available();
		byte[] bytelist = new byte[size];
		fis.read(bytelist);

		String str = new String(bytelist);
		String[] thread_tmplist_1 = str.split("%\n");
		for (String str_by_song : thread_tmplist_1) {
			String[] thread_tmplist_2 = str_by_song.split("&");
			int number = Integer.parseInt(thread_tmplist_2[0]);
			String lyrics = thread_tmplist_2[1];
			thread1_lyrics_ballad.add(new Lyric(number, lyrics));
		}
		fis.close();
	}

	public void thread1_file_read_dance() throws IOException {
		FileInputStream fis = new FileInputStream(filepath_dance);
		int size = fis.available();
		byte[] bytelist = new byte[size];
		fis.read(bytelist);

		String str = new String(bytelist);
		String[] thread_tmplist_1 = str.split("%\n");
		for (String str_by_song : thread_tmplist_1) {
			String[] thread_tmplist_2 = str_by_song.split("&");
			int number = Integer.parseInt(thread_tmplist_2[0]);
			String lyrics = thread_tmplist_2[1];
			thread1_lyrics_dance.add(new Lyric(number, lyrics));
		}
		fis.close();
	}

	public void thread1_file_read_hiphop() throws IOException {
		FileInputStream fis = new FileInputStream(filepath_hiphop);
		int size = fis.available();
		byte[] bytelist = new byte[size];

		String str = new String(bytelist);
		String[] thread_tmplist_1 = str.split("%\n");
		for (String str_by_song : thread_tmplist_1) {
			String[] thread_tmplist_2 = str_by_song.split("&");
			int number = Integer.parseInt(thread_tmplist_2[0]);
			String lyrics = thread_tmplist_2[1];
			thread1_lyrics_hiphop.add(new Lyric(number, lyrics));
		}
		fis.close();
	}

	public void thread1_file_read_song() throws IOException {
		FileInputStream fis = new FileInputStream(filepath_song_list);
		int size = fis.available();
		byte[] bytelist = new byte[size];
		fis.read(bytelist);

		String str = new String(bytelist);
		String[] L1 = str.split("\n");
		for (String s : L1) {
			if (!s.equals(" ")) {
				String[] L2 = s.split(",");
				thread1_song_list.add(new Song(L2[0], L2[1], L2[2], L2[3]));
			}
		}
		fis.close();
	}

}
