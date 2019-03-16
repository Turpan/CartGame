package cartGame.core;

import cartGame.io.GraphicsQueue;
import cartGame.travel.towns.Town;
import cartGame.ui.town.TownController;

public class TownMain {
	private TownController fromTown;
	private TownController toTown;
	
	private TownController activeTown;
	
	public TownMain() {
		
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
	}
	
	public void createToTownInterface(Town town) {
		toTown = new TownController();
		toTown.loadTownInfo(town);
	}
	
	public void arrivedAtTown() {
		GraphicsQueue.addSceneToRemoveQueue(fromTown.getRoot());
		GraphicsQueue.addSceneToAddQueue(toTown.getRoot());
		
		fromTown = toTown;
		toTown = null;
		
		activeTown = fromTown;
	}
	
}
