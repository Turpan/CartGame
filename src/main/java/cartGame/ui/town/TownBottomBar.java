package cartGame.ui.town;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import amyGraphics.Texture;
import amyInterface.Button;
import amyInterface.CentreLayout;
import amyInterface.Container;
import amyInterface.Label;
import cartGame.io.ImageCache;

public class TownBottomBar extends Container {
	
	private Button shopButton;
	private Button innButton;
	private Button campButton;
	private Button questButton;
	private Button leaveButton;
	
	public TownBottomBar() {
		super();
		
		setBounds(0, 211, 480,59);
		
		String[] names = new String[] {"Market", "Inn", "Camp", "Quest", "Leave"};
		Color[] colors = new Color[] {Color.RED, Color.RED, Color.RED, Color.RED, Color.RED};
		final int width = 71;
		List<Button> buttons = new ArrayList<Button>();
		
		for (int i=0; i<5; i++) {
			buttons.add(createTownButton(names[i], colors[i], i * width));
		}
		
		shopButton = buttons.get(0);
		innButton = buttons.get(1);
		campButton = buttons.get(2);
		questButton = buttons.get(3);
		leaveButton = buttons.get(4);
		
		addChild(shopButton);
		addChild(innButton);
		addChild(campButton);
		addChild(questButton);
		addChild(leaveButton);
	}
	
	private Button createTownButton(String text, Color color, int x) {
		Texture buttonTexture = ImageCache.getTexture("graphics/ui/town/townbutton.png");
		Texture buttonPressed = ImageCache.getTexture("graphics/ui/town/townbutton-pressed.png");
		Texture buttonHover = ImageCache.getTexture("graphics/ui/town/townbutton-hover.png");
		
		Button button = new Button(buttonTexture, buttonPressed, buttonHover);
		button.setVisible(true);
		button.setLayout(new CentreLayout());
		button.setBounds(64 + x, 18, 68, 38);
		
		Label label = createLabel(color);
		label.setText(text);
		
		button.addChild(label);
		
		return button;
	}
	
	public Label createLabel(Color color) {
		Label label = new Label();
		
		label.setFont(ImageCache.getFont("fonts/DSBehrensschrift.ttf"));
		//label.setFont(ImageCache.getFont("fonts/IrishPenny.ttf"));
		
		label.setFontSize(21);
		
		label.setColour(new Color(0, 0, 0, 0));
		
		label.setFontColour(color);
		
		label.setVisible(true);
		
		return label;
	}
	
	public Button getShopButton() {
		return shopButton;
	}
}
