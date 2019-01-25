package cartGame.core;

import java.util.ArrayList;
import java.util.List;

import amyInterface.InterfaceController;
import cartGame.travel.graphics.TravelGraphic;
import cartGame.travel.towns.TravelManager;
import cartGame.ui.travel.TravelController;
import movement.Room;
import tests.TestDeepForest;

public class TravelMain {

	private TravelManager manager;
	
	private List<TravelGraphic> environments = new ArrayList<TravelGraphic>();
	private TravelController ui;
	
	public TravelMain() {
		manager = new TravelManager();
		ui = new TravelController();
		
		//TODO remove later
		environments.add(new TestDeepForest());
	}
	
	public InterfaceController getUI() {
		return ui;
	}
	
	public Room getGraphic() {
		return environments.get(0);
	}
	
	public void tick() {
		//TODO also remove later
		TravelGraphic activeEnvironment = environments.get(0);
		activeEnvironment.tick();
		ui.tick();
		
		if (activeEnvironment.hourPassed()) {
			manager.travel();
		}
		
		if (ui.isStopStartPressed()) {
			if (activeEnvironment.isMoving()) {
				activeEnvironment.stop();
			} else {
				activeEnvironment.start();
			}
		}
	}
	
}
