package cartGame.ui.travel;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import amyGraphics.Texture;
import amyInterface.CentreLayout;
import amyInterface.Component;
import amyInterface.Container;
import amyInterface.Label;
import cartGame.io.ImageCache;
import cartGame.travel.cart.Party;
import cartGame.travel.cart.Traveller;

public class TravelHealthPlate extends Container {
	
	private static final int PARTYCOUNT = 4;
	private static final double HEALTHWIDTH = 92;
	
	private Party party;
	
	private Label[] names = new Label[PARTYCOUNT];
	private Component[] health = new Component[PARTYCOUNT];
	
	public TravelHealthPlate() {
		super();
		
		setBounds(93, 17, 202, 56);
		setVisible(true);
		
		Texture texture = ImageCache.getTexture("graphics/ui/travel/healthplate.png");
		addTexture(texture);
		setActiveTexture(texture);
		
		int x = 0;
		int y = 0;
		
		for (int i=0; i<names.length; i++) {			
			names[i] = createLabel();
			
			Container container = new Container();
			container.setBounds(8 + x, 6 + y, 92, 10);
			container.setLayout(new CentreLayout());
			container.addChild(names[i]);
			
			health[i] = new Component();
			health[i].setColour(Color.GREEN);
			health[i].setVisible(true);
			health[i].setBounds(8 + x, 19 + y, (int) HEALTHWIDTH, 6);
			
			addChild(container);
			addChild(health[i]);
			
			x += 96;
			
			if ((i+1) % 2 == 0) {
				x = 0;
				y += 22;
			}
		}
		
		update();
	}
	
	public void update() {
		String[] names = getPartyNames();
		int[] health = getPartyHealth();
		
		for (int i=0; i<PARTYCOUNT; i++) {
			this.names[i].setText(names[i]);
			
			double width = HEALTHWIDTH * ((double) health[i] / 100);
			
			width = (width < HEALTHWIDTH / 2) ? Math.floor(width) : Math.ceil(width);
			this.health[i].setBounds(this.health[i].getBounds().x, this.health[i].getBounds().y,
					(int) width, this.health[i].getBounds().height);
		}
	}
	
	private String[] getPartyNames() {
		List<Traveller> characters = new ArrayList<Traveller>();
		if (party != null) characters = party.getPartyMembers();
		String[] names = new String[PARTYCOUNT];
		
		int i = 0;
		for (Traveller traveller : characters) {
			names[i] = traveller.getName();
			
			i++;
			
			if (i == PARTYCOUNT) break;
		}
		
		for (int j=i; j<PARTYCOUNT; j++) {
			names[j] = "";
		}
		
		return names;
	}
	
	private int[] getPartyHealth() {
		List<Traveller> characters = new ArrayList<Traveller>();
		if (party != null) characters = party.getPartyMembers();
		int[] health = new int[PARTYCOUNT];
		
		int i = 0;
		for (Traveller traveller : characters) {
			health[i] = traveller.getHealth();
			
			i++;
			
			if (i == PARTYCOUNT) break;
		}
		
		for (int j=i; j<PARTYCOUNT; j++) {
			health[j] = 0;
		}
		
		return health;
	}

	public void setParty(Party party) {
		this.party = party;
	}
	
	public Label createLabel() {
		Label label = new Label();
		
		label.setFont(ImageCache.getFont("fonts/DSBehrensschrift.ttf"));
		//label.setFont(ImageCache.getFont("fonts/IrishPenny.ttf"));
		
		label.setFontSize(10);
		
		label.setColour(new Color(0, 0, 0, 0));
		
		label.setFontColour(Color.RED);
		
		label.setVisible(true);
		
		return label;
	}

}
