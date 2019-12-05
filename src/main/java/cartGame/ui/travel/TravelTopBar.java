package cartGame.ui.travel;

import amyGraphics.Texture;
import amyInterface.Button;
import amyInterface.Component;
import amyInterface.Container;
import cartGame.io.ImageCache;

public class TravelTopBar extends Container {
	
	private TravelClock clock;
	private TravelResources resources;
	private Button mapButton;
	private Button menuButton;

	public TravelTopBar() {
		super();
		
		setBounds(0, 0, 480, 65);
		
		clock = new TravelClock();
		
		resources = new TravelResources();
		
		Texture mapTexture = ImageCache.getTexture("graphics/ui/travel/mapbutton.png");
		Texture mapHover = ImageCache.getTexture("graphics/ui/travel/mapbutton-hover.png");
		Texture mapPressed = ImageCache.getTexture("graphics/ui/travel/mapbutton-pressed.png");
		
		mapButton = new Button(mapTexture, mapPressed, mapHover);
		mapButton.setVisible(true);
		mapButton.setBounds(436, 0, 18, 21);
		
		Texture menuTexture = ImageCache.getTexture("graphics/ui/travel/menubutton.png");
		Texture menuHover = ImageCache.getTexture("graphics/ui/travel/menubutton-hover.png");
		Texture menuPressed = ImageCache.getTexture("graphics/ui/travel/menubutton-pressed.png");
		
		menuButton = new Button(menuTexture, menuPressed, menuHover);
		menuButton.setVisible(true);
		menuButton.setBounds(459, 0, 18, 21);
		
		addChild(clock);
		addChild(resources);
		addChild(mapButton);
		addChild(menuButton);
	}
	
	public TravelClock getClock() {
		return clock;
	}
	
	public TravelResources getResourceBar() {
		return resources;
	}
	
	public Button getMapButton() {
		return mapButton;
	}
	
	public Button getMenuButton() {
		return menuButton;
	}
	
}
