package cartGame.core;

import java.util.ArrayList;
import java.util.List;

import amyGLGraphics.IO.GraphicsQueue;
import cartGame.travel.cart.Inventory;
import cartGame.travel.towns.Town;
import cartGame.ui.town.TownController;

public class TownMain {
	private TownController fromTown;
	private TownController toTown;
	
	private TownController activeTown;
	
	private Inventory inventory;
	
	public TownMain() {
		
	}
	
	public void setActiveTown() {
		activeTown = fromTown;
	}
	
	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}
	
	public TownController getFromTown() {
		return fromTown;
	}

	public void setFromTown(TownController fromTown) {
		this.fromTown = fromTown;
	}

	public TownController getToTown() {
		return toTown;
	}

	public void setToTown(TownController toTown) {
		this.toTown = toTown;
	}
	
	public TownController getActiveTown() {
		return activeTown;
	}
	
	public void tick() {
		if (activeTown == null) {
			return;
		}
		
		activeTown.tick();
	}

	public void createFromTownInterface(Town town) {
		fromTown = new TownController();
		fromTown.loadTownInfo(town);
		fromTown.setInventory(inventory);
	}
	
	public void createToTownInterface(Town town) {
		toTown = new TownController();
		toTown.loadTownInfo(town);
		toTown.setInventory(inventory);
	}
	
	public void arrivedAtTown() {
		GraphicsQueue.addSceneToRemoveQueue(fromTown.getRoot());
		GraphicsQueue.addSceneToAddQueue(toTown.getRoot());
		
		fromTown = toTown;
		toTown = null;
		
		activeTown = fromTown;
		
		GraphicsQueue.setSceneToSwitch(activeTown.getRoot());
	}
	
}
