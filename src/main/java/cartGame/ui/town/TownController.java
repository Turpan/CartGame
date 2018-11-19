package cartGame.ui.town;

import amyGLGraphics.IO.MouseEvent;
import amyInterface.Component;
import amyInterface.InterfaceController;
import cartGame.travel.towns.Town;

public class TownController extends InterfaceController {
	
	TownUI ui;

	public TownController() {
		super();
		ui = new TownUI();
		setRoot(ui);
		setTickThreshold(10);
	}
	
	public void loadTownInfo(Town town) {
		ui.setTown(town);
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
