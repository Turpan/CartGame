package cartGame.ui.town;

import java.awt.Color;

import amyGraphics.Texture;
import amyInterface.CentreLayout;
import amyInterface.Container;
import amyInterface.Label;
import cartGame.io.ImageCache;
import cartGame.travel.towns.Town;

public class TownTitle extends Container {
	
	private Label townName;

	public TownTitle() {
		super();
		
		Texture texture = ImageCache.getTexture("graphics/ui/town/titlebar.png");
		addTexture(texture);
		setActiveTexture(texture);
		
		setBounds(203, 0, 228, 30);
		
		setVisible(true);
		
		Container panel = new Container();
		panel.setBounds(4, 7, 220, 19);
		CentreLayout layout = new CentreLayout();
		panel.setLayout(layout);
		
		townName = createLabel(17);
		panel.addChild(townName);
		
		addChild(panel);
	}
	
	public void setTown(Town town) {
		townName.setText(town.getName());
	}
	
	public Label createLabel(int size) {
		Label label = new Label();
		
		label.setFont(ImageCache.getFont("fonts/DSBehrensschrift.ttf"));
		
		label.setFontSize(size);
		
		label.setColour(new Color(0, 0, 0, 0));
		
		label.setFontColour(Color.red);
		
		label.setVisible(true);
		
		return label;
	}
	
}
