package cartGame.ui.town;

import java.awt.Color;

import amyInterface.Button;
import amyInterface.Container;
import amyInterface.HorizontalLayout;
import amyInterface.Layout;

public class TownMenuBar extends Container {
	
	private Button mapButton;
	private Button menuButton;
	
	public TownMenuBar() {
		super();
		
		setColour(new Color(0, 0, 0, 0));
		setBounds(0, 0, 640, 14);
		
		Layout layout = new HorizontalLayout(HorizontalLayout.CENTREALIGN);
		setLayout(layout);
		
		setVisible(false);
		
		mapButton = new TownMapButton();
		menuButton = new TownMenuButton();
		
		addChild(HorizontalLayout.createFreeSpace(2, 1));
		addChild(mapButton);
		addChild(HorizontalLayout.createGlue());
		addChild(menuButton);
		addChild(HorizontalLayout.createFreeSpace(2, 1));
	}
	
	public Button getMapButton() {
		return mapButton;
	}
	
	public Button getMenuButton() {
		return menuButton;
	}
}
