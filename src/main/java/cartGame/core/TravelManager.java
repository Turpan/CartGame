package cartGame.core;

import cartGame.travel.cart.Wagon;
import cartGame.travel.towns.Road;
import cartGame.travel.towns.Town;
import cartGame.travel.towns.WorldMap;
import movement.mathDS.Graph;

public class TravelManager {
	Wagon wagon;
	
	WorldMap map;
	
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
