package cartGame.travel.towns;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import cartGame.resources.Item;

public class Shop {
	
	String ID;
	
	Map<Item, Integer> goods;
	
	public Shop() {
		goods = new HashMap<Item, Integer>();
	}
	
	public void addItem(Item item, int price) {
		goods.put(item, price);
	}
	
	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public Set<Item> getItems() {
		return goods.keySet();
	}
	
	public boolean hasItem(Item item) {
		return goods.containsKey(item);
	}
	
	public int getItemPrice(Item item) {
		return goods.get(item);
	}

}
