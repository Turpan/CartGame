package cartGame.ui.town;

import amyGraphics.Texture;
import amyInterface.Button;
import amyInterface.Component;
import amyInterface.Container;
import cartGame.io.ImageCache;
import cartGame.travel.towns.Town;

public class TownTopBar extends Container {
	
	private Component clock;
	private Component resources;
	private TownTitle title;
	private Button mapButton;
	private Button menuButton;
	
	public TownTopBar() {
		super();
		
		setBounds(0, 0, 480, 65);
		
		clock = new Component();
		Texture clockTexture = ImageCache.getTexture("graphics/ui/town/clock.png");
		clock.addTexture(clockTexture);
		clock.setActiveTexture(clockTexture);
		clock.setVisible(true);
		clock.setBounds(3, 0, 51, 65);
		
		resources = new Component();
		Texture resourceTexture = ImageCache.getTexture("graphics/ui/town/resourcebar.png");
		resources.addTexture(resourceTexture);
		resources.setActiveTexture(resourceTexture);
		resources.setVisible(true);
		resources.setBounds(59, 0, 139, 45);
		
		title = new TownTitle();
		
		Texture mapTexture = ImageCache.getTexture("graphics/ui/town/mapbutton.png");
		Texture mapHover = ImageCache.getTexture("graphics/ui/town/mapbutton-hover.png");
		Texture mapPressed = ImageCache.getTexture("graphics/ui/town/mapbutton-pressed.png");
		
		mapButton = new Button(mapTexture, mapPressed, mapHover);
		mapButton.setVisible(true);
		mapButton.setBounds(436, 0, 18, 21);
		
		Texture menuTexture = ImageCache.getTexture("graphics/ui/town/menubutton.png");
		Texture menuHover = ImageCache.getTexture("graphics/ui/town/menubutton-hover.png");
		Texture menuPressed = ImageCache.getTexture("graphics/ui/town/menubutton-pressed.png");
		
		menuButton = new Button(menuTexture, menuPressed, menuHover);
		menuButton.setVisible(true);
		menuButton.setBounds(459, 0, 18, 21);
		
		addChild(clock);
		addChild(resources);
		addChild(title);
		addChild(mapButton);
		addChild(menuButton);
	}
	
	public void setTown(Town town) {
		title.setTown(town);
	}
	
	public Button getMapButton() {
		return mapButton;
	}
	
	public Button getMenuButton() {
		return menuButton;
	}
	
}
