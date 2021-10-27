package controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.TreeSet;

import model.Lyric;
import model.Lyric_AscendingOrder;

public class Thread1 extends Thread {

	public boolean stop;
	public boolean work = true;

	private String filepath_ballad = "./src/file/ballad_lyrics.txt";
	private String filepath_dance = "./src/file/dance_lyrics.txt";
	private String filepath_hiphop = "./src/file/hiphop_lyrics.txt";

	public Scanner scanner = new Scanner(System.in);

	public TreeSet<Lyric> thread1_lyrics_ballad = new TreeSet<Lyric>(new Lyric_AscendingOrder());
	public TreeSet<Lyric> thread1_lyrics_dance = new TreeSet<Lyric>(new Lyric_AscendingOrder());
	public TreeSet<Lyric> thread1_lyrics_hiphop = new TreeSet<Lyric>(new Lyric_AscendingOrder());

	private final int STEP = 1000;

	@Override
	public void run() {
		while (true) {
			try {
				int count = 0; // 1. 가사가 다 출력되면 스레드를 종료시킬 카운터
				System.out.print("[알림] 예약 번호 : (뒤로가기 : 0번) : ");
				int customer_select_song = scanner.nextInt();
				int idx = customer_select_song / 1000;

				if (idx == 1) {
					// 1. 발라드 가사 목록을 출력합니다.
					thread1_file_reader_ballad();
					for (Lyric lyric : thread1_lyrics_ballad) {
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
						}
					}
				}

				else if (idx == 2) {
					// 1. 댄스 가사 목록을 출력합니다.
					thread1_file_reader_dance();
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
						}
					}
				}

				else if (idx == 3) {
					// 1. 힙합 가사 목록을 출력합니다.
					thread1_file_reader_hiphop();
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
						}
					}
				}

				else if (idx == 0) {
					break;
				}
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

	public void thread1_file_reader_ballad() throws IOException {
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

	public void thread1_file_reader_dance() throws IOException {
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

	public void thread1_file_reader_hiphop() throws IOException {
		FileInputStream fis = new FileInputStream(filepath_hiphop);
		int size = fis.available();
		byte[] bytelist = new byte[size];
		fis.read(bytelist);

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

}
