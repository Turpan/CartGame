package cartGame.travel.graphics;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import amyGraphics.Texture;
import amyGraphics.TexturePosition;
import cartGame.travel.graphics.Backdrop.Direction;
import movement.Room;

public class TravelGraphic extends Room {
	
	static final private int BOUNCEINTERVAL = 50;
	static final private int MINUTEINTERVAL = 5;
	static final public double SUNSTARTDEGREES = 0;
	static final public double SUNROTATIONRADIUS = 4000;

	/*static final public double SUNSTARTX = -4000;
	static final public double SUNSTARTY = 2000;
	static final public double SUNARCWIDTH = 7000;
	static final public double SUNPEAKX = (SUNARCWIDTH / 2) + SUNSTARTX;
	static final public double SUNPEAKY = 4000;*/
	
	static final public int SUNRISE = 7;
	static final public int SUNSET = 19;
	
	private List<TravelGraphicListener> listeners;
	
	private List<BufferedImage[]> backgrounds;
	
	private List<Environment> environments;
	private List<Integer> environmentOrder;
	private int environmentIndex;
	
	private double[] position;
	private int[] activeLayer;
	private int[] layersPassed;
	
	private Direction direction = Direction.LEFT;
	
	private Cart cart;
	private Lantern lantern;
	
	private Sun sun;
	private Moon moon;
	
	private int tick;
	
	private int minute;
	private int hour;
	
	private boolean hourPassed;
	private boolean moving;
	private boolean shouldStop = true;
	
	public TravelGraphic() {
		super();
		
		listeners = new ArrayList<TravelGraphicListener>();
		
		backgrounds = new ArrayList<BufferedImage[]>();
		
		environments = new ArrayList<Environment>();
		environmentOrder = new ArrayList<Integer>();
		
		position = new double[Environment.BACKDROPCOUNT];
		activeLayer = new int[Environment.BACKDROPCOUNT];
		layersPassed = new int[Environment.BACKDROPCOUNT];
		
		cart = new Cart();
		lantern = new Lantern();
			
		sun = new Sun();
		moon = new Moon();
		
		addEntity(cart);
		addEntity(lantern);
		
		addEntity(sun);
		addEntity(moon);
	}
	
	public void addListener(TravelGraphicListener listener) {
		listeners.add(listener);
	}
	
	public void removeListener(TravelGraphicListener listener) {
		listeners.remove(listener);
	}
	
	public List<Environment> getEnvironments() {
		return environments;
	}
	
	public void addEnvironment(Environment environment) {
		environments.add(environment);
		
		for (Backdrop backdrop : environment.getBackdrops()) {
			addEntity(backdrop);
		}
		for (Backdrop backdrop : environment.getFloor()) {
			addEntity(backdrop);
		}
		
		reset();
	}
	
	public void removeEnvironment(Environment environment) {
		environments.remove(environment);
		
		for (Backdrop backdrop : environment.getBackdrops()) {
			removeEntity(backdrop);
		}
		for (Backdrop backdrop : environment.getFloor()) {
			removeEntity(backdrop);
		}
		
		reset();
	}
	
	public void setEnvironmentOrder(List<Integer> order) {
		Iterator<Integer> iter = order.iterator();
		while (iter.hasNext()) {
			Integer i = iter.next();
			if (i < 0 || i >= environments.size()) {
				iter.remove();
			}
		}
		
		environmentIndex = 0;
		this.environmentOrder = order;
	}
	
	public void reset() {
		environmentIndex = -1;
		if (direction == Direction.LEFT) environmentIndex = 1;
		position = new double[Environment.BACKDROPCOUNT];
		activeLayer = new int[Environment.BACKDROPCOUNT];
		layersPassed = new int[Environment.BACKDROPCOUNT];
		
		if (direction == Direction.LEFT) for (int i=0; i<Environment.BACKDROPCOUNT; i++) activeLayer[i] = Environment.BACKDROPCOUNT - 1;
		
		for (Environment environment : environments) {
			environment.reset();
		}
		
		if (environmentOrder.size() == 0) {
			return;
		}
		
		for (int i=0; i<3; i++) {
			int layersPerDepth = Environment.BACKDROPCOUNT - i;
			int workingIndex = 0;
			int layersActivated = 0;
			
			for (int k=0; k<Environment.BACKDROPCOUNT; k++) {
				environments.get(environmentOrder.get(workingIndex)).setLayerActive(k, i, true);
				environments.get(environmentOrder.get(workingIndex)).setLayerActive(k, i, false);
				environments.get(environmentOrder.get(workingIndex)).setFloorActive(k, true);
				environments.get(environmentOrder.get(workingIndex)).setFloorActive(k, false);
				layersActivated++;
				if (layersActivated >= layersPerDepth) {
					workingIndex++;
					layersActivated = 0;
				}
				if (workingIndex >= environmentOrder.size()) {
					workingIndex = 0;
				}
			}
			
			layersPassed[i] = layersPerDepth - 1;
		}
	}
	
	public void changeDirection(Direction direction) {
		if (this.direction != direction) {
			int sign = 0;
			switch(direction) {
			case LEFT:
				sign = -1;
				break;
			case RIGHT:
				sign = 1;
				break;
			}
			for (int i=0; i<Environment.BACKDROPCOUNT; i++) {
				activeLayer[i] = (activeLayer[i] + Environment.BACKDROPCOUNT + (1 * sign)) % Environment.BACKDROPCOUNT;
			}
		}
		
		this.direction = direction;
		
		for (Environment environment : environments) {
			for (Backdrop backdrop : environment.getBackdrops()) {
				backdrop.setDirection(direction);
			}
			for (Backdrop backdrop : environment.getFloor()) {
				backdrop.setDirection(direction);
			}
		}
	}
	
	public boolean isMoving() {
		return moving;
	}
	
	public boolean hourPassed() {
		boolean value = hourPassed;
		hourPassed = false;
		
		return value;
	}
	
	public void stop() {
		shouldStop = true;
	}
	
	public void start() {
		moving = true;
		shouldStop = false;
	}
	
	@Override
	public void tick() {
		if (!moving) {
			return;
		}
		
		tick ++;
		
		if (tick % BOUNCEINTERVAL == 0) {
			cart.bounce();
		}
		
		if (tick % MINUTEINTERVAL == 0) {
			minute += 1;
			updateSky();
		}
		
		if (tick % BOUNCEINTERVAL == 0 && tick % MINUTEINTERVAL == 0) {
			tick = 0;
		}
		
		for (Environment environment : environments) {
			for (Backdrop backdrop : environment.getBackdrops()) {
				backdrop.scroll();
			}
			for (Backdrop backdrop : environment.getFloor()) {
				backdrop.scroll();
			}
		}
		
		if (environmentOrder.size() == 0) {
			reset();
			return;
		}
		
		int sign = 0;
		switch(direction) {
		case LEFT:
			sign = -1;
			break;
		case RIGHT:
			sign = 1;
			break;
		}
		
		for (int depth=0; depth<Environment.BACKDROPCOUNT; depth++) {
			position[depth] += Backdrop.getSpeed(depth) * sign;
		}
		
		boolean nextEnvironment = false;
		boolean[] layerPassed = new boolean[Environment.BACKDROPCOUNT];
		switch(direction) {
		case LEFT:
			for (int depth=0; depth<Environment.BACKDROPCOUNT; depth++) {
				if (position[depth] < 0) {
					layerPassed[depth] = true;
					position[depth] += Backdrop.WIDTH;
				}
			}
			break;
		case RIGHT:
			for (int depth=0; depth<Environment.BACKDROPCOUNT; depth++) {
				if (position[depth] > Backdrop.WIDTH) {
					layerPassed[depth] = true;
					position[depth] -= Backdrop.WIDTH;
				}
			}
		}
		
		for (int depth=0; depth<Environment.BACKDROPCOUNT; depth++) {
			int layersPerDepth = Environment.BACKDROPCOUNT - depth;
			if (layerPassed[depth]) {
				layersPassed[depth] += 1;
				
				if (layersPassed[depth] >= layersPerDepth) {
					nextEnvironment = true;
					layersPassed[depth] = 0;
				}
			}
		}
		
		if (nextEnvironment) {
			environmentIndex += (1 * sign);
			if (environmentIndex >= environmentOrder.size()) {
				environmentIndex = 0;
			} else if (environmentIndex < 0) {
				environmentIndex = environmentOrder.size() - 1;
			}
			
			for (TravelGraphicListener listener : listeners) listener.environmentPassed();
		}
		
		for (int depth = 0; depth<Environment.BACKDROPCOUNT; depth++) {
			if (layerPassed[depth]) {
				environments.get(environmentOrder.get(environmentIndex)).setLayerActive(activeLayer[depth], depth, true);
				environments.get(environmentOrder.get(environmentIndex)).setLayerActive(activeLayer[depth], depth, false);
				
				if (depth == 0) {
					environments.get(environmentOrder.get(environmentIndex)).setFloorActive(activeLayer[0], true);
					environments.get(environmentOrder.get(environmentIndex)).setFloorActive(activeLayer[0], false);
				}
				
				activeLayer[depth] += 1 * sign;
				if (activeLayer[depth] >= Environment.BACKDROPCOUNT) {
					activeLayer[depth] = 0;
				} else if (activeLayer[depth] < 0) {
					activeLayer[depth] = Environment.BACKDROPCOUNT - 1;
				}
			}
		}
	}
	
	public void setTime(int hour) {
		if (hour > backgrounds.size() - 1) {
			hour = backgrounds.size() - 1;
		} else if (hour < 0) {
			hour = 0;
		}
		
		minute = 60;
		this.hour = hour - 1;
		
		start();
		
		updateSky();
		hourPassed = false;
	}
	
	protected void updateSky() {
		if (minute == 60) {
			minute = 0;
			hour += 1;
			if (hour >= backgrounds.size()) {
				hour = 0;
			}
			
			setActiveBackground1(backgrounds.get(hour));
			setActiveBackground2(backgrounds.get((hour + 1 >= backgrounds.size()) ? 0 : hour + 1));
			
			moving = !shouldStop;
			hourPassed = true;
		}
		
		float blend = ((float) minute / 60.0f);
		blend = 1.0f - blend;
		setBackgroundBlend(blend);
		sun.moveSun(hour, minute);
		lantern.setNight(hour <= SUNRISE || hour >= SUNSET);
	}
	
	public void addBackground(BufferedImage background) {
		BufferedImage[] backarray = new BufferedImage[] {background};
		backgrounds.add(backarray);
		super.addBackground(backarray);
		sun.setDayLength(backgrounds.size());
		
		minute = 60;
		hour = -1;
		updateSky();
	}
}
