package cartGame.ui.travel;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import amyGraphics.Texture;
import amyInterface.Component;
import amyInterface.Container;
import amyInterface.FontTexture;
import amyInterface.Label;
import amyInterface.Layout;
import amyInterface.VerticalLayout;

public class TravelNamePlate extends Container {
	
	float health;
	
	String name;
	
	TravelHealthBar healthBar;
	Label nameLabel;
	
	public TravelNamePlate() {
		setPreferredSize(148, 33);
		
		setColour(new Color(0, 0, 0, 0));
		setVisible(false);
		
		Layout layout = new VerticalLayout(VerticalLayout.CENTREALIGN);
		setLayout(layout);
		
		healthBar = new TravelHealthBar();
		
		nameLabel = createNameLabel(14);
		nameLabel.setText("Name");
		
		addChild(healthBar);
		addChild(VerticalLayout.createFreeSpace(0, 1));
		addChild(nameLabel);
	}

	public float getHealth() {
		return health;
	}

	public void setHealth(float health) {
		this.health = health;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		
		nameLabel.setText(name);
	}

	public Label createNameLabel(int size) {
		Label label = new Label();
		
		label.setFont(FontTexture.createFontTexture("fonts/IrishPenny.ttf", true));
		
		label.setFontSize(size);
		
		label.setColour(new Color(0, 0, 0, 0));
		
		label.setFontColour(Color.white);
		
		label.setVisible(true);
		
		return label;
	}
}
