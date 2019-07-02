package cartGame.ui.town;

import amyGraphics.Texture;
import cartGame.io.ImageCache;

public class TownShop extends TownSky {
	public TownShop() {
		super();
		
		setBounds(0, 0, 480, 270);
		
		Texture background = ImageCache.getTexture("graphics/ui/town/shop.png");
		addTexture(background);
		setActiveTexture(background);
		
		townBackground.addTexture(background);
		townBackground.setActiveTexture(background);
		
		loadSky();
	}
}
