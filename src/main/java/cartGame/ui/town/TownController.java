package cartGame.ui.town;

import amyGLGraphics.IO.MouseEvent;
import amyGLGraphics.IO.MouseEventAction;
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
		
		if (clickSource == root.getMapButton() && event.getMouseAction() == MouseEventAction.RELEASE) {
			mapOpenPressed = true;
		} else if (clickSource == root.getMenuButton() && event.getMouseAction() == MouseEventAction.RELEASE) {
			
		} else if (clickSource == root.getShopButton() && event.getMouseAction() == MouseEventAction.RELEASE) {
			root.switchToShop();
		}
		
		return clickSource;
	}
}
