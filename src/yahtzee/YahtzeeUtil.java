package yahtzee;

import java.util.Scanner;

public class YahtzeeUtil {
	static Scanner scnr = new Scanner(System.in);
	static int center = 60;
	static String print = "";
	
	public static void header() {
		for(int i = 0; i < 7; i++) {
			System.out.println();
		}
		print = "YAHTZEE!!!!!!!!!!!";
		System.out.println(padding(center - (print.length() / 2), ' ') + print);
	}
	
	public static void menu(Menu menu) {
		header();
		int size = menu.getMenuItems().size();
		int select;
		System.out.println();
		for (int i = 0; i < size; i++) {
			System.out.println(padding(center - 9, ' ') + (i + 1) + ") " + menu.getMenuItems().get(i).name());
		}
		centerTerminal();
		print = "Choose a menu option: ";
		System.out.print("\n" + padding(center - (print.length() / 2), ' ') + print);
		select = validateMenu(size) - 1;
		((MenuInterface) menu.getEnum(select)).execute();
	}
	
	public static boolean validateInt(String input) {
		if (input.isEmpty()) {
			return false;
		}
		if (!input.matches("[-0-9]*")) {
			return false;
		}
		String maxInt = String.valueOf(Integer.MAX_VALUE);
		if (input.length() < maxInt.length()) {
			return true;
		} else if (input.length() > maxInt.length()) {
			return false;
		} else {
			for (int i = 0; i < input.length(); i++) {
				if (input.charAt(i) < maxInt.charAt(i)) {
					return true;
				} 
				if (input.charAt(i) > maxInt.charAt(i)) {
					return false;
				}
			}
			return true;
		}
	}
	
	public static boolean validateCharAlpha(char character) {
		String charString = Character.toString(character);
		if (charString.matches("[a-zA-Z]")) {
			return true;
		} else {
			return false;
		}			
	}
	
	public static int validateMenu(int menuCount) {
		int input = 0;
		boolean valid = false;
		while(!valid) {
			String in = scnr.nextLine().trim();
			if (in.isEmpty()) {
				print = "Perhaps check your numlock, try again... ";
				System.err.print(padding(center - (print.length() / 2), ' ') + print);
				continue;					
			} else if (in.matches("[0-9]*")) {
				input = Integer.parseInt(in);
				if (input >= 1 && input <= menuCount) {
					return input;
				} else {
					print = "Sorry, " + input + " is not an option, try again... ";
					System.err.print(padding(center - (print.length() / 2), ' ') + print);
					continue;
				}
			} else {
				print = "Looking for numbers, try again... ";
				System.err.print(padding(center - (print.length() / 2), ' ') + print);
				continue;
			}
		}
		return input;
	}
	
 	public static boolean retry(String question) {
 		boolean valid = false;
 		char select = ' ';
 		while(!valid) {
	 		print =  question + " (y/n) ";
			System.out.print("\n" + padding(center - (print.length() / 2), ' ') + print);
			String input = scnr.nextLine();
			if (!input.isEmpty()) {
				select = input.charAt(0);
				valid = true;
			} else {
				print = "Didn't quite catch that, try again... ";
				System.err.print(padding(center - (print.length() / 2), ' ') + print);
			} 			
 		}
		return validateYesNo(select);
	}
	
	public static boolean validateYesNo(char input) {
		while (input != 'y' && input != 'Y' && input != 'n' && input != 'N') {
			print = "This is a simple yes or no question, try again...";
			System.err.print(padding(center - (print.length() / 2), ' ') + print);
			if (scnr.hasNextLine()) {
				input = scnr.nextLine().charAt(0);
			}	
		}
		return (input == 'y' || input == 'Y');
	}
	
	public static String padding(int multiplier, char character) {
		String multiples = "";
		for (int i = 1; i <= multiplier; i++) {
			multiples += character;
		}
		return multiples;
	}
	
	public static void centerTerminal() {
		int multiplier = 14;
		for (int i = 0; i < multiplier;i++) {
			System.out.println(" ");
		}
	}
		
	public static void clearTerminal() {
		int multiplier = 30;
		for (int i = 0; i < multiplier;i++) {
			System.out.println(" ");
		}
	}

	public static void exit() {
		print = "Catch you later!";
		System.out.println(padding(center - (print.length() / 2), ' ') + print);
		scnr.close();	
		if (YahtzeeFiles.readerOpen) {
			YahtzeeFiles.closeReader();
		}
	}
}
