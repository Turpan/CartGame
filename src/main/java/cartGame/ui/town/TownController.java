package cartGame.ui.town;

import amyGLGraphics.IO.MouseEvent;
import amyInterface.Component;
import amyInterface.InterfaceController;
import cartGame.travel.towns.Town;

public class TownController extends InterfaceController {
	
	private TownRoot root;
	
	private boolean mapOpenPressed;

	public TownController() {
		super();
		root = new TownRoot();
		setRoot(root);
		setTickThreshold(10);
	}
	
	public TownRoot getInterface() {
		return root;
	}
	
	public void setHour(int hour) {
		root.setHour(hour);
	}
	
	public void loadTownInfo(Town town) {
		root.setTown(town);
	}
	
	public boolean isMapOpenPressed() {
		boolean value = mapOpenPressed;
		mapOpenPressed = false;
		
		return value;
	}

	@Override
	protected Component processInput(MouseEvent event) {
		Component clickSource = super.processInput(event);
		
		if (clickSource == null) {
			return clickSource;
		}
		
		if (clickSource == root.getMapButton()) {
			mapOpenPressed = true;
		} else if (clickSource == root.getMenuButton()) {
			
		}
		
		return clickSource;
	}
}
