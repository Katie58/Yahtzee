package yahtzee;

import java.util.ArrayList;
import java.util.EnumSet;

public class YahtzeeApp {
	static boolean exit = false;
	
	public static void main(String[] args) {
		while(!exit) {
			Menu main = new Menu("main");
			main.setMenuItems(new ArrayList<Enum>(EnumSet.allOf(MainMenu.class)));			
			YahtzeeUtil.menu(main);
		}
		YahtzeeUtil.exit();
	}	
}
