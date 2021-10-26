package model;

public class Song {

	public Song() {
	}

	private String title;
	private String singer;
	private String number;
	private String category;

	public Song(String title, String singer, String number, String category) {
		super();
		this.title = title;
		this.singer = singer;
		this.number = number;
		this.category = category;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSinger() {
		return singer;
	}

	public void setSinger(String singer) {
		this.singer = singer;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
