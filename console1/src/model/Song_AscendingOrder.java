package model;

import java.util.Comparator;

public class Song_AscendingOrder implements Comparator<Song> {

	@Override
	public int compare(Song o1, Song o2) {
		int a1 = Integer.parseInt(o1.getNumber());
		int a2 = Integer.parseInt(o2.getNumber());
		return a1 - a2;
	}

}
