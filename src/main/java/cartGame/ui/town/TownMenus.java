package cartGame.ui.town;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import amyGraphics.Texture;
import amyInterface.Button;
import amyInterface.Container;
import cartGame.io.ImageCache;
import cartGame.travel.towns.Town;

public class TownMenus extends Container {
	
	TownMenuBar menuBar;
	TownSideBar sideBar;
	
	public TownMenus() {
		Texture texture = loadTexture();
		addTexture(texture);
		setActiveTexture(texture);
		
		setBounds(0, 0, 640, 360);
		
		//setVisible(true);
		
		menuBar = new TownMenuBar();
		sideBar = new TownSideBar();
		
		addChild(menuBar);
		addChild(sideBar);
	}
	
	public void setTown(Town town) {
		sideBar.setTownInfo(town.getName(), town.getDesc(), town.getIcon());
	}
	
	public Texture loadTexture() {
		Texture texture = ImageCache.getTexture("graphics/ui/town/background.png");
		
		return texture;
	}
	
	public Button getMapButton() {
		return menuBar.getMapButton();
	}
	
	public Button getMenuButton() {
		return menuBar.getMenuButton();
	}
	
}
