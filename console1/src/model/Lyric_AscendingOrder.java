package model;

import java.util.Comparator;

public class Lyric_AscendingOrder implements Comparator<Lyric> {

	@Override
	public int compare(Lyric o1, Lyric o2) {
		return o1.getNumber() - o2.getNumber();
	}

}
