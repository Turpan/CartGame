package tests.towns;

import cartGame.resources.Food;
import cartGame.resources.Medicine;
import cartGame.resources.Scrap;
import cartGame.travel.towns.Shop;

public class DefaultShop extends Shop {
	public DefaultShop() {
		super();
		
		setID("default");
		
		Food food = new Food();
		Medicine medicine = new Medicine();
		Scrap scrap = new Scrap();
		
		addItem(food, 5);
		addItem(medicine, 20);
		addItem(scrap, 10);
	}
}
