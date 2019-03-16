package tests;

import java.util.Timer;
import java.util.TimerTask;

import OpenGLTests.GraphicsTestWindow;
import amyInterface.Component;
import cartGame.core.CartGameCore;
import cartGame.io.GraphicsQueue;
import movement.Room;

public class CartGameTest {
	protected GraphicsTestWindow window;
	
	protected CartGameCore core;
	
	protected Timer timer;
	
	public CartGameTest() {
		timer = new Timer();
		
		core = new CartGameCore();
		
		window = new GraphicsTestWindow(() -> {
			timer.scheduleAtFixedRate(new TimerTask() {

				@Override
				public void run() {
					tick();
				}
				
			}, 10, 10);
		});
		
		Thread graphicsThread = new Thread(window);
		graphicsThread.start();
		
		try {
			graphicsThread.join();
		} catch (InterruptedException e) {

		}

		//Remove the timer and close the program
		timer.cancel();
		System.exit(0);
	}
	
	protected void tick() {
		core.tick();
		
		Room room;
		while ((room = GraphicsQueue.getNextRoomToAdd()) != null) {
			window.addRoom(room);
		}
		
		while ((room = GraphicsQueue.getNextRoomToRemove()) != null) {
			window.removeRoom(room);
		}
		
		room = GraphicsQueue.getRoomToSwitch();
		if (room != null) {
			window.setActiveRoom(room);
		}
		
		Component scene;
		while((scene = GraphicsQueue.getNextSceneToAdd()) != null) {
			window.addScene(scene);
		}
		
		while ((scene = GraphicsQueue.getNextSceneToRemove()) != null) {
			window.removeScene(scene);
		}
		
		scene = GraphicsQueue.getSceneToSwitch();
		if (scene != null) {
			window.setActiveScene(scene);
		}
	}
	
	public static void main(String[] args) {
		new CartGameTest();
	}
}
