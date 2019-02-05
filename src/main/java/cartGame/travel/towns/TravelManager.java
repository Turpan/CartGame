package cartGame.travel.towns;

import cartGame.travel.cart.Wagon;
import movement.mathDS.Graph;

public class TravelManager {
	private Wagon wagon = new Wagon();
	
	private WorldMap map = new WorldMap();
	
	public TravelManager() {
		
	}
	
	public void setWagon(Wagon wagon) {
		this.wagon = wagon;
	}
	
	public Wagon getWagon() {
		return wagon;
	}
	
	public void setMap(WorldMap map) {
		this.map = map;
	}
	
	public WorldMap getMap() {
		return map;
	}
	
	public Road getCurrentRoad() {
		if (wagon.isTravelling()) {
			return map.getMap().getEdgeValue(wagon.getCurrentTown(), wagon.getDestinationTown());
		} else {
			return null;
		}
	}

	public void setDestination(Town town) {
		if (!canTravelTo(town)) {
			return;
		}
		wagon.setDestinationTown(town);
	}
	
	public boolean canTravelTo(Town town) {
		if (wagon.isTravelling()) {
			return false;
		}
		
		if (!map.getMap().isConnected(wagon.getCurrentTown(), town)) {
			return false;
		}
		
		return true;
	}
	
	public void travel() {
		if (wagon.isTravelling()) {
			Graph<Town, Road> map = this.map.getMap();
			Road toTravel = map.getEdgeValue(wagon.getCurrentTown(), wagon.getDestinationTown());
			
			wagon.addPosition(wagon.getPosition() + wagon.getSpeed());
			
			if (wagon.getPosition() >= toTravel.getDistance()) {
				wagon.setCurrentTown(wagon.getDestinationTown());
				wagon.setDestinationTown(null);
				wagon.setPosition(0);
			}
		}
	}
}
