package cartGame.ui.travel;

import amyGLGraphics.IO.MouseEvent;
import amyGLGraphics.IO.MouseEventAction;
import amyInterface.Component;
import amyInterface.InterfaceController;

public class TravelController extends InterfaceController {
	private TravelUI ui;
	
	private boolean stopStartPressed;
	private boolean mapOpenPressed;

	public TravelController() {
		super();
		ui = new TravelUI();
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

	@Override
	protected Component processInput(MouseEvent event) {
		Component clickSource = super.processInput(event);
		
		if (clickSource == null) {
			return clickSource;
		}
		
		if (clickSource == ui.getMapButton() && event.getMouseAction() == MouseEventAction.PRESS) {
			mapOpenPressed = true;
		} else if (clickSource == ui.getStatsButton()) {
			
		} else if (clickSource == ui.getMenuButton()) {
			stopStartPressed = true;
		}
		
		return clickSource;
	}
}
