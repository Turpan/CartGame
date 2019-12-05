package cartGame.ui.travel;

import amyInterface.Component;
import amyInterface.Container;

public class TravelRoot extends Container {
	
	private TravelTopBar topBar;
	private TravelBottomBar bottomBar;
	
	public TravelRoot() {
		super();
		
		setBounds(0, 0, 480, 270);
		
		topBar = new TravelTopBar();
		bottomBar = new TravelBottomBar();
		
		addChild(topBar);
		addChild(bottomBar);
	}
	
	public TravelTopBar getTopBar() {
		return topBar;
	}
	
	public TravelBottomBar getBottomBar() {
		return bottomBar;
	}
	
	public Component getMapButton() {
		return topBar.getMapButton();
	}
	
	public Component getMenuButton() {
		return topBar.getMenuButton();
	}
	
	public Component getStopButton() {
		return bottomBar.getStopButton();
	}
	
}
