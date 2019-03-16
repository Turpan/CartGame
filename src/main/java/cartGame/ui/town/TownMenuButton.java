package cartGame.ui.town;

import java.awt.Color;

import amyGraphics.Texture;
import amyInterface.Button;
import amyInterface.CentreLayout;
import amyInterface.FontTexture;
import amyInterface.Label;
import cartGame.io.ImageCache;

public class TownMenuButton extends Button {
	
	public TownMenuButton() {
		setVisible(true);
		setInteractable(true);
		
		setButtonTextures(loadTexture(), loadPressedTexture(), loadHoverTexture());
		
		setLayout(new CentreLayout());
		
		Label label = createLabel(10);
		label.setText("Menu");
		addChild(label);
		
		setPreferredSize(62, 12);
	}
	
	public Texture loadTexture() {
		return ImageCache.getTexture("graphics/ui/town/topbutton.png");
	}
	
	public Texture loadPressedTexture() {
		return ImageCache.getTexture("graphics/ui/town/topbutton-pressed.png");
	}
	
	public Texture loadHoverTexture() {
		return ImageCache.getTexture("graphics/ui/town/topbutton-hover.png");
	}
	
	public Label createLabel(int size) {
		Label label = new Label();
		
		label.setFont(FontTexture.createFontTexture("fonts/DSBehrensschrift.ttf", true));
		
		label.setFontSize(size);
		
		label.setColour(new Color(0, 0, 0, 0));
		
		label.setFontColour(Color.white);
		
		label.setVisible(true);
		
		return label;
	}
}
