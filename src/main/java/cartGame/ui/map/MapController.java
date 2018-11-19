package cartGame.ui.map;

import amyGLGraphics.IO.MouseEvent;
import amyInterface.Button;
import amyInterface.Component;
import amyInterface.InterfaceController;
import cartGame.ui.town.TownUI;

public class MapController extends InterfaceController {
	String travelRequest;
	
	MapUI ui;

	public MapController() {
		super();
		ui = new MapUI();
		setRoot(ui);
		setTickThreshold(10);
	}
	
	private void setTravelRequest(String travelRequest) {
		this.travelRequest = travelRequest;
	}
	
	public String getTravelRequest() {
		String travelRequest = this.travelRequest;
		setTravelRequest(null);
		
		return travelRequest;
	}
	
	@Override
	protected Component processInput(MouseEvent event) {
		Component clickSource = super.processInput(event);
		
		if (clickSource == null) {
			return clickSource;
		}
		
		if (clickSource instanceof Button) {
			Button button = (Button) clickSource;
			
			String townID = ui.getButtonID(button);
		}
		
		return clickSource;
	}
}
