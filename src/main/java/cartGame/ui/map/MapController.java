package cartGame.ui.map;

import java.util.ArrayList;
import java.util.List;

import amyGLGraphics.IO.MouseEvent;
import amyGLGraphics.IO.MouseEventAction;
import amyInterface.Button;
import amyInterface.Component;
import amyInterface.InterfaceController;

public class MapController extends InterfaceController {
	private String travelRequest;
	
	private boolean backPressed;
	private boolean destinationExpected;
	
	private MapUI ui;
	
	private List<MapListener> listeners = new ArrayList<MapListener>();

	public MapController() {
		super();
		ui = new MapUI();
		setRoot(ui);
		setTickThreshold(10);
	}
	
	private void setTravelRequest(String travelRequest) {
		this.travelRequest = travelRequest;
	}
	
	public void addListener(MapListener listener) {
		listeners.add(listener);
	}
	
	public void destinationRequired() {
		destinationExpected = true;
	}
	
	public boolean isBackPressed() {
		boolean bool = backPressed;
		backPressed = false;
		
		if(bool == true) destinationExpected = false;
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
		
		if (clickSource == ui.getBackButton() && event.getMouseAction() == MouseEventAction.PRESS) {
			backPressed = true;
		}
		
		if (clickSource instanceof TownButton && event.getMouseAction() == MouseEventAction.PRESS) {
			if (destinationExpected) {
				Button button = (Button) clickSource;
				
				String townID = ui.getButtonID(button);
				setTravelRequest(townID);
				
				destinationExpected = false;
				
				for (MapListener listener : listeners) listener.destinationSelected();
			}
		}
		
		return clickSource;
	}
}
