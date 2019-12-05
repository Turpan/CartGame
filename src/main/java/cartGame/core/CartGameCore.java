package cartGame.core;

import java.util.List;
import java.util.Map;

import amyGLGraphics.IO.GraphicsQueue;
import cartGame.travel.cart.Traveller;
import cartGame.travel.graphics.Environment;
import cartGame.travel.towns.Town;
import cartGame.travel.towns.TravelManagerListener;
import cartGame.travel.towns.WorldMap;
import cartGame.ui.map.MapController;
import cartGame.ui.map.MapListener;
import tests.TestParty;
import tests.towns.TestEnvironmentPool;
import tests.towns.TestMap;

public class CartGameCore implements TravelManagerListener, MapListener {
	
	private TravelMain travel;
	private TownMain town;
	private MapController map;
	
	private boolean mapOpen;
	private boolean mapFromTravel;
	
	public CartGameCore() {
		travel = new TravelMain();
		town = new TownMain();
		map = new MapController();
		
		travel.setEnvironmentPool(loadEnvironments());
		travel.getUI().setParty(travel.getManager().getParty());
		travel.getManager().getParty().setPartyMembers(loadParty());
		travel.getManager().addListener(this);
		travel.getManager().setMap(loadWorldMap());
		GraphicsQueue.addSceneToAddQueue(travel.getUI().getRoot());
		GraphicsQueue.addRoomToAddQueue(travel.getGraphic());
		
		town.setInventory(travel.getManager().getParty().getInventory());
		town.createFromTownInterface(travel.getManager().getMap().getStart());
		town.setActiveTown();
		town.getActiveTown().setHour(12);
		GraphicsQueue.addSceneToAddQueue(town.getFromTown().getRoot());
		GraphicsQueue.setSceneToSwitch(town.getFromTown().getRoot());
		
		map.addListener(this);
		map.getMapUI().setMap(travel.getManager().getMap());
		map.getMapUI().updateButtons(travel.getManager().getWagon());
		GraphicsQueue.addSceneToAddQueue(map.getRoot());
	}

	public void tick() {
		travel.tick();
		if (!mapOpen) town.tick();
		
		boolean travelMapRequest = travel.getUI().isMapOpenPressed();
		boolean townMapRequest = town.getActiveTown().isMapOpenPressed();
		
		if (travelMapRequest || townMapRequest) {
			GraphicsQueue.setSceneToSwitch(map.getRoot());
			mapOpen = true;
			mapFromTravel = true;
		}
		
		if (townMapRequest) {
			map.destinationRequired();
			mapFromTravel = false;
		}
		
		if (mapOpen) {
			map.tick();
			if (map.isBackPressed()) {
				mapOpen = false;
				GraphicsQueue.setSceneToSwitch((mapFromTravel) ? travel.getUI().getRoot() : town.getActiveTown().getRoot());
			}
		}
	}
	
	private WorldMap loadWorldMap() {
		//TODO actually load this later
		return new TestMap();
	}
	
	private List<Traveller> loadParty() {
		//TODO actually load this later
		return new TestParty().getPartyMembers();
	}
	
	private Map<String, List<Environment>> loadEnvironments() {
		// TODO actually load this later
		return new TestEnvironmentPool();
	}

	@Override
	public void arrivedAtTown() {
		town.arrivedAtTown();
		town.getActiveTown().setHour(travel.getGraphic().getHour());
		map.getMapUI().updateButtons(travel.getManager().getWagon());
	}

	@Override
	public void destinationSelected() {
		String destinationID = map.getTravelRequest();
		Town destination = null;
		
		for (Town town : travel.getManager().getMap().getTowns()) {
			if (town.getID().equals(destinationID)) {
				destination = town;
				break;
			}
		}
		
		if (destination == null) {
			map.destinationRequired();
			return;
		}
		
		if (!travel.getManager().canTravelTo(destination)) {
			map.destinationRequired();
			return;
		}
		
		mapOpen = false;
		
		town.createToTownInterface(destination);
		GraphicsQueue.addSceneToAddQueue(town.getToTown().getRoot());
		
		GraphicsQueue.setRoomToSwitch(travel.getGraphic());
		GraphicsQueue.setSceneToSwitch(travel.getUI().getRoot());
		
		travel.startTravel(destination);
		travel.getGraphic().setTime(town.getFromTown().getInterface().getHour());
	}
}
