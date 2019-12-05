package cartGame.ui.town;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import amyInterface.Button;
import amyInterface.Container;
import cartGame.travel.cart.Inventory;
import cartGame.travel.towns.Town;
import cartGame.ui.town.shop.TownShop;

public class TownRoot extends Container {
	
	private TownMenu menu;
	private TownShop shop;
	
	private int hour;
	
	public TownRoot() {
		super();
		
		setBounds(0, 0, 480, 270);
		
		menu = new TownMenu();
		shop = new TownShop();
		
		menu.setShopEnabled(false);
		
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
		shop.setShop(town.getShop());
		
		menu.setShopEnabled(town.getShop() != null);
	}
	
	public void setHour(int hour) {
		this.hour = hour;
		
		menu.setHour(hour);
		shop.setHour(hour);
	}
	
	public int getHour() {
		return hour;
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
	
	public TownMenu getMenu() {
		return menu;
	}
	
	public TownShop getShop() {
		return shop;
	}
	
	public List<Button> getBackButtons() {
		List<Button> buttons = new ArrayList<Button>();
		buttons.add(shop.getBackButton());
		return buttons;
	}

	public List<Button> getShopAddButtons() {
		return Arrays.asList(shop.getShopPanel().getAddButtons());
	}

	public List<Button> getShopRemoveButtons() {
		return Arrays.asList(shop.getShopPanel().getRemoveButtons());
	}
	
	public List<Button> getShopPurchaseButtons() {
		return Arrays.asList(shop.getShopPanel().getConfirmButtons());
	}

	public void shopChangeAmount(int amount, int item) {
		shop.changeAmount(amount, item);
	}	
}
