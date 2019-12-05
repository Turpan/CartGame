package cartGame.ui.town.shop;

import amyGraphics.Texture;
import amyInterface.Container;
import cartGame.io.ImageCache;

public class ResourcePanel extends Container {
	public ResourcePanel(int x) {
		super();
		
		Texture resourceTexture = ImageCache.getTexture("graphics/ui/town/shop/resourcedisplay.png");
		
		addTexture(resourceTexture);
		setActiveTexture(resourceTexture);
		setBounds(0 + x, 0, 68, 74);
		setVisible(true);
	}
}
