package jUnitStuff;

import java.util.TreeSet;

public class Stuff2Test {

	public static String checkPossibilities(int[] rolls) {
		int[] counts = new int[6];
		
		for (int i = 0; i < 5; i++) {
			int roll = rolls[i];
			counts[roll - 1] += 1;
		}
		
		String ofKind = OfKind(counts);
		String straights = straights(counts);
		
		if (ofKind.equalsIgnoreCase("false")) {
			if (straights.equalsIgnoreCase("false")) {
				return "false";
			} else {
				return straights;
			}
		} else {
			return ofKind;
		}
	}

	public static String straights(int[] counts) {
		boolean large = true;
		boolean small = true;
		
		TreeSet<Integer> ordered = new TreeSet<Integer>();
		for (int num : counts) {
			ordered.add(num);
		}			
		for (int i = 1; i < counts.length; i++) {
			if (counts[i] != 1) {
				large = false;
				break;
			}
		}			
		for (int i = 0; i < counts.length - 1; i++) {
			if (counts[i] != 1) {
				small = false;
				break;
			}
		}
		
		if (large) {
			return "Large Straight";
		} 		
		if (small) {
			return "Small Straight";
		}
		return "false";
	}
	
	public static String OfKind(int[] counts) {
		for (int i = 0; i < counts.length; i++) {
			if (counts[i] == 5) {
				return "YAHTZEE!";
			}
			if (counts[i] == 4) {
				return "4 of a Kind";
			}
			if (counts[i] == 3) {
				for (int count2 : counts) {
					if (count2 == 2) {
						return "Full House";
					}
				}
				return "3 of a Kind";
			}
		}
		return "false";
	}
	
}
