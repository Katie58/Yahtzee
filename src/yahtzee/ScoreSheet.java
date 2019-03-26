package yahtzee;

import java.util.ArrayList;

public class ScoreSheet {

	private ArrayList<String> keys = new ArrayList<>();
	private ArrayList<Integer> values = new ArrayList<>();
	private ArrayList<Boolean> played = new ArrayList<>();
	private ArrayList<Integer> possible = new ArrayList<>();
	
	public ScoreSheet() {
		keys.add("Ones");values.add(0);played.add(false);possible.add(0);//0
		keys.add("Twos"); values.add(0);played.add(false);possible.add(0);//1
		keys.add("Threes");values.add(0);played.add(false);possible.add(0);//2
		keys.add("Fours");values.add(0);played.add(false);possible.add(0);//3
		keys.add("Fives");values.add(0);played.add(false);possible.add(0);//4
		keys.add("Sixes");values.add(0);played.add(false);possible.add(0);//5
		keys.add("Three of a Kind");values.add(0);played.add(false);possible.add(0);//6
		keys.add("Four of a Kind");values.add(0);played.add(false);possible.add(0);//7
		keys.add("Full House");values.add(0);played.add(false);possible.add(0);//8
		keys.add("Small Straight");values.add(0);played.add(false);possible.add(0);//9
		keys.add("Large Straight");values.add(0);played.add(false);possible.add(0);//10
		keys.add("Yahtzee");values.add(0);played.add(false);possible.add(0);//11
		keys.add("Chance");values.add(0);played.add(false);possible.add(0);//12
	}
	
	public void setScore(int index, Dice dice) {
		values.set(index, possible.get(index));
		played.set(index, true);
		clearPossible();
	}
	
	public void setPossibility(int index, int score) {
		possible.set(index, score);
	}
	
	public ArrayList<String> getKeys() {
		return keys;
	}
	
	public ArrayList<Integer> getValues() {
		return values;
	}
	
	public ArrayList<Boolean> getPlayed() {
		return played;
	}
	
	public ArrayList<Integer> getPossible() {
		return possible;
	}
	
	public int getTotal() {
		int total = 0;
		for (int score : values) {
			total += score;
		}
		return total;
	}
	
	public void clearPossible() {
		for (int i = 0; i < possible.size(); i++) {
			possible.set(i, 0);
		}
	}
	
}
