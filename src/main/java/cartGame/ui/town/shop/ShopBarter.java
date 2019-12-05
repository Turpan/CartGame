package cartGame.ui.town.shop;

import java.awt.Color;

import amyGraphics.Texture;
import amyInterface.Button;
import amyInterface.CentreLayout;
import amyInterface.Container;
import amyInterface.Label;
import cartGame.io.ImageCache;

public class ShopBarter extends Container {
	
	private Container barterInfo;
	private Button accept;
	private Button refuse;
	
	public ShopBarter() {
		super();
		
		setBounds(356, 68, 114, 122);
		
		Texture barterTexture = ImageCache.getTexture("graphics/ui/town/shop/barterinfo.png");
		barterInfo = new Container();
		barterInfo.addTexture(barterTexture);
		barterInfo.setActiveTexture(barterTexture);
		barterInfo.setLayout(new CentreLayout());
		barterInfo.setBounds(0, 0, 114, 98);
		barterInfo.setVisible(true);
		
		Texture buttonTexture = ImageCache.getTexture("graphics/ui/town/shop/barterbutton.png");
		Texture buttonPressed = ImageCache.getTexture("graphics/ui/town/shop/barterbutton.png");
		Texture buttonHover = ImageCache.getTexture("graphics/ui/town/shop/barterbutton.png");
		
		accept = new Button();
		accept.setButtonTextures(buttonTexture, buttonPressed, buttonHover);
		accept.setLayout(new CentreLayout());
		accept.setBounds(2, 100, 54, 22);
		accept.setVisible(true);
		
		refuse = new Button();
		refuse.setButtonTextures(buttonTexture, buttonPressed, buttonHover);
		refuse.setLayout(new CentreLayout());
		refuse.setBounds(58, 100, 54, 22);
		refuse.setVisible(true);
		
		Label acceptLabel = createLabel();
		acceptLabel.setText("Accept");
		
		Label refuseLabel = createLabel();
		refuseLabel.setText("Refuse");
		
		accept.addChild(acceptLabel);
		refuse.addChild(refuseLabel);
		
		addChild(barterInfo);
		addChild(accept);
		addChild(refuse);
	}
	
	public Label createLabel() {
		Label label = new Label();
		
		label.setFont(ImageCache.getFont("fonts/DSBehrensschrift.ttf"));
		//label.setFont(ImageCache.getFont("fonts/IrishPenny.ttf"));
		
		label.setFontSize(15);
		
		label.setColour(new Color(0, 0, 0, 0));
		
		label.setFontColour(Color.RED);
		
		label.setVisible(true);
		
		return label;
	}
}
