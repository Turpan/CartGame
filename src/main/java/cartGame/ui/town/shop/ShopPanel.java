package cartGame.ui.town.shop;

import java.awt.Color;
import java.awt.Rectangle;

import amyGraphics.Texture;
import amyInterface.Button;
import amyInterface.CentreLayout;
import amyInterface.Container;
import amyInterface.HorizontalLayout;
import amyInterface.Label;
import cartGame.io.ImageCache;
import cartGame.resources.Item;
import cartGame.travel.cart.Inventory;
import cartGame.travel.towns.Shop;

public class ShopPanel extends Container {
	
	private static final Item[] items = new Item[]{
			Inventory.food, Inventory.scrap, Inventory.medicine
	};
	
	private ResourcePanel[] panels = new ResourcePanel[3];
	private Button[] addButtons = new Button[3];
	private Button[] removeButtons = new Button[3];
	private Button[] confirmButtons = new Button[3];
	private Label[] amountDisplays = new Label[3];
	private Label[] priceDisplays = new Label[3];
	
	private int[] purchaseAmounts = new int[3];
	
	private Shop shop;
	private Inventory inventory;
	
	public ShopPanel() {
		super();
		
		setBounds(133, 68, 214, 122);
		
		createComponents();
	}
	
	private void createComponents() {
		Texture addTexture = ImageCache.getTexture("graphics/ui/town/shop/addbutton.png");
		Texture addPress = ImageCache.getTexture("graphics/ui/town/shop/addbutton.png");
		Texture addHover = ImageCache.getTexture("graphics/ui/town/shop/addbutton.png");
		
		Texture removeTexture = ImageCache.getTexture("graphics/ui/town/shop/removebutton.png");
		Texture removePress = ImageCache.getTexture("graphics/ui/town/shop/removebutton.png");
		Texture removeHover = ImageCache.getTexture("graphics/ui/town/shop/removebutton.png");
		
		Texture confirmTexture = ImageCache.getTexture("graphics/ui/town/shop/purchasebutton.png");
		Texture confirmPress = ImageCache.getTexture("graphics/ui/town/shop/purchasebutton.png");
		Texture confirmHover = ImageCache.getTexture("graphics/ui/town/shop/purchasebutton.png");
		
		Texture amountTexture = ImageCache.getTexture("graphics/ui/town/shop/purchaseamount.png");
		
		String[] itemNames = new String[] {"Food", "Scrap", "Medicine"};
		
		int x = 0;
		
		for (int i=0; i<3; i++) {
			panels[i] = new ResourcePanel(x);
			addButtons[i] = createButton(new Rectangle(0 + x, 79, 15, 16), addTexture, addPress, addHover, null);
			removeButtons[i] = createButton(new Rectangle(53 + x, 79, 15, 16), removeTexture, removePress, removeHover, null);
			confirmButtons[i] = createButton(new Rectangle(0 + x, 100, 68, 22), confirmTexture, confirmPress, confirmHover, "Confirm");
			amountDisplays[i] = createLabel(15);
			amountDisplays[i].setText("0");
			priceDisplays[i] = createLabel(11);
			priceDisplays[i].setText("N/A");
			
			Container amountDisplay = new Container();
			amountDisplay.addTexture(amountTexture);
			amountDisplay.setActiveTexture(amountTexture);
			amountDisplay.setVisible(true);
			amountDisplay.setBounds(15 + x, 76, 38, 22);
			amountDisplay.setLayout(new CentreLayout());
			amountDisplay.addChild(amountDisplays[i]);
			
			Label name = createLabel(11);
			name.setText(itemNames[i]);
			
			Container nameDisplay = new Container();
			nameDisplay.setBounds(4 + x, 45, 60, 12);
			nameDisplay.setLayout(new CentreLayout());
			nameDisplay.addChild(name);
			
			Container priceDisplay = new Container();
			priceDisplay.setBounds(16 + x, 58, 48, 12);
			priceDisplay.setLayout(new HorizontalLayout(HorizontalLayout.CENTREALIGN));
			priceDisplay.addChild(HorizontalLayout.createGlue());
			priceDisplay.addChild(priceDisplays[i]);
			priceDisplay.addChild(HorizontalLayout.createFreeSpace(2, 0));
			
			addChild(panels[i]);
			addChild(addButtons[i]);
			addChild(removeButtons[i]);
			addChild(confirmButtons[i]);
			addChild(nameDisplay);
			addChild(priceDisplay);
			addChild(amountDisplay);
			
			x += 73;
		}
	}
	
	private Button createButton(Rectangle bounds, Texture texture, Texture press, Texture hover, String text) {
		Button button = new Button(texture, press, hover);
		button.setBounds(bounds);
		button.setVisible(true);
		
		if (text != null) {
			button.setLayout(new CentreLayout());
			Label label = createLabel(15);
			label.setText(text);
			button.addChild(label);
		}
		
		return button;
	}
	
	public Label createLabel(int size) {
		Label label = new Label();
		
		label.setFont(ImageCache.getFont("fonts/DSBehrensschrift.ttf"));
		//label.setFont(ImageCache.getFont("fonts/IrishPenny.ttf"));
		
		label.setFontSize(size);
		
		label.setColour(new Color(0, 0, 0, 0));
		
		label.setFontColour(Color.RED);
		
		label.setVisible(true);
		
		return label;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
		
		Item food = shop.getItem("food");
		Item scrap = shop.getItem("scrap");
		Item medicine = shop.getItem("medicine");
		
		String foodPrice = food != null ? String.valueOf(shop.getItemPrice(food)) : "N/A";
		String scrapPrice = food != null ? String.valueOf(shop.getItemPrice(scrap)) : "N/A";
		String medicinePrice = food != null ? String.valueOf(shop.getItemPrice(medicine)) : "N/A";
		
		priceDisplays[0].setText(foodPrice);
		priceDisplays[1].setText(scrapPrice);
		priceDisplays[2].setText(medicinePrice);
	}
	
	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}
	
	private int getCurrentMoney() {
		if (inventory == null) return 0;
		return inventory.getMoney();
	}
	
	public void changeAmount(int change, int item) {
		Item toBuy = items[item];
		Item toCheck = shop.getItem(toBuy.getID());
		
		if (toCheck == null) return;
		
		int playerMoney = getCurrentMoney();
		int totalCost = (purchaseAmounts[item] + change) * shop.getItemPrice(toCheck);
		
		if (playerMoney < totalCost) return;
		
		purchaseAmounts[item] += change;
		
		if (purchaseAmounts[item] < 0) purchaseAmounts[item] = 0;
		
		setAmountLabel(item);
	}
	
	public void purchase(int item) {
		if (item < 0 || item > items.length) {
			return;
		}
		
		Item toBuy = items[item];
		Item toCheck = shop.getItem(toBuy.getID());
		
		int amount = purchaseAmounts[item];
		
		if (toCheck == null) {
			amount = 0;
		}
		
		if (inventory != null) {
			inventory.addItem(toBuy, amount);
			inventory.addMoney(-(amount * shop.getItemPrice(toCheck)));
		}
		
		purchaseAmounts[item] = 0;
		setAmountLabel(item);
	}
	
	public Button[] getAddButtons() {
		return addButtons;
	}
	
	public Button[] getRemoveButtons() {
		return removeButtons;
	}
	
	public Button[] getConfirmButtons() {
		return confirmButtons;
	}
	
	public void reset() {
		for (int i=0; i<3; i++) {
			purchaseAmounts[i] = 0;
			setAmountLabel(i);
		}
	}
	
	private void setAmountLabel(int i) {
		amountDisplays[i].setText(String.valueOf(purchaseAmounts[i]));
	}
}
