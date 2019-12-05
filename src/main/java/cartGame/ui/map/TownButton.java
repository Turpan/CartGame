package cartGame.ui.map;

import amyInterface.Button;
import cartGame.travel.towns.Town;

public class TownButton extends Button {
	
	private boolean present;
	private Town town;
	
	public TownButton(Town town) {
		this.town = town;
		
		setVisible(true);
		setInteractable(true);
		
		int width = town.getMap().getWidth();
		int height = town.getMap().getHeight();
		
		setButtonTextures(town.getMap(), town.getMapHover(), town.getMapHover());
		addTexture(town.getMapPresent());
		
		setBounds(town.getMapX(), town.getMapY(), width, height);
	}
	
	public void setPresent(boolean present) {
		this.present = present;
		
		if (present) {
			setActiveTexture(town.getMapPresent());
		}
		
		updateButtonTexture();
	}
	
	public Town getTown() {
		return town;
	}

	@Override
	public void updateButtonTexture() {
		if (!present) {
			super.updateButtonTexture();
		}
	}
}
