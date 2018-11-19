package cartGame.travel.cart;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cartGame.resources.Item;

public class Inventory {
	
	Map<String, List<Item>> items;
	
	public Inventory() {
		items = new HashMap<String, List<Item>>();
	}
	
	public void addItemCatagory(String type) {
		List<Item> list = new ArrayList<Item>();
		
		items.put(type, list);
	}
	
	public void removeItemCatagory(String type) {
		items.remove(type);
	}
	
	public void addItem(String type, Item item) {
		items.get(type).add(item);
	}
	
	public void addItem(String type, Collection<Item> item) {
		items.get(type).addAll(item);
	}
	
	public void removeItem(String type, Item item) {
		items.get(type).remove(item);
	}
	
	public void removeItem(String type, Collection<Item> item) {
		items.get(type).removeAll(item);
	}
	
	public Map<String, List<Item>> getItems() {
		return items;
	}

}
