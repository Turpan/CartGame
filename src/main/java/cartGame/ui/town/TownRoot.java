package cartGame.ui.town;

import amyInterface.Button;
import amyInterface.Container;
import cartGame.travel.towns.Town;

public class TownRoot extends Container {
	
	private TownMenu menu;
	private TownShop shop;
	
	public TownRoot() {
		super();
		
		setBounds(0, 0, 480, 270);
		
		menu = new TownMenu();
		shop = new TownShop();
		
		addChild(menu);
		addChild(shop);
		
		switchToMenu();
	}
	
	public void switchToMenu() {
		menu.setBounds(0, 0, 480, 270);
		shop.setBounds(-1, -1, 0, 0);
	}
	
	public void switchToShop() {
		menu.setBounds(-1, -1, 0, 0);
		shop.setBounds(0, 0, 480, 270);
	}
	
	public void setTown(Town town) {
		menu.setTown(town);
	}
	
	public void setHour(int hour) {
		menu.setHour(hour);
		shop.setHour(hour);
	}
	
	public Button getMapButton() {
		return menu.getMapButton();
	}
	
	public Button getMenuButton() {
		return menu.getMenuButton();
	}
	
	public Button getShopButton() {
		return menu.getShopButton();
	}
	
	
}
