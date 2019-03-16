package cartGame.ui.town;

import amyInterface.Container;
import cartGame.travel.towns.Town;

public class TownSplash extends Container {
	
	public TownSplash() {
		super();
		
		setBounds(0, 0, 640, 360);
		
		setVisible(true);
		setInteractable(true);
	}
	
	public void setTown(Town town) {
		addTexture(town.getIntro());
		setActiveTexture(town.getIntro());
	}
}
