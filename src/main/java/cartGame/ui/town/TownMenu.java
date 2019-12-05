package cartGame.ui.town;

import amyInterface.Button;
import cartGame.travel.towns.Town;

public class TownMenu extends TownSky {
	
	private TownTopBar topBar;
	private TownBottomBar bottomBar;
	
	public TownMenu() {
		super();
		
		setBounds(0, 0, 480, 270);
		
		topBar = new TownTopBar();
		bottomBar = new TownBottomBar();
		
		addChild(topBar);
		addChild(bottomBar);
		
		loadSky();
	}
	
	@Override
	public void setHour(int hour) {
		super.setHour(hour);
		
		topBar.getClock().setHour(hour);
	}
	
	public void setTown(Town town) {
		townBackground.addTexture(town.getBackground());
		townBackground.setActiveTexture(town.getBackground());
		topBar.setTown(town);
	}
	
	public void setShopEnabled(boolean enabled) {
		bottomBar.setShopEnabled(enabled);
	}
	
	public Button getMapButton() {
		return topBar.getMapButton();
	}
	
	public Button getMenuButton() {
		return topBar.getMenuButton();
	}
	
	public Button getShopButton() {
		return bottomBar.getShopButton();
	}

	public TownTopBar getTopBar() {
		return topBar;
	}
	
	public TownBottomBar getBottomBar() {
		return bottomBar;
	}
}
