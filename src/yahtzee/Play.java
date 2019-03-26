package yahtzee;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeSet;

public class Play {
	static ArrayList<Player> players = new ArrayList<Player>();
	static Scanner scnr = YahtzeeUtil.scnr;
	static Dice dice = new Dice();
	static int center = 60;
	static String print = "";
	
	public static void play() {
		boolean retry = true;
		boolean playGame = true;
		String playerCount = "";
		while(retry) {
			print = "Enter number of players: ";
			pad(print);
			if (scnr.hasNextLine()) {
				playerCount = scnr.nextLine();
				retry = players(playerCount);	
			} else {
				print = "What was that? try again... ";
				System.err.print(YahtzeeUtil.padding(center - (print.length() / 2), ' ') + print);
			}
		}
		while(playGame) {
			playerNames();
			boolean gameOver = false;

			while(!gameOver) {
				for (Player player : players) {
					roll(player);				
				}
				gameOver = checkGameOver();
			}
			displayWinner(setWinner());	
			playGame = YahtzeeUtil.retry("Play again?");
		}
	}
	
	public static ArrayList<Player> setWinner() {
		ArrayList<Player> winner = new ArrayList<Player>();
		int highScore = 0;
		for (Player player : players) {
			if (player.getScore().getTotal() > highScore) {
				winner.clear();
				winner.add(player);
			} else if (player.getScore().getTotal() == highScore) {
				winner.add(player);
			}
		}
		for (Player player : players) {
			int[] record = new int[3];
			record = player.getRecord();
			if (winner.size() == 1 && winner.contains(player)) {
				record[0] += 1;
				player.setWinLossTie("WINNER! ");
			} else if (winner.size() > 1 && winner.contains(player)){
				record[2] += 1;
				player.setWinLossTie("TIED GAME... ");
			} else {
				record[1] += 1;
				player.setWinLossTie("LOSER. ");
			}
			player.setRecord(record);
		}
		return winner;
	}
	
	public static void displayWinner(ArrayList<Player> winner) {
		for (Player player : winner) {
			System.out.println(player);
		}
	}
	
	public static void roll(Player player) {
		YahtzeeUtil.clearTerminal();
		print = player.getName() + ", enter any key to roll...";
		padLine(print);
		scnr.nextLine();
		for (int i = 0; i < Dice.rolls; i++) {	
			YahtzeeUtil.clearTerminal();
			dice.setRoll();
			displayRoll();		
			if (i < Dice.rolls - 1) {
				displayCombos(player);
				selectDice(player);
			} else {
				selectPlay(player);
			}			
		}
		dice.clearSelect();
	}
	
	public static void selectPlay(Player player) {
		checkPossibilities(player);
		int count = 0;
		System.out.println("\n");
		for (int i = 0; i < player.getScore().getKeys().size(); i++) {
			count++;
			if (player.getScore().getPlayed().get(i) == false) {
				print = count + ") " + player.getScore().getKeys().get(i);
				System.out.print(YahtzeeUtil.padding(50, ' ') + print);
				System.out.println(YahtzeeUtil.padding(25 - print.length(), ' ') + "+" + player.getScore().getPossible().get(i));
			} else {
				print = "Already played";
				padLine(print);
			}
		}
		System.out.println();
		boolean valid = false;
		int select = 0;
		while(!valid) {
			print = "What would you like to play? ";
			pad(print);
			select = YahtzeeUtil.validateMenu(player.getScore().getPlayed().size());
			if (player.getScore().getPlayed().get(select - 1) == false) {
				valid = true;
			} else {
				print = player.getName() + ", you've already played that...";
				System.err.println(YahtzeeUtil.padding(center - (print.length() / 2), ' ') + print);
			}
			if (valid) {
				valid = YahtzeeUtil.retry(player.getScore().getKeys().get(select - 1) + ", correct?");
			}
		}
		player.getScore().setScore(select - 1, dice);
		YahtzeeUtil.clearTerminal();
		print = player.getName() + "'s scoresheet:\n";
		pad(print);		
		print = YahtzeeUtil.padding(print.length(), '‾') + "\n";
		pad(print);	
		count = 0;
		for (int i = 0; i < player.getScore().getKeys().size(); i++) {
			count++;
			if (player.getScore().getPlayed().get(i) == true) {
				print = count + ") " + player.getScore().getKeys().get(i);
				print += YahtzeeUtil.padding(25 - print.length(), '-') + player.getScore().getValues().get(i);
			} else {
				print = count + ") " + player.getScore().getKeys().get(i);
			}
			System.out.println(YahtzeeUtil.padding(50, ' ') + print);
		}
		print = "Total: " + player.getScore().getTotal();
		padLine(print);
		System.out.println("\n");
		print = "Enter any key to continue...";
		pad(print);
		scnr.nextLine();
	}
	
	public static void selectDice(Player player) {
		checkPossibilities(player);
		boolean select;
		select = YahtzeeUtil.retry(player.getName() + ", would you like to select dice to keep?");
		while(select) {
			boolean[] selection = dice.getSelect();
			print = "Select die to keep (1-5): ";
			pad(print);
			int index = YahtzeeUtil.validateMenu(Dice.dice) - 1;
			if (selection[index] == false) {
				selection[index] = true;
				dice.setSelect(selection);
				print = "die " + (index + 1) + " selected.";
				pad(print);
			} else {
				selection[index] = false;
				dice.setSelect(selection);
				print = "die " + (index + 1) + " unselected.";
				pad(print);
			}
			YahtzeeUtil.clearTerminal();
			displayRoll();
			displayCombos(player);
			select = YahtzeeUtil.retry("Select another die, " + player.getName() + "?");
		}		
	}
	
	public static void checkPossibilities(Player player) {
		player.getScore().clearPossible();
		int[] counts = new int[Dice.sides];
		for (int roll : counts) {
			roll = 0;
		}
		
		for (int i = 0; i < Dice.dice; i++) {
			int roll = dice.getRoll()[i];
			counts[roll - 1] += 1;
		}	
		numbers(player, counts);
		OfKind(player, counts);
		straights(player, counts);
		chance(player, counts);
	}
	
	public static void numbers(Player player, int[] counts) {
		for (int i = 0; i < counts.length; i++) {
			int score = (i + 1) * counts[i];
			player.getScore().setPossibility(i, score);
		}
	}
	
	public static void straights(Player player, int[] counts) {
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
			print = "Large Straight";
			pad(print);
			player.getScore().setPossibility(10, 40);
		} 		
		if (small) {
			print = "Small Straight";
			pad(print);
			player.getScore().setPossibility(9, 30);
		}		
	}
	
	public static void OfKind(Player player, int[] counts) {
		int score = 0;
		for (int i = 0; i < counts.length; i++) {
			if (counts[i] == 5) {
				print = "YAHTZEE!";
				pad(print);
				score = player.getScore().getValues().get(11);
				player.getScore().setPossibility(11, score + 50);
			}
			if (counts[i] == 4) {
				print = "4 of a Kind";
				pad(print);
				score = (i + 1) * counts[i];
				player.getScore().setPossibility(7, score);
			}
			if (counts[i] == 3) {
				for (int count2 : counts) {
					if (count2 == 2) {
						print = "Full House";
						pad(print);
						player.getScore().setPossibility(8, 25);
					}
				}
				print = "3 of a Kind";
				score = (i + 1) * counts[i];
				player.getScore().setPossibility(6, score);
			}
		}
	}

	public static void chance(Player player, int[] counts) {
		int score = 0;
		for (int i = 0; i < counts.length; i++) {
			score += (i + 1) * counts[i];
		}
		player.getScore().setPossibility(12, score);
	}
	
	public static void displayRoll() {
		char[] select = new char[] {'①','②','③','④','⑤','⑥'};
		System.out.println();
		print = " _   _   _   _   _ \n";
		pad(print);
		System.out.print(YahtzeeUtil.padding(50, ' ') + "|");
		if (dice.getSelect()[0] == true) {
			System.out.print(select[dice.getRoll()[0] - 1]);
		} else {
			System.out.print(dice.getRoll()[0]);
		}
		System.out.print("| |");
		if (dice.getSelect()[1] == true) {
			System.out.print(select[dice.getRoll()[1] - 1]);
		} else {
			System.out.print(dice.getRoll()[1]);
		}
		System.out.print("| |");
		if (dice.getSelect()[2] == true) {
			System.out.print(select[dice.getRoll()[2] - 1]);
		} else {
			System.out.print(dice.getRoll()[2]);
		}
		System.out.print("| |");
		if (dice.getSelect()[3] == true) {
			System.out.print(select[dice.getRoll()[3] - 1]);
		} else {
			System.out.print(dice.getRoll()[3]);
		}
		System.out.print("| |");
		if (dice.getSelect()[4] == true) {
			System.out.print(select[dice.getRoll()[4] - 1]);
		} else {
			System.out.print(dice.getRoll()[4]);
		}
		System.out.print("|\n");
		print = " ‾   ‾   ‾   ‾   ‾ \n";
		pad(print);
		print = " 1   2   3   4   5 \n";
		pad(print);
		System.out.println();
	}
	
	public static void displayCombos(Player player) {
		for (int i = 0; i < player.getScore().getKeys().size(); i++) {
			if (player.getScore().getPlayed().get(i) == false) {
				System.out.println(player.getScore().getKeys().get(i));
			} else {
				System.out.println();
			}
		}
	}
	
	public static boolean checkGameOver() {
		boolean[] playerFinish = new boolean[Player.players];
		int count = 0;
		for (Player player : players) {
			playerFinish[count] = true;
			for (boolean mark : player.getScore().getPlayed()) {
				if (mark == false) {
					playerFinish[count] = false;
				}
			}
			count++;
		}
		for (boolean gameComplete : playerFinish) {
			if (gameComplete) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean players(String players) {
		if(YahtzeeUtil.validateInt(players)) {
			Player.players = Integer.parseInt(players);
			return false;
		} else {
			print = "Sorry, " + players + " is not valid...";
			System.err.println(YahtzeeUtil.padding(center - (print.length() / 2), ' ') + print);
			return true;
		}
	}
	
	public static void playerNames() {
		for (int i = 0; i < Player.players; i++) {
			players.add(new Player(getName(i + 1)));
		}
	}
	
	public static String getName(int i) {
		boolean valid = false;
		String name = "";
		
		while(!valid) {
			YahtzeeUtil.clearTerminal();
			print = "Enter name for Player " + i + ": ";
			pad(print);
			name = scnr.nextLine();
			valid = YahtzeeUtil.retry(name + ", is that correct?");
		}

		return name;
	}
	
	public static void pad(String print) {
		System.out.print(YahtzeeUtil.padding(center - (print.length() / 2), ' ') + print);
	}
	
	public static void padLine(String print) {
		System.out.println(YahtzeeUtil.padding(center - (print.length() / 2), ' ') + print);
	}
}
