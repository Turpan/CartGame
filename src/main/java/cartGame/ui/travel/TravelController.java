package cartGame.ui.travel;

import amyGLGraphics.IO.MouseEvent;
import amyInterface.Component;
import amyInterface.InterfaceController;

public class TravelController extends InterfaceController {
	TravelUI ui;

	public TravelController() {
		super();
		ui = new TravelUI();
		setRoot(ui);
		setTickThreshold(10);
	}

	@Override
	protected Component processInput(MouseEvent event) {
		Component clickSource = super.processInput(event);
		
		if (clickSource == null) {
			return clickSource;
		}
		
		if (clickSource == ui.getMapButton()) {
			
		} else if (clickSource == ui.getStatsButton()) {
			
		} else if (clickSource == ui.getMenuButton()) {
			
		}
		
		return clickSource;
	}
}
