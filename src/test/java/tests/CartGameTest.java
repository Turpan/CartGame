package tests;

import java.util.Timer;
import java.util.TimerTask;

import OpenGLTests.GraphicsTestWindow;
import cartGame.core.CartGameCore;

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
	}
	
	public static void main(String[] args) {
		new CartGameTest();
	}
}
