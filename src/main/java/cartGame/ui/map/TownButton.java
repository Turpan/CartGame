package cartGame.ui.map;

import amyGraphics.Texture;
import amyInterface.Button;
import cartGame.io.ImageCache;

public class TownButton extends Button {
	
	private static final int width = 40;
	private static final int height = 40;
	
	public TownButton(int x, int y) {
		setVisible(true);
		setInteractable(true);
		
		setButtonTextures(getTexture(), getPressedTexture());
		
		setBounds(x, y, width, height);
	}
	
	private Texture getTexture() {
		return ImageCache.getTexture("graphics/ui/map/town.png");
	}
	
	private Texture getPressedTexture() {
		return ImageCache.getTexture("graphics/ui/map/town-pressed.png");
	}

}
