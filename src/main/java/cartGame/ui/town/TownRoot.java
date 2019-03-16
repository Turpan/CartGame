package cartGame.ui.town;

import amyInterface.Component;
import amyInterface.Container;
import cartGame.travel.towns.Town;

public class TownRoot extends Container {
	
	private TownSplash splash;
	private TownBackground background;
	private TownMenus menus;
	
	public TownRoot() {
		super();
		
		setBounds(0, 0, 640, 360);
		
		setVisible(true);
		
		background = new TownBackground();
		menus = new TownMenus();
		splash = new TownSplash();
		
		addChild(splash);
		addChild(background);
		addChild(menus);
		
		switchToTown();
	}
	
	public void switchToTown() {
		splash.setVisible(false);
		splash.setBounds(-1, -1, 0, 0);
		background.setVisible(true);
		menus.setVisible(true);
	}
	
	public void setHour(int hour) {
		background.setHour(hour);
	}

	public void setTown(Town town) {
		splash.setTown(town);
		background.setTown(town);
		menus.setTown(town);
	}
	
	public TownBackground getTownBackground() {
		return background;
	}
	
	public Component getSplashScreen() {
		return splash;
	}

	public Component getMapButton() {
		return menus.getMapButton();
	}

	public Component getMenuButton() {
		return menus.getMenuButton();
	}
}
