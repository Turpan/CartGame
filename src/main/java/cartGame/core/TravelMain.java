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
import cartGame.travel.towns.TravelManager;
import cartGame.ui.travel.TravelController;
import movement.Room;
import tests.TestTravel;

public class TravelMain implements TravelGraphicListener {

	private TravelManager manager;
	
	private TravelGraphic graphics;
	
	private TravelController ui;
	
	private Map<String, List<Environment>> environmentPool = new LinkedHashMap<String, List<Environment>>();
	private List<String> environmentQueue = new ArrayList<String>();
	private List<String> environmentsPassed = new ArrayList<String>();
	
	public TravelMain() {
		manager = new TravelManager();
		ui = new TravelController();
		
		//TODO remove later
		graphics = new TestTravel();
	}
	
	public InterfaceController getUI() {
		return ui;
	}
	
	public Room getGraphic() {
		return graphics;
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
		//TODO also remove later
		graphics.tick();
		ui.tick();
		
		if (graphics.hourPassed()) {
			manager.travel();
			
			if (manager.getWagon().isTravelling()) {
				Road road = manager.getCurrentRoad();
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
	
}
