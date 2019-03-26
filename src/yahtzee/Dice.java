package yahtzee;

import java.util.Random;

public class Dice {	
	static int sides = 6;
	static int dice = 5;
	static int rolls = 3;
	
	private int[] roll = new int[dice];
	private boolean[] select = new boolean[dice];
	
	public Dice() {
		for (int i = 0; i < dice; i++) {
			select[i] = false;
		}
	}
	
	public void setRoll() {
		for (int i = 0; i < roll.length; i++) {
			if (select[i] == false) {
				roll[i] = roll();
			}
		}
	}
	
	public void setSelect(boolean[] select) {
		this.select = select;
	}
	
	
	public void clearSelect() {
		for (int i = 0; i < select.length; i++) {
			select[i] = false;
		}
	}
		
	public int[] getRoll() {
		return roll;
	}
	
	public boolean[] getSelect() {
		return select;
	}
	
	public int roll() {
		Random r = new Random();
		return r.nextInt(sides) + 1;
	}	
}
