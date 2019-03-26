package yahtzee;

import java.util.ArrayList;

public class Menu {

	private String name;
	private ArrayList<Enum> menuItems = new ArrayList<Enum>();
	
	public Menu(String name) {
	}
	
	public void setMenuItems(ArrayList<Enum> menuItems) {
		this.menuItems = menuItems;
	}
	
	public ArrayList<Enum> getMenuItems() {
		return menuItems;
	}
	
	public String getName() {
		return name;
	}
	
	public Enum getEnum(int index) {
		return menuItems.get(index);
	}
	
}
