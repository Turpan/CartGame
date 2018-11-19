package cartGame.ui.town;

import java.awt.Color;

import amyGraphics.Texture;
import amyInterface.Container;
import amyInterface.Layout;
import amyInterface.VerticalLayout;

public class TownSideBar extends Container {
	
	TownInfo townInfo;
	
	public TownSideBar() {
		super();
		
		setColour(new Color(0, 0, 0, 0));
		setBounds(487, 15, 153, 345);
		
		Layout layout = new VerticalLayout(VerticalLayout.CENTREALIGN);
		setLayout(layout);
		
		setVisible(false);
		
		townInfo = new TownInfo();
		
		addChild(VerticalLayout.createFreeSpace(1, 7));
		addChild(townInfo);
	}
	
	public void setTownInfo(String name, String desc, Texture crest) {
		townInfo.setTownInfo(name, desc, crest);
	}
	
}
