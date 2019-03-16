package cartGame.ui.town;

import java.util.ArrayList;
import java.util.List;

import amyGraphics.Texture;
import amyInterface.Component;
import amyInterface.Container;
import cartGame.io.ImageCache;
import cartGame.travel.towns.Town;

public class TownBackground extends Container {
	
	private List<Texture> skyTextures = new ArrayList<Texture>();
	
	private Component skyBackground;
	private Component townBackground;
	
	public TownBackground() {
		super();
		
		setBounds(2, 16, 484, 342);
		
		//setVisible(true);
		
		skyBackground = new Component();
		skyBackground.setBounds(0, 0, 484, 342);
		skyBackground.setVisible(true);
		
		townBackground = new Component();
		townBackground.setBounds(0, 0, 484, 342);
		townBackground.setVisible(true);
		
		addChild(skyBackground);
		addChild(townBackground);
		
		loadSky();
	}
	
	private void loadSky() {
		addSky(ImageCache.getTexture("graphics/travel/sky/" + 12 + "am.png"));
		
		for (int i=1; i<12; i++) {
			addSky(ImageCache.getTexture("graphics/travel/sky/" + i + "am.png"));
		}
		
		addSky(ImageCache.getTexture("graphics/travel/sky/" + 12 + "pm.png"));
		
		for (int i=1; i<12; i++) {
			addSky(ImageCache.getTexture("graphics/travel/sky/" + i + "pm.png"));
		}
	}
	
	public void setHour(int hour) {
		if (skyTextures.size() == 0) {
			return;
		}
		
		if (hour < 0) {
			hour = 0;
		} else if (hour >= skyTextures.size()) {
			hour = skyTextures.size() - 1;
		}
		
		skyBackground.setActiveTexture(skyTextures.get(hour));
	}
	
	public void setTown(Town town) {
		townBackground.addTexture(town.getBackground());
		townBackground.setActiveTexture(town.getBackground());
	}
	
	public void addSky(Texture texture) {
		skyTextures.add(texture);
		skyBackground.addTexture(texture);
	}

}
