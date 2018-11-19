package cartGame.ui.travel;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import amyInterface.Container;
import amyInterface.HorizontalLayout;
import amyInterface.Layout;
import amyInterface.VerticalLayout;

public class TravelNameContainer extends Container {
	
	List<TravelNamePlate> namePlates = new ArrayList<TravelNamePlate>();

	public TravelNameContainer() {
		setBounds(1, 288, 498, 70);
		setColour(new Color(0, 0, 0, 0));
		setVisible(false);
		
		Layout layout = new VerticalLayout(VerticalLayout.CENTREALIGN);
		setLayout(layout);
		
		Container container1 = new Container();
		container1.setColour(new Color(0, 0, 0, 0));
		container1.setVisible(false);
		container1.setLayout(new HorizontalLayout(HorizontalLayout.TOPALIGN));
		container1.setPreferredSize(498, 33);
		
		Container container2 = new Container();
		container2.setColour(new Color(0, 0, 0, 0));
		container2.setVisible(false);
		container2.setLayout(new HorizontalLayout(HorizontalLayout.TOPALIGN));
		container2.setPreferredSize(498, 33);
		
		addChild(container1);
		addChild(VerticalLayout.createFreeSpace(0, 2));
		addChild(container2);
		addChild(VerticalLayout.createFreeSpace(0, 2));
		
		for (int i=0; i<3; i++) {
			TravelNamePlate namePlate = new TravelNamePlate();
			namePlates.add(namePlate);
			container1.addChild(HorizontalLayout.createFreeSpace(18, 0));
			container1.addChild(namePlate);
		}
		
		for (int i=0; i<3; i++) {
			TravelNamePlate namePlate = new TravelNamePlate();
			namePlates.add(namePlate);
			container2.addChild(HorizontalLayout.createFreeSpace(18, 0));
			container2.addChild(namePlate);
			
			if (i == 3-1) {
				namePlate.setName("Cart");
			}
		}
	}
	
	public List<TravelNamePlate> getNamePlates() {
		return namePlates;
	}
}
