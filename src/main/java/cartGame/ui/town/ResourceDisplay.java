package cartGame.ui.town;

import java.awt.Color;

import amyInterface.Container;
import amyInterface.Label;
import cartGame.io.ImageCache;
import cartGame.travel.cart.Inventory;

public class ResourceDisplay extends Container {
	
	protected Label[] resourceCounters = new Label[4];
	protected int[] lastCount = new int[4];
	
	protected Inventory inventory;
	
	public ResourceDisplay() {
		super();
	}
	
	protected int[] getResourceCount() {
		int[] count = new int[lastCount.length];
		
		count[0] = inventory.getItemCount(inventory.getItem("food"));
		count[1] = inventory.getItemCount(inventory.getItem("medicine"));
		count[2] = inventory.getMoney();
		count[3] = inventory.getItemCount(inventory.getItem("scrap"));
		
		return count;
	}
	
	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}
	
	public void update() {
		if (inventory == null) return;
		
		int[] currentCount = getResourceCount();
		
		for (int i=0; i<lastCount.length; i++) {
			if (lastCount[i] == currentCount[i]) continue;
			
			lastCount[i] = currentCount[i];
			resourceCounters[i].setText(String.valueOf(currentCount[i]));
		}
	}
	
	public Label createLabel() {
		Label label = new Label();
		
		label.setFont(ImageCache.getFont("fonts/DSBehrensschrift.ttf"));
		//label.setFont(ImageCache.getFont("fonts/IrishPenny.ttf"));
		
		label.setFontSize(10);
		
		label.setColour(new Color(0, 0, 0, 0));
		
		label.setFontColour(Color.RED);
		
		label.setVisible(true);
		
		return label;
	}
}
