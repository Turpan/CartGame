package cartGame.ui.travel;

import java.awt.Color;

import amyInterface.Button;
import amyInterface.Container;
import amyInterface.HorizontalLayout;
import amyInterface.Layout;
import cartGame.ui.town.TownMapButton;
import cartGame.ui.town.TownMenuButton;
import cartGame.ui.town.TownStatsButton;

public class TravelMenuBar extends Container {
	
	private TravelMapButton mapButton;
	
	private TravelStatsButton statsButton;
	
	private TravelMenuButton menuButton;
	
	public TravelMenuBar() {
		super();
		
		setColour(new Color(0, 0, 0, 0));
		setBounds(0, 0, 640, 14);
		
		Layout layout = new HorizontalLayout(HorizontalLayout.CENTREALIGN);
		setLayout(layout);
		
		setVisible(false);
		
		mapButton = new TravelMapButton();
		statsButton = new TravelStatsButton();
		menuButton = new TravelMenuButton();
		
		addChild(HorizontalLayout.createFreeSpace(2, 1));
		addChild(mapButton);
		addChild(HorizontalLayout.createGlue());
		addChild(statsButton);
		addChild(menuButton);
		addChild(HorizontalLayout.createFreeSpace(2, 1));
	}
	
	public Button getMapButton() {
		return mapButton;
	}
	
	public Button getStatsButton() {
		return statsButton;
	}
	
	public Button getMenuButton() {
		return menuButton;
	}
}
