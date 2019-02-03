package cartGame.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

import amyInterface.InterfaceController;
import cartGame.travel.graphics.Environment;
import cartGame.travel.graphics.TravelGraphic;
import cartGame.travel.graphics.TravelGraphicListener;
import cartGame.travel.towns.TravelManager;
import cartGame.ui.travel.TravelController;
import movement.Room;
import tests.TestTravel;

public class TravelMain implements TravelGraphicListener {

	private TravelManager manager;
	
	private TravelGraphic graphics;
	
	private TravelController ui;
	
	private Map<String, List<Environment>> environmentPool = new LinkedHashMap<String, List<Environment>>();
	
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
		// TODO Auto-generated method stub
		
	}
	
}
