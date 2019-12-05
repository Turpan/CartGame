package cartGame.ui.town;

import amyGraphics.Texture;
import amyInterface.CentreLayout;
import amyInterface.Container;
import cartGame.io.ImageCache;

public class TownResource extends ResourceDisplay {
	
	public TownResource() {
		super();
		
		Texture resourceTexture = ImageCache.getTexture("graphics/ui/town/resourcebar.png");
		addTexture(resourceTexture);
		setActiveTexture(resourceTexture);
		setVisible(true);
		setBounds(59, 0, 139, 45);
		
		int x = 0;
		int y = 0;
		
		for (int i=0; i<resourceCounters.length; i++) {
			lastCount[i] = -1;
			
			resourceCounters[i] = createLabel();
			
			Container container = new Container();
			container.setBounds(16 + x, 7 + y, 47, 12);
			container.setLayout(new CentreLayout());
			container.addChild(resourceCounters[i]);
			
			addChild(container);
			
			x += 72;
			
			if ((i+1) % 2 == 0) {
				x = 0;
				y += 22;
			}
		}
		
		update();
	}
}
