package cartGame.ui.travel;

import amyGraphics.Texture;
import amyInterface.Container;
import cartGame.io.ImageCache;

public class TravelHealthPlate extends Container {
	
	public TravelHealthPlate() {
		super();
		
		setBounds(93, 17, 202, 56);
		setVisible(true);
		
		Texture texture = ImageCache.getTexture("graphics/ui/travel/healthplate.png");
		addTexture(texture);
		setActiveTexture(texture);
	}

}
