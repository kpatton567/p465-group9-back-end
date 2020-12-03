package com.group9.prevue.utility;

import java.util.Comparator;

import com.group9.prevue.model.Theater;

public class TheaterNameComparator implements Comparator<Theater>{
	
	public int compare(Theater t1, Theater t2) {
		return t1.getName().compareToIgnoreCase(t2.getName());
	}
}
