package cartGame.travel.towns;

import java.util.ArrayList;
import java.util.List;

import cartGame.travel.cart.Party;
import cartGame.travel.cart.Wagon;
import movement.mathDS.Graph;

public class TravelManager {
	private Wagon wagon = new Wagon();
	private Party party = new Party();
	
	private WorldMap map = new WorldMap();
	
	private List<TravelManagerListener> listeners = new ArrayList<TravelManagerListener>();
	
	public TravelManager() {
		
	}
	
	public void setWagon(Wagon wagon) {
		this.wagon = wagon;
	}
	
	public Wagon getWagon() {
		return wagon;
	}
	
	public Party getParty() {
		return party;
	}

	public void setParty(Party party) {
		this.party = party;
	}

	public void setMap(WorldMap map) {
		this.map = map;
		wagon.setCurrentTown(map.getStart());
	}
	
	public WorldMap getMap() {
		return map;
	}
	
	public void addListener(TravelManagerListener listener) {
		listeners.add(listener);
	}
	
	public void removeListener(TravelManagerListener listener) {
		listeners.remove(listener);
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
			
			wagon.addPosition(wagon.getSpeed());
			party.eat();
			
			if (wagon.getPosition() >= toTravel.getDistance()) {
				wagon.setCurrentTown(wagon.getDestinationTown());
				wagon.setDestinationTown(null);
				wagon.setPosition(0);
				
				for (TravelManagerListener listener : listeners) listener.arrivedAtTown();
			}
		}
	}
}
