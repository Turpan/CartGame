package cartGame.ui.town;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import amyGraphics.Texture;
import amyInterface.Button;
import amyInterface.Container;
import cartGame.travel.towns.Town;

public class TownUI extends Container {
	
	TownMenuBar menuBar;
	TownSideBar sideBar;
	
	public TownUI() {
		Texture texture = loadTexture();
		addTexture(texture);
		setActiveTexture(texture);
		
		setBounds(0, 0, 640, 360);
		
		setVisible(true);
		
		menuBar = new TownMenuBar();
		sideBar = new TownSideBar();
		
		addChild(menuBar);
		addChild(sideBar);
	}
	
	public void setTown(Town town) {
		sideBar.setTownInfo(town.getName(), town.getDesc(), town.getIcon());
	}
	
	public Texture loadTexture() {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File("graphics/ui/town/background.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Texture texture = new Texture(image);
		
		return texture;
	}
	
	public Button getMapButton() {
		return menuBar.getMapButton();
	}
	
	public Button getStatsButton() {
		return menuBar.getStatsButton();
	}
	
	public Button getMenuButton() {
		return menuBar.getMenuButton();
	}
	
}
