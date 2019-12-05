package cartGame.ui.travel;

import amyGLGraphics.IO.MouseEvent;
import amyGLGraphics.IO.MouseEventAction;
import amyInterface.Component;
import amyInterface.InterfaceController;
import cartGame.travel.cart.Inventory;
import cartGame.travel.cart.Party;

public class TravelController extends InterfaceController {
	private TravelRoot ui;
	
	private boolean stopStartPressed;
	private boolean mapOpenPressed;

	public TravelController() {
		super();
		ui = new TravelRoot();
		setRoot(ui);
		setTickThreshold(10);
	}
	
	public boolean isStopStartPressed() {
		boolean value = stopStartPressed;
		stopStartPressed = false;
		
		return value;
	}
	
	public boolean isMapOpenPressed() {
		boolean value = mapOpenPressed;
		mapOpenPressed = false;
		
		return value;
	}
	
	public void setParty(Party party) {
		ui.getTopBar().getResourceBar().setInventory(party.getInventory());
		ui.getBottomBar().getHealthPlate().setParty(party);
	}
	
	public void updateTime(int hour, int minute) {
		ui.getTopBar().getClock().update(hour, minute);
	}
	
	@Override
	public void tick() {
		super.tick();
		
		ui.getTopBar().getResourceBar().update();
		ui.getBottomBar().getHealthPlate().update();
	}

	@Override
	protected Component processInput(MouseEvent event) {
		Component clickSource = super.processInput(event);
		
		if (clickSource == null) {
			return clickSource;
		}
		
		if (clickSource == ui.getMapButton() && event.getMouseAction() == MouseEventAction.PRESS) {
			mapOpenPressed = true;
		} else if (clickSource == ui.getMenuButton()) {
			
		} else if (clickSource == ui.getStopButton() && event.getMouseAction() == MouseEventAction.PRESS) {
			stopStartPressed = true;
		}
		
		return clickSource;
	}
}
