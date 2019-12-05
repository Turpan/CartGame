package cartGame.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

import amyInterface.InterfaceController;
import cartGame.travel.graphics.Environment;
import cartGame.travel.graphics.TravelGraphic;
import cartGame.travel.graphics.TravelGraphicListener;
import cartGame.travel.towns.Road;
import cartGame.travel.towns.Town;
import cartGame.travel.towns.TravelManager;
import cartGame.travel.towns.TravelManagerListener;
import cartGame.ui.travel.TravelController;
import movement.Room;
import tests.TestTravel;

public class TravelMain implements TravelGraphicListener, TravelManagerListener {

	private TravelManager manager;
	
	private TravelGraphic graphics;
	
	private TravelController ui;
	
	private Map<String, List<Environment>> environmentPool = new LinkedHashMap<String, List<Environment>>();
	private List<String> environmentQueue = new ArrayList<String>();
	private List<String> environmentsPassed = new ArrayList<String>();
	
	public TravelMain() {
		manager = new TravelManager();
		manager.addListener(this);
		
		ui = new TravelController();
		
		//TODO remove later
		//graphics = new TestTravel();
		graphics = new TravelGraphic();
		graphics.addListener(this);
	}
	
	public TravelController getUI() {
		return ui;
	}
	
	public TravelGraphic getGraphic() {
		return graphics;
	}
	
	public TravelManager getManager() {
		return manager;
	}
	
	public void startTravel(Town destination) {
		if (manager.canTravelTo(destination)) {
			manager.setDestination(destination);
			
			Road road = manager.getCurrentRoad();
			String biomeID = null;
			for (String biome : road.getBiomes().keySet()) {
				biomeID = biome;
				break;
			}
			if (biomeID == null) {
				return;
			}
			
			environmentQueue.add(biomeID);
			advanceBiome();
			graphics.reset();
		}
	}
	
	private void advanceBiome() {
		if (environmentQueue.size() > 0) {
			String biomeID = environmentQueue.get(0);
			environmentQueue.remove(biomeID);
			List<Environment> environments = environmentPool.get(biomeID);
			environmentsPassed.add(biomeID);
			
			if (environments == null) {
				return;
			}	
			
			List<Integer> environmentOrder = new ArrayList<Integer>();
			for (Environment environment : environments) {
				int index = graphics.getEnvironments().indexOf(environment);
				environmentOrder.add(index);
			}
			
			graphics.setEnvironmentOrder(environmentOrder);
		}
	}

	public void setEnvironmentPool(Map<String, List<Environment>> environmentPool) {
		this.environmentPool.clear();
		
		for (String biomeID : environmentPool.keySet()) {
			for (Environment environment : environmentPool.get(biomeID)) {
				addEnvironment(biomeID, environment);
			}
		}
	}
	
	public void addEnvironment(String biomeID, Environment environment) {
		if (environmentPool.containsKey(biomeID)) {
			environmentPool.get(biomeID).add(environment);
		} else {
			List<Environment> environments = new ArrayList<Environment>();
			environments.add(environment);
			environmentPool.put(biomeID, environments);
		}
		
		graphics.addEnvironment(environment);
	}
	
	public void removeEnvironment(Environment environment) {
		for (String biomeID : environmentPool.keySet()) {
			if (environmentPool.get(biomeID).contains(environment)) {
				environmentPool.get(biomeID).remove(environment);
			}
		}
	}
	
	public void tick() {
		if (!manager.getWagon().isTravelling()) {
			return;
		}
		
		graphics.tick();
		ui.tick();
		ui.updateTime(graphics.getHour(), graphics.getMinute());
		
		if (graphics.hourPassed()) {
			manager.travel();
			
			Road road = manager.getCurrentRoad();
			if (road == null) return;
			
			double distanceTotal = 0;
			for (String biomeID : road.getBiomes().keySet()) {
				double distance = road.getBiomes().get(biomeID);
				distanceTotal += distance;
				
				if (manager.getWagon().getPosition() >= distanceTotal
						&& !environmentQueue.contains(biomeID)
						&& !environmentsPassed.contains(biomeID)) {
					environmentQueue.add(biomeID);
				}
			}
		}
		
		if (ui.isStopStartPressed()) {
			if (graphics.isMoving()) {
				graphics.stop();
			} else {
				graphics.start();
			}
		}
	}

	@Override
	public void environmentPassed() {
		advanceBiome();
	}

	@Override
	public void arrivedAtTown() {
		graphics.stop();
	}
	
}
