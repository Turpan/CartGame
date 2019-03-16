package cartGame.ui.map;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import amyGraphics.Texture;
import amyInterface.Button;
import amyInterface.Container;
import cartGame.travel.cart.Wagon;
import cartGame.travel.graphics.Cart;
import cartGame.travel.towns.Town;
import cartGame.travel.towns.WorldMap;
import cartGame.ui.town.TownMenuBar;
import cartGame.ui.town.TownSideBar;

public class MapUI extends Container {
	
	private Map<TownButton, String> buttonIDs = new LinkedHashMap<TownButton, String>();
	
	public MapUI() {
		Texture texture = loadTexture();
		addTexture(texture);
		setActiveTexture(texture);
		
		setBounds(0, 0, 640, 360);
		
		setVisible(true);
	}
	
	public void populateMap(WorldMap map, Wagon wagon) {
		for (Town town : map.getTowns()) {
			addTownButton(town);
		}
	}
	
	public void updateButtons(WorldMap map, Wagon wagon) {
		for (TownButton button : buttonIDs.keySet()) {
			setInteractable(false);
		}
		
		if (wagon.isTravelling()) {
			return;
		}
		
		
	}
	
	public void addTownButton(Town town) {
		TownButton button = new TownButton(town.getMapX(), town.getMapY());
		
		addChild(button);
		buttonIDs.put(button, town.getID());
	}
	
	public void removeTownButton(Button button) {
		removeChild(button);
		buttonIDs.remove(button);
	}
	
	public String getButtonID(Button button) {
		return buttonIDs.get(button);
	}
	
	public Texture loadTexture() {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File("graphics/ui/map/map.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Texture texture = new Texture(image);
		
		return texture;
	}
}
