package cartGame.io;

import java.util.ArrayList;
import java.util.List;

import amyInterface.Component;
import movement.Room;

public class GraphicsQueue {
	private static List<Room> roomAddQueue = new ArrayList<Room>();
	private static List<Room> roomRemoveQueue = new ArrayList<Room>();
	private static Room roomSwitch;
	
	private static List<Component> sceneAddQueue = new ArrayList<Component>();
	private static List<Component> sceneRemoveQueue = new ArrayList<Component>();
	private static Component sceneSwitch;
	
	public static void addRoomToAddQueue(Room room) {
		roomAddQueue.add(room);
	}
	
	public static void removeRoomFromAddQueue(Room room) {
		roomAddQueue.remove(room);
	}
	
	public static void addRoomToRemoveQueue(Room room) {
		roomRemoveQueue.add(room);
	}
	
	public static void removeRoomFromRemoveQueue(Room room) {
		roomRemoveQueue.remove(room);
	}
	
	public static Room getNextRoomToAdd() {
		Room room = null;
		
		if (roomAddQueue.size() > 0) {
			room = roomAddQueue.get(0);
			roomAddQueue.remove(room);
		}
		
		return room;
	}
	
	public static Room getNextRoomToRemove() {
		Room room = null;
		
		if (roomRemoveQueue.size() > 0) {
			room = roomRemoveQueue.get(0);
			roomRemoveQueue.remove(room);
		}
		
		return room;
	}
	
	public static void setRoomToSwitch(Room room) {
		roomSwitch = room;
	}
	
	public static Room getRoomToSwitch() {
		Room room = roomSwitch;
		roomSwitch = null;
		return room;
	}
	
	public static void addSceneToAddQueue(Component scene) {
		sceneAddQueue.add(scene);
	}
	
	public static void removeSceneFromAddQueue(Component scene) {
		sceneAddQueue.remove(scene);
	}
	
	public static void addSceneToRemoveQueue(Component scene) {
		sceneRemoveQueue.add(scene);
	}
	
	public static void removeSceneFromRemoveQueue(Component scene) {
		sceneRemoveQueue.remove(scene);
	}
	
	public static Component getNextSceneToAdd() {
		Component scene = null;
		
		if (sceneAddQueue.size() > 0) {
			scene = sceneAddQueue.get(0);
			sceneAddQueue.remove(scene);
		}
		
		return scene;
	}
	
	public static Component getNextSceneToRemove() {
		Component scene = null;
		
		if (sceneRemoveQueue.size() > 0) {
			scene = sceneRemoveQueue.get(0);
			sceneRemoveQueue.remove(scene);
		}
		
		return scene;
	}
	
	public static void setSceneToSwitch(Component scene) {
		sceneSwitch = scene;
	}
	
	public static Component getSceneToSwitch() {
		Component scene = sceneSwitch;
		sceneSwitch = null;
		return scene;
	}
}
