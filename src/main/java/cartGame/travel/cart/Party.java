package cartGame.travel.cart;

import java.util.ArrayList;
import java.util.List;

import cartGame.resources.Item;

public class Party {
	
	private static final int FOODNEEDED = 1;
	private static final int STARVATIONPANELTY = 10;
	
	private Inventory inventory;
	
	private List<Traveller> partyMembers = new ArrayList<Traveller>();
	
	public Party() {
		inventory = new Inventory();
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public List<Traveller> getPartyMembers() {
		return partyMembers;
	}

	public void setPartyMembers(List<Traveller> partyMembers) {
		this.partyMembers = partyMembers;
	}
	
	public void eat() {
		Item food = Inventory.food;
		
		for (Traveller traveller : partyMembers) {
			int foodHeld = inventory.getItemCount(food);
			
			if (foodHeld < FOODNEEDED) {
				traveller.setHealth(traveller.getHealth() - 10);
			} else {
				inventory.addItem(food, -FOODNEEDED);
			}
		}
	}
}
