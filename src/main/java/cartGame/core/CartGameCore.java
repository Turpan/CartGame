package cartGame.core;

import cartGame.io.GraphicsQueue;
import cartGame.travel.towns.TravelManagerListener;
import cartGame.travel.towns.WorldMap;
import cartGame.ui.map.MapController;

public class CartGameCore implements TravelManagerListener {
	
	private TravelMain travel;
	private TownMain town;
	private MapController map;
	
	private boolean mapOpen;
	
	public CartGameCore() {
		travel = new TravelMain();
		town = new TownMain();
		map = new MapController();
		
		travel.getManager().addListener(this);
		travel.getManager().setMap(loadWorldMap());
		
		town.createFromTownInterface(travel.getManager().getMap().getStart());
		GraphicsQueue.addSceneToAddQueue(town.getFromTown().getRoot());
		GraphicsQueue.setSceneToSwitch(town.getFromTown().getRoot());
		
		GraphicsQueue.addSceneToAddQueue(map.getRoot());
	}
	
	public void tick() {
		travel.tick();
		town.tick();
		
		if (travel.getUI().isMapOpenPressed() || town.getActiveTown().isMapOpenPressed()) {
			GraphicsQueue.setSceneToSwitch(map.getRoot());
			mapOpen = true;
		}
		
		if (mapOpen) map.tick();
	}
	
	private WorldMap loadWorldMap() {
		//TODO actually load this later
		return null;
	}

	@Override
	public void arrivedAtTown() {
		town.arrivedAtTown();
	}
}
