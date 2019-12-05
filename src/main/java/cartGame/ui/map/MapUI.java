package cartGame.ui.map;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import amyGraphics.Texture;
import amyInterface.Button;
import amyInterface.CentreLayout;
import amyInterface.Container;
import amyInterface.Label;
import cartGame.io.ImageCache;
import cartGame.travel.cart.Wagon;
import cartGame.travel.towns.Town;
import cartGame.travel.towns.WorldMap;

public class MapUI extends Container {
	
	private List<TownButton> buttons = new ArrayList<TownButton>();
	
	private WorldMap map;
	
	private Button backButton;
	
	public MapUI() {
		Texture texture = ImageCache.getTexture("graphics/ui/map/map.png");
		addTexture(texture);
		setActiveTexture(texture);
		
		setBounds(0, 0, 480, 270);
		
		setVisible(true);
		
		Label backLabel = createLabel();
		backLabel.setText("Back");
		
		Texture backbutton = ImageCache.getTexture("graphics/ui/map/backbutton.png");
		Texture backpressed = ImageCache.getTexture("graphics/ui/map/backbutton-pressed.png");
		Texture backhover = ImageCache.getTexture("graphics/ui/map/backbutton-hover.png");
		
		backButton = new Button(backbutton, backpressed, backhover);
		backButton.setVisible(true);
		backButton.setBounds(409, 235, 68, 32);
		backButton.setLayout(new CentreLayout());
		backButton.addChild(backLabel);
		
		addChild(backButton);
	}
	
	public void setMap(WorldMap map) {
		this.map = map;
		
		populateMap();
	}
	
	public void populateMap() {
		if (map == null) return;
		
		for (Town town : map.getTowns()) {
			addTownButton(town);
		}
	}
	
	public void updateButtons(Wagon wagon) {
		if (map == null) return;
		
		if (wagon.isTravelling()) {
			return;
		}
		
		Town presentTown = wagon.getCurrentTown();
		
		for (TownButton button : buttons) {
			boolean present = false;
			if (button.getTown().equals(presentTown)) {
				present = true;
			}
			
			button.setPresent(present);
		}
		
		backButton.setPressed(false);
		backButton.updateButtonTexture();
	}
	
	public void addTownButton(Town town) {
		TownButton button = new TownButton(town);
		
		addChild(button);
		buttons.add(button);
	}
	
	public void removeTownButton(Button button) {
		removeChild(button);
		buttons.remove(button);
	}
	
	public String getButtonID(Button button) {
		for (TownButton tbutton : buttons) {
			if (button.equals(tbutton)) {
				return tbutton.getTown().getID();
			}
		}
		
		return null;
	}
	
	public Button getBackButton() {
		return backButton;
	}
	
	public Label createLabel() {
		Label label = new Label();
		
		label.setFont(ImageCache.getFont("fonts/DSBehrensschrift.ttf"));
		//label.setFont(ImageCache.getFont("fonts/IrishPenny.ttf"));
		
		label.setFontSize(17);
		
		label.setColour(new Color(0, 0, 0, 0));
		
		label.setFontColour(Color.RED);
		
		label.setVisible(true);
		
		return label;
	}
}
