package cartGame.ui.travel;

import amyGLGraphics.IO.MouseEvent;
import amyGLGraphics.IO.MouseEventAction;
import amyInterface.Component;
import amyInterface.InterfaceController;

public class TravelController extends InterfaceController {
	private TravelUI ui;
	
	private boolean stopStartPressed;

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

	@Override
	protected Component processInput(MouseEvent event) {
		Component clickSource = super.processInput(event);
		
		if (clickSource == null) {
			return clickSource;
		}
		
		if (clickSource == ui.getMapButton() && event.getMouseAction() == MouseEventAction.PRESS) {
			stopStartPressed = true;
		} else if (clickSource == ui.getStatsButton()) {
			
		} else if (clickSource == ui.getMenuButton()) {
			
		}
		
		return clickSource;
	}
}
