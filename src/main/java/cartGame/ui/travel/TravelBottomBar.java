package cartGame.ui.travel;

import amyGraphics.Texture;
import amyInterface.Button;
import amyInterface.Component;
import amyInterface.Container;
import cartGame.io.ImageCache;

public class TravelBottomBar extends Container {
	
	private TravelHealthPlate healthPlate;
	private Component cartPlate;
	private TravelStopButton stopButton;
	
	public TravelBottomBar() {
		super();
		
		setBounds(0, 194, 480, 76);
		
		healthPlate = new TravelHealthPlate();
		
		Texture cartTexture = ImageCache.getTexture("graphics/ui/travel/carthealth.png");
		cartPlate = new Component();
		cartPlate.setBounds(298, 0, 19, 73);
		cartPlate.setVisible(true);
		cartPlate.addTexture(cartTexture);
		cartPlate.setActiveTexture(cartTexture);
		
		stopButton = new TravelStopButton();
		
		addChild(healthPlate);
		addChild(cartPlate);
		addChild(stopButton);
	}
	
	public Button getStopButton() {
		return stopButton;
	}

}
