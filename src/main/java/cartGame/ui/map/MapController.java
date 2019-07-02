package cartGame.ui.map;

import amyGLGraphics.IO.MouseEvent;
import amyInterface.Button;
import amyInterface.Component;
import amyInterface.InterfaceController;

public class MapController extends InterfaceController {
	String travelRequest;
	
	boolean backPressed;
	
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
	
	public boolean isBackPressed() {
		boolean bool = backPressed;
		backPressed = false;
		return bool;
	}
	
	public String getTravelRequest() {
		String travelRequest = this.travelRequest;
		setTravelRequest(null);
		
		return travelRequest;
	}
	
	public MapUI getMapUI() {
		return ui;
	}
	
	@Override
	protected Component processInput(MouseEvent event) {
		Component clickSource = super.processInput(event);
		
		if (clickSource == null) {
			return clickSource;
		}
		
		if (clickSource == ui.getBackButton()) {
			backPressed = true;
		}
		
		if (clickSource instanceof TownButton) {
			Button button = (Button) clickSource;
			
			String townID = ui.getButtonID(button);
			setTravelRequest(townID);
		}
		
		return clickSource;
	}
}
