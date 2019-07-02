package cartGame.ui.travel;

import amyGLGraphics.IO.MouseEvent;
import amyGLGraphics.IO.MouseEventAction;
import amyInterface.Component;
import amyInterface.InterfaceController;

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

	@Override
	protected Component processInput(MouseEvent event) {
		Component clickSource = super.processInput(event);
		
		if (clickSource == null) {
			return clickSource;
		}
		
		if (clickSource == ui.getMapButton() && event.getMouseAction() == MouseEventAction.PRESS) {
			mapOpenPressed = true;
		} else if (clickSource == ui.getMenuButton()) {
			
		} else if (clickSource == ui.getStopButton()) {
			stopStartPressed = true;
		}
		
		return clickSource;
	}
}
