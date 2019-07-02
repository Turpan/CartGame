package tests;

import java.util.ArrayList;
import java.util.List;

import OpenGLTests.GraphicsTestEnvironment;
import amyGLGraphics.IO.MouseEvent;
import amyGLGraphics.IO.MouseEventAction;
import amyInterface.Button;
import amyInterface.Component;
import amyInterface.InterfaceController;
import cartGame.core.TravelMain;
import cartGame.ui.map.MapController;
import cartGame.ui.travel.TravelController;
import movement.Room;

public class TyroneTestEnvironment extends GraphicsTestEnvironment{
	
	private Room room;
	
	int tickCount = 0;
	
	public TyroneTestEnvironment() {
		super();
	}
	
	@Override
	protected List<Component> getScenes() {
		List<Component> scenes = new ArrayList<Component>();
		
		return scenes;
	}
	
	@Override
	protected List<Room> getRooms() {
		List<Room> rooms = new ArrayList<Room>();
		
		room = new TestArena();
		rooms.add(room);

		return rooms;
	}
	
	public static void main(String args[]) {
		new TyroneTestEnvironment();
	}
	
	@Override
	public void tick() {
		super.tick();
		
		room.tick();
		
		tickCount++;
	}
	
	protected void buttonClick(Button button, MouseEvent event) {
		if (event.getMouseAction() == MouseEventAction.PRESS) {
			button.setPressed(true);
		} else if (event.getMouseAction() == MouseEventAction.RELEASE) {
			button.setPressed(false);
		}
	}
}
