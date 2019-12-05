package cartGame.ui.town.shop;

import amyGraphics.Texture;
import amyInterface.Container;
import cartGame.io.ImageCache;

public class ShopInfo extends Container {
	public ShopInfo() {
		super();
		
		setBounds(10, 48, 114, 162);
		
		setVisible(true);
		
		Texture background = ImageCache.getTexture("graphics/ui/town/shop/resourcelist.png");
		addTexture(background);
		setActiveTexture(background);
	}
}
