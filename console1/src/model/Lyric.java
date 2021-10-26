package model;

public class Lyric {

	private int number;
	private String lyrics;

	public Lyric(int number, String lyrics) {
		super();
		this.number = number;
		this.lyrics = lyrics;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getLyrics() {
		return lyrics;
	}

	public void setLyrics(String lyrics) {
		this.lyrics = lyrics;
	}

}
