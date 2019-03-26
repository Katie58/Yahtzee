package yahtzee;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class YahtzeeFiles {
	static String print = "";
	static Scanner scnr = YahtzeeUtil.scnr;
	static BufferedReader reader;
	static boolean readerOpen = false;
	static ArrayList<String> files = new ArrayList<String>();//list of files
	static ArrayList<String> linesList = new ArrayList<String>();//list of words per line in file

	////////////////////////////// CREATE /////////////////////////////
	public static Path path(String dir) {
		Path path = Paths.get(dir);
		return path;
	}
	/////////////////////////////// READ //////////////////////////////
	public static ArrayList<String> fileList(String dir) {//get file names and stores in list		
		File[] fileNames = new File(dir).listFiles();
		for (File file : fileNames) {
		    if (file.isFile()) {
		        files.add(file.getName());
		    }
		}
		return files;
	}	
	
	public static ArrayList<String> readFile(String dir) {//list of items per line in file	
		readerOpen = true;
		ArrayList<String> aList = new ArrayList<String>();
		try {
			reader = new BufferedReader(new FileReader(dir));
			String line = reader.readLine();
			while (line != null) {
				aList.add(line);
				line = reader.readLine();	
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return aList;
	}
	//////////////////////////////// WRITE //////////////////////////////////
	public static void writeToFile(ArrayList<String> list, String file, Path path) {
		deleteFile(path);
		Paths.get(file);
		try {
			for (String line : list) {
				line = line + "\n";
				Files.write(path, line.getBytes(), StandardOpenOption.APPEND);
			}
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	//////////////////////////////// DELETE /////////////////////////////////
	private static void deleteFile(Path filePath) {
		try {
			System.out.println(filePath.toAbsolutePath() + " has been deleted: " + Files.deleteIfExists(filePath));
		} catch (IOException e) {
			System.out.println("Something went wrong");
		}
	}
	///////////////////////////////// EXIT //////////////////////////////////
	public static void clearArrays() {
		files.clear();
		linesList.clear();
	}
	
	public static void closeReader() {
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//////////////////////////////// YAHTZEE ////////////////////////////////
	public static void getRecords() {
		ArrayList<String> records = readFile("records");
		int length = 0;
		for (String record : records) {
			if (record.length() > length) {
				length = record.length();
			}
		}
		TreeMap<Double, String> ordered = new TreeMap<Double, String>();
		for (String score : records) {
			String[] record = score.split(":");
			double ratio = Double.parseDouble(record[0]);
			ordered.put(ratio, record[1]);
		}	
		YahtzeeUtil.clearTerminal();
		print = YahtzeeUtil.padding((length + 16) - 1, '=');
		System.out.println(YahtzeeUtil.padding(YahtzeeUtil.center - (print.length() / 2), ' ') + print);
		print = "RECORDS";
		System.out.println(YahtzeeUtil.padding(YahtzeeUtil.center - (print.length() / 2), ' ') + print);
		print = YahtzeeUtil.padding((length + 16) - 1, '=');
		System.out.println(YahtzeeUtil.padding(YahtzeeUtil.center - (print.length() / 2), ' ') + print);		
		int count = 1;
		for (Map.Entry<Double, String> set : ordered.entrySet()) {
			print = count + ". " + set.getValue() + YahtzeeUtil.padding(length - set.getValue().length() - set.getKey().toString().length() - Integer.toString(count).length() + 5, ' ') + set.getKey();
			System.out.println(YahtzeeUtil.padding(YahtzeeUtil.center - (print.length() / 2), ' ') + print);
			count++;
		}
		YahtzeeUtil.centerTerminal();
		print = "Enter any key to continue... ";
		System.out.print("\n" + YahtzeeUtil.padding(YahtzeeUtil.center - (print.length() / 2), ' ') + print);
		scnr.nextLine();
	}
	
	public static boolean addRecord(Player player) {
		NumberFormat f = NumberFormat.getInstance();
		String add = f.format(player.getWltRatio()) + ":" + player.getName() + ":" + player.getRecord()[0] + ":" + player.getRecord()[1] + ":" + player.getRecord()[2];
		ArrayList<String> records = readFile("records");
		if (records.size() < 20) {
			records.add(add);
			writeToFile(records, "records", path("records"));
			return true;
		} 
		for (String score : records) {
			String[] scoreName = score.split(":");
			double ratio = (Integer.parseInt(scoreName[1] + (0.5 * Integer.parseInt(scoreName[3]))) / (Integer.parseInt(scoreName[1] + Integer.parseInt(scoreName[2]) + Integer.parseInt(scoreName[3]))));
			if (player.getWltRatio() > ratio) {
				records.remove(records.size() - 1);
				records.add(add);
				writeToFile(records, "records", path("records"));
				return true;
			}
		}
		return false;
	}
	
}

