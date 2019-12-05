package cartGame.travel.cart;

import java.util.HashMap;
import java.util.Map;

import cartGame.resources.Item;

public class Inventory {
	
	public static final Item food = new Item();
	public static final Item scrap = new Item();
	public static final Item medicine = new Item();
	
	private int money;
	private Map<Item, Integer> items = new HashMap<Item, Integer>();
	
	public Inventory() {
		food.setID("food");
		scrap.setID("scrap");
		medicine.setID("medicine");
		
		setMoney(200);
		
		addItem(food, 4);
		addItem(scrap, 4);
		addItem(medicine, 1);
	}
	
	public Item getItem(String id) {
		for (Item item : items.keySet()) {
			if (item.getID().equals(id)) {
				return item;
			}
		}
		
		return null;
	}
	
	public void addMoney(int change) {
		money += change;
	}
	
	public void setMoney(int money) {
		this.money = money;
	}
	
	public int getMoney() {
		return money;
	}
	
	public void addItem(Item item, int amount) {
		if (amount == 0) return;
		
		int count = 0;
		
		if (items.containsKey(item)) {
			count = items.get(item);
			amount += count;
		}
		
		items.put(item, amount);
	}
	
	public int getItemCount(Item item) {
		int amount = 0;
		
		if (items.containsKey(item)) {
			amount = items.get(item);
		}
		
		return amount;
	}
	
	public boolean isItemPresent(Item item, int amount) {
		if (!items.containsKey(item)) {
			return false;
		}
		
		int count = items.get(item);
		
		return count >= amount;
	}
	
	public boolean isItemPresent(Item item) {
		return isItemPresent(item, 1);
	}

}
