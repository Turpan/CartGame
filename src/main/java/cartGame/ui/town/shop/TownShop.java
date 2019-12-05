package cartGame.ui.town.shop;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import amyGraphics.Texture;
import amyInterface.Button;
import amyInterface.CentreLayout;
import amyInterface.Label;
import cartGame.io.ImageCache;
import cartGame.travel.cart.Inventory;
import cartGame.travel.towns.Shop;
import cartGame.ui.town.TownSky;

public class TownShop extends TownSky {
	
	private ShopInfo shopInfo;
	private ShopBarter shopBarter;
	private ResourceBar resourceBar;
	private ShopPanel shopPanel;
	private Button backButton;
	
	public TownShop() {
		super();
		
		setBounds(0, 0, 480, 270);
		
		Texture background = ImageCache.getTexture("graphics/ui/town/shop/shop.png");
		addTexture(background);
		setActiveTexture(background);
		
		townBackground.addTexture(background);
		townBackground.setActiveTexture(background);
		
		loadSky();
		
		createComponents();
	}
	
	private void createComponents() {
		shopInfo = new ShopInfo();
		shopBarter = new ShopBarter();
		resourceBar = new ResourceBar();
		shopPanel = new ShopPanel();
		
		Texture buttonTexture = ImageCache.getTexture("graphics/ui/town/townbutton.png");
		Texture buttonPressed = ImageCache.getTexture("graphics/ui/town/townbutton-pressed.png");
		Texture buttonHover = ImageCache.getTexture("graphics/ui/town/townbutton-hover.png");
		
		backButton = new Button(buttonTexture, buttonPressed, buttonHover);
		backButton.setVisible(true);
		backButton.setLayout(new CentreLayout());
		backButton.setBounds(409, 229, 68, 38);
		
		Label label = createLabel();
		label.setText("Back");
		
		backButton.addChild(label);
		
		addChild(shopInfo);
		addChild(shopBarter);
		addChild(resourceBar);
		addChild(shopPanel);
		addChild(backButton);
	}
	
	public Label createLabel() {
		Label label = new Label();
		
		label.setFont(ImageCache.getFont("fonts/DSBehrensschrift.ttf"));
		//label.setFont(ImageCache.getFont("fonts/IrishPenny.ttf"));
		
		label.setFontSize(21);
		
		label.setColour(new Color(0, 0, 0, 0));
		
		label.setFontColour(Color.RED);
		
		label.setVisible(true);
		
		return label;
	}
	
	public Button getBackButton() {
		return backButton;
	}

	public void setShop(Shop shop) {
		shopPanel.setShop(shop);
	}
	
	public ShopPanel getShopPanel() {
		return shopPanel;
	}
	
	public ResourceBar getResourceBar() {
		return resourceBar;
	}

	public void changeAmount(int amount, int item) {
		shopPanel.changeAmount(amount, item);
	}
}
