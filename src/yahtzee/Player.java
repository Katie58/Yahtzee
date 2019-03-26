package yahtzee;

public class Player {
	static public int players = 0;
	
	private String name;
	private String winLossTie;
	private int[] record = new int[3];
	private double wltRatio;
	private ScoreSheet score;
	
	public Player(String name) {
		this.name = name;
		this.score = new ScoreSheet();
	}
	
	public void setWinLossTie(String winLossTie) {
		this.winLossTie = winLossTie;
	}
	
	public void setRecord(int[] record) {
		this.record = record;
	}
	
	public String getName() {
		return name;
	}
	
	public int[] getRecord() {
		return record;
	}
	
	public double getWltRatio() {
		return (record[0] + (0.5 * record[2])) / (record[0] + record[1] + record[2]);
	}
	
	public ScoreSheet getScore() {
		return score;
	}
	
	public void newRecord(char wlt) {
		switch(wlt) {
		case 'w': record[0] += 1;
		break;
		case 'l': record[1] += 1;
		break;
		case 't': record[2] += 1;
		break;
		}
	}
	
	@Override
	public String toString() {
		return winLossTie + name + " with a score of " + score + "! New record of: " + record[0] + "-" + record[1] + "-" + record[2] + " win/loss ratio: " + wltRatio;
	}
	
}
