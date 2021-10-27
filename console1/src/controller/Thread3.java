package controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.TreeSet;

import model.Lyric;
import model.Lyric_AscendingOrder;
import model.Song;
import model.Song_AscendingOrder;

public class Thread3 extends Thread {

	private boolean stop;
	private boolean work = true;

	private String filepath_ballad = "./src/file/ballad_lyrics.txt";
	private String filepath_dance = "./src/file/dance_lyrics.txt";
	private String filepath_hiphop = "./src/file/hiphop_lyrics.txt";
	private final String filepath_reserve_list = "./src/file/reserve_list.txt";

	public Scanner scanner = new Scanner(System.in);

	private TreeSet<Lyric> thread3_lyrics_ballad = new TreeSet<Lyric>(new Lyric_AscendingOrder());
	private TreeSet<Lyric> thread3_lyrics_dance = new TreeSet<Lyric>(new Lyric_AscendingOrder());
	private TreeSet<Lyric> thread3_lyrics_hiphop = new TreeSet<Lyric>(new Lyric_AscendingOrder());
	private TreeSet<Song> thread3_reserve_list = new TreeSet<Song>(new Song_AscendingOrder());

	@Override
	public void run() {
		// 1. 예약 곡을 연속으로 출력합니다.
		try {
			// 1. txt file ----> TreeSet 객체로 데이터 전달
			thread3_read_reserve_list();
			for (Song song : thread3_reserve_list) {
				// 1. 인덱스
				// 1. 발라드 2. 댄스 3. 힙합
				int idx = Integer.parseInt(song.getNumber()) / 1000;
				System.out.println("제목 : " + song.getTitle() + " | 가수 : " + song.getSinger());

				if (idx == 1) {
					thread3_file_read_ballad();
					// 1. 발라드 - 해당하는 곡 출력
					for (Lyric lyric : thread3_lyrics_ballad) {
						String L1 = lyric.getLyrics();
						String[] byLine = L1.split("\n");
						while (!stop) {
							for (String line : byLine) {
								if (work) {
									System.out.println(line);
									Thread.sleep(1000);
								} else {
									Thread.yield();
								}
							}
						}
					}
				}
				if (idx == 2) {
					thread3_file_read_dance();
					// 1. 댄스 - 해당하는 곡 출력
					for (Lyric lyric : thread3_lyrics_dance) {
						String L1 = lyric.getLyrics();
						String[] byLine = L1.split("\n");
						while (!stop) {
							for (String line : byLine) {
								if (work) {
									System.out.println(line);
									Thread.sleep(1000);
								}

								else {
									Thread.yield();
								}
							}
						}
					}
				}
				if (idx == 3) {
					thread3_file_read_hiphop();
					// 1. 힙합 - 해당하는 곡 출력
					for (Lyric lyric : thread3_lyrics_hiphop) {
						String L1 = lyric.getLyrics();
						String[] byLine = L1.split("\n");
						while (!stop) {
							for (String line : byLine) {
								if (work) {
									System.out.println(line);
									Thread.sleep(1000);
								}

								else {
									Thread.yield();
								}
							}
						}
					}
				}
			}

			// 1. for 문이 끝나는 시점에서 종료 (모든 노래가 가사 출력 완료되는 시점)
			return;

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void thread3_read_reserve_list() throws IOException {
		// 1. 예약 목록을 불러옵니다.

		FileInputStream fis = new FileInputStream(filepath_reserve_list);
		int size = fis.available();
		byte[] bytelist = new byte[size];
		fis.read(bytelist);

		String str = new String(bytelist);

		String[] l1 = str.split("\n");
		for (String s : l1) {
			if (!s.equals(" ")) {
				String[] l2 = s.split(",");
				thread3_reserve_list.add(new Song(l2[0], l2[1], l2[2], l2[3]));
			}
		}

	}

	public void thread3_file_read_ballad() throws IOException {
		// 1. 가사를 읽어서 TreeSet 에 저장합니다.
		FileInputStream fis = new FileInputStream(filepath_ballad);
		int size = fis.available();
		byte[] bytelist = new byte[size];
		fis.read(bytelist);

		String str = new String(bytelist);

		String[] l1 = str.split("%\n"); // 1. 예약번호&가사
		for (String s : l1) {
			if (!s.equals(" ")) {
				String[] l2 = s.split("&"); // 2. { 예약번호, 가사 }
				int number = Integer.parseInt(l2[0]);
				String lyrics = l2[1];
				thread3_lyrics_ballad.add(new Lyric(number, lyrics));
			}
		}
		fis.close();
	}

	public void thread3_file_read_dance() throws IOException {
		// 1. 가사를 읽어서 TreeSet 에 저장합니다.
		FileInputStream fis = new FileInputStream(filepath_dance);
		int size = fis.available();
		byte[] bytelist = new byte[size];
		fis.read(bytelist);

		String str = new String(bytelist);

		String[] l1 = str.split("%\n");
		for (String s : l1) {
			if (!s.equals(" ")) {
				String[] l2 = s.split("&");
				int number = Integer.parseInt(l2[0]);
				String lyrics = l2[1];
				thread3_lyrics_dance.add(new Lyric(number, lyrics));
			}
		}
		fis.close();
	}

	public void thread3_file_read_hiphop() throws IOException {
		// 1. 가사를 읽어서 TreeSet 에 저장합니다.
		FileInputStream fis = new FileInputStream(filepath_hiphop);
		int size = fis.available();
		byte[] bytelist = new byte[size];
		fis.read(bytelist);

		String str = new String(bytelist);

		String[] l1 = str.split("%\n");
		for (String s : l1) {
			if (!s.equals(" ")) {
				String[] l2 = s.split("&");
				int number = Integer.parseInt(l2[0]);
				String lyrics = l2[1];
				thread3_lyrics_hiphop.add(new Lyric(number, lyrics));
			}
		}
		fis.close();
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
