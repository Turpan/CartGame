package cartGame.ui.town;

import java.awt.Color;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import amyGraphics.Texture;
import amyInterface.CentreLayout;
import amyInterface.Component;
import amyInterface.Container;
import amyInterface.FontTexture;
import amyInterface.Label;
import amyInterface.Layout;

public class TownInfo extends Container {
	
	private Label townName;
	private Label townDesc;
	private Component townCrest;
	
	public TownInfo() {
		setVisible(true);
		
		setPreferredSize(153, 111);
		
		Texture texture = loadTexture();
		addTexture(texture);
		setActiveTexture(texture);
		
		Layout layout = new CentreLayout();
		
		townName = createLabel(14);
		townName.setText("Town");
		townDesc = createLabel(7);
		townDesc.setText("Description");
		townCrest = new Component();
		townCrest.setColour(new Color(0, 0, 0, 0));
		townCrest.setVisible(true);
		townCrest.setBounds(0, 36, 152, 74);
		
		Container container = new Container();
		container.setBounds(0, 1, 152, 22);
		container.setLayout(layout);
		container.addChild(townName);
		
		Container container2 = new Container();
		container2.setBounds(0, 24, 152, 11);
		container2.setLayout(layout);
		container2.addChild(townDesc);
		
		addChild(container);
		addChild(container2);
		addChild(townCrest);
	}
	
	public Texture loadTexture() {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File("graphics/ui/town/towninfo.png"));
		} catch (IOException e) {
			image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		}
		
		Texture texture = new Texture(image);
		
		return texture;
	}
	
	public void setTownInfo(String name, String desc, Texture crest) {
		townName.setText(name);
		townDesc.setText(desc);
		townCrest.removeTexture(townCrest.getActiveTexture());
		townCrest.addTexture(crest);
		townCrest.setActiveTexture(crest);
	}
	
	public Label createLabel(int size) {
		Label label = new Label();
		
		label.setFont(FontTexture.createFontTexture("fonts/IrishPenny.ttf", true));
		
		label.setFontSize(size);
		
		label.setColour(new Color(0, 0, 0, 0));
		
		label.setFontColour(Color.white);
		
		label.setVisible(true);
		
		return label;
	}
}
