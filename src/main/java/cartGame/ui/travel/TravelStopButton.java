package cartGame.ui.travel;

import java.awt.Color;

import amyGraphics.Texture;
import amyInterface.Button;
import amyInterface.CentreLayout;
import amyInterface.Label;
import cartGame.io.ImageCache;

public class TravelStopButton extends Button {

	public TravelStopButton() {
		super();
		
		Texture stopTexture = ImageCache.getTexture("graphics/ui/travel/stopbutton.png");
		Texture stopPressed = ImageCache.getTexture("graphics/ui/travel/stopbutton-pressed.png");
		Texture stopHover = ImageCache.getTexture("graphics/ui/travel/stopbutton-hover.png");
		
		Label label = createLabel(21);
		label.setText("Stop");
		
		setButtonTextures(stopTexture, stopPressed, stopHover);
		setBounds(320, 17, 58, 56);
		setVisible(true);
		setLayout(new CentreLayout());
		
		addChild(label);
	}
	
	public Label createLabel(int size) {
		Label label = new Label();
		
		label.setFont(ImageCache.getFont("fonts/DSBehrensschrift.ttf"));
		//label.setFont(ImageCache.getFont("fonts/IrishPenny.ttf"));
		
		label.setFontSize(size);
		
		label.setColour(new Color(0, 0, 0, 0));
		
		label.setFontColour(Color.red);
		
		label.setVisible(true);
		
		return label;
	}
	
}
