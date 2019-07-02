package cartGame.ui.travel;

import amyGraphics.Texture;
import amyInterface.Button;
import amyInterface.Component;
import amyInterface.Container;
import cartGame.io.ImageCache;

public class TravelTopBar extends Container {
	
	private Component clock;
	private Component resources;
	private Button mapButton;
	private Button menuButton;

	public TravelTopBar() {
		super();
		
		setBounds(0, 0, 480, 65);
		
		clock = new Component();
		Texture clockTexture = ImageCache.getTexture("graphics/ui/travel/clock.png");
		clock.addTexture(clockTexture);
		clock.setActiveTexture(clockTexture);
		clock.setVisible(true);
		clock.setBounds(3, 0, 51, 65);
		
		resources = new Component();
		Texture resourceTexture = ImageCache.getTexture("graphics/ui/travel/resourcebar.png");
		resources.addTexture(resourceTexture);
		resources.setActiveTexture(resourceTexture);
		resources.setVisible(true);
		resources.setBounds(59, 0, 139, 45);
		
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
	
	public Button getMapButton() {
		return mapButton;
	}
	
	public Button getMenuButton() {
		return menuButton;
	}
	
}
