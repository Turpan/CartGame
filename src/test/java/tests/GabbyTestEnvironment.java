package tests;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import OpenGLTests.CommunismRoom;
import OpenGLTests.GraphicsTestEnvironment;
import amyGLGraphics.IO.MouseEvent;
import amyGLGraphics.IO.MouseEventAction;
import amyInterface.Button;
import amyInterface.Component;
import amyInterface.InterfaceController;
import cartGame.core.TravelMain;
import cartGame.io.GraphicsQueue;
import cartGame.ui.map.MapController;
import cartGame.ui.town.TownController;
import cartGame.ui.travel.TravelController;
import movement.Room;
import tests.towns.ShepardTown;

public class GabbyTestEnvironment extends GraphicsTestEnvironment{
	
	//private TravelMain travel;
	
	//InterfaceController controller1;
	//InterfaceController controller2;
	TownController town;
	
	//TravelGraphic room1;
	//CommunismRoom room2;
	
	int tickCount = 0;
	
	public GabbyTestEnvironment() {
		super();
	}
	
	@Override
	protected List<Component> getScenes() {
		//controller1 = new TravelController();
		//controller2 = new MapController();
		
		List<Component> scenes = new ArrayList<Component>();
		//scenes.add(controller1.getRoot());
		//scenes.add(controller2.getRoot());
		//scenes.add(travel.getUI().getRoot());
		
		town = new TownController();
		town.loadTownInfo(new ShepardTown());
		town.setHour(12);
		scenes.add(town.getRoot());
		
		return scenes;
	}
	
	@Override
	protected List<Room> getRooms() {
		List<Room> rooms = new ArrayList<Room>();
		
		//travel = new TravelMain();
		//rooms.add(travel.getGraphic());
		
		//Room room1 = new TestDeepForest();
		//rooms.add(room1);
		
		/*try {
			room2 = new CommunismRoom();
			rooms.add(room2);
		} catch (MalformedEntityException e) {
			System.err.println("There was a woopsies");
		}*/
		
		return rooms;
	}
	
	public static void main(String args[]) {
		new GabbyTestEnvironment();
	}
	
	@Override
	public void tick() {
		super.tick();
		
		town.tick();
		//travel.tick();
	}
	
	protected void buttonClick(Button button, MouseEvent event) {
		if (event.getMouseAction() == MouseEventAction.PRESS) {
			button.setPressed(true);
		} else if (event.getMouseAction() == MouseEventAction.RELEASE) {
			button.setPressed(false);
		}
	}
}
