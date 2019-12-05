package cartGame.ui.town.shop;

import amyGraphics.Texture;
import amyInterface.CentreLayout;
import amyInterface.Container;
import cartGame.io.ImageCache;
import cartGame.ui.town.ResourceDisplay;

public class ResourceBar extends ResourceDisplay {
	
	public ResourceBar() {
		super();
		
		setBounds(98, 0, 283, 23);
		
		setVisible(true);
		
		Texture background = ImageCache.getTexture("graphics/ui/town/shop/resourcebar.png");
		addTexture(background);
		setActiveTexture(background);
		
		int x = 0;
		
		for (int i=0; i<resourceCounters.length; i++) {
			lastCount[i] = -1;
			
			resourceCounters[i] = createLabel();
			
			Container container = new Container();
			container.setBounds(16 + x, 7, 47, 12);
			container.setLayout(new CentreLayout());
			container.addChild(resourceCounters[i]);
			
			addChild(container);
			
			x += 72;
		}
		
		update();
	}
}
