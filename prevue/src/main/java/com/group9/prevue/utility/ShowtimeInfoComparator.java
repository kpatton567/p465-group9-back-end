package com.group9.prevue.utility;

import java.util.Comparator;

import com.group9.prevue.model.ShowtimeInfo;

public class ShowtimeInfoComparator implements Comparator<ShowtimeInfo>{

	public int compare(ShowtimeInfo s1, ShowtimeInfo s2) {
		return s1.getDate().compareToIgnoreCase(s2.getDate());
	}
}
