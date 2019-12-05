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
	
	public Item getItem(String ID) {
		for (Item item : goods.keySet()) {
			if (item.getID().equals(ID)) return item;
		}
		
		return null;
	}
	
	public boolean hasItem(Item item) {
		return goods.containsKey(item);
	}
	
	public boolean hasItem(String ID) {
		for (Item item : goods.keySet()) {
			if (item.getID().equals(ID)) return true;
		}
		
		return false;
	}
	
	public int getItemPrice(Item item) {
		if (goods.containsKey(item)) {
			return goods.get(item);
		}
		
		return 0;
	}

}
