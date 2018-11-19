package cartGame.ui.travel;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import amyGraphics.Texture;
import amyInterface.Button;
import amyInterface.Container;

public class TravelUI extends Container {
	
	TravelMenuBar menuBar;
	TravelNameContainer namePlates;
	
	public TravelUI() {
		Texture texture = loadTexture();
		addTexture(texture);
		setActiveTexture(texture);
		
		setBounds(0, 0, 640, 360);
		
		setVisible(true);
		
		menuBar = new TravelMenuBar();
		namePlates = new TravelNameContainer();
		
		addChild(menuBar);
		addChild(namePlates);
	}
	
	public Texture loadTexture() {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File("graphics/ui/travel/background.png"));
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
