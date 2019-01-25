package cartGame.travel.graphics;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import amyGraphics.Texture;
import amyGraphics.TexturePosition;
import movement.Room;

public class TravelGraphic extends Room {
	
	static final private int BOUNCEINTERVAL = 50;
	static final private int MINUTEINTERVAL = 5;
	static final public int BACKDROPCOUNT = 3;
	static final private double SUNSTARTDEGREES = 0;
	static final private double SUNROTATIONRADIUS = 4000;
	
	static final private int SUNRISE = 7;
	static final private int SUNSET = 19;
	
	private List<BufferedImage[]> backgrounds;
	
	private Cart cart;
	private Lantern lantern;
	
	private List<Backdrop> backdrops;
	private List<Backdrop> floor;
	
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
		
		backgrounds = new ArrayList<BufferedImage[]>();
		
		backdrops = new ArrayList<Backdrop>();
		floor = new ArrayList<Backdrop>();
		
		cart = new Cart();
		lantern = new Lantern();
			
		sun = new Sun();
		moon = new Moon();
		
		addEntity(cart);
		addEntity(lantern);
		
		addEntity(sun);
		addEntity(moon);
	}
	
	public void changeDirection(Backdrop.Direction direction) {
		for (Backdrop backdrop : backdrops) {
			backdrop.setDirection(direction);
		}
		
		for (Backdrop backdrop : floor) {
			backdrop.setDirection(direction);
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
		
		for (Backdrop backdrop : backdrops) {
			backdrop.scroll();
		}
		for (Backdrop backdrop : floor) {
			backdrop.scroll();
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
		moveSun();
	}
	
	protected void moveSun() {
		double degreeStep = 360 / backgrounds.size();
		
		double degreePosition = SUNSTARTDEGREES;
		double degreeOffset = hour + ((double) minute / 60.0);
		
		degreePosition += (degreeOffset - SUNRISE) * degreeStep;
		
		double z = SUNROTATIONRADIUS * Math.cos(Math.toRadians(degreePosition));
		double y = SUNROTATIONRADIUS * Math.sin(Math.toRadians(degreePosition));
		
		double[] sunPosition = sun.getPosition();
		sunPosition[1] = y;
		sunPosition[2] = z;
		
		double sunShine = 1.0;
		
		if (hour <= SUNRISE + 1) {
			sunShine = (hour + ((double) minute / 60.0) - (SUNRISE - 1)) / 2;
		} else if (hour >= SUNSET - 2) {
			sunShine = (SUNSET - hour + (1 - ((double) minute / 60.0))) / 3;
		}
		sun.setDay(sunShine);
		
		lantern.setNight(hour <= SUNRISE || hour >= SUNSET);
		lantern.setNight(false);
	}
	
	public void addBackground(BufferedImage background) {
		BufferedImage[] backarray = new BufferedImage[] {background};
		backgrounds.add(backarray);
		super.addBackground(backarray);
		
		minute = 60;
		hour = -1;
		updateSky();
	}
	
	public void addBackdrop(Texture texture, float start, double[] dimensions) {
		addBackdrop(texture, start, dimensions, 0);
	}
	
	public void addBackdrop(Texture texture, float start, double[] dimensions, int depth) {
		for (int i=0; i<BACKDROPCOUNT; i++) {
			Backdrop backdrop = new Backdrop(texture, start, dimensions, depth);
			double[] pos = backdrop.getPosition();
			pos[0] += (0 - (Backdrop.WIDTH * i)) + start;
			backdrops.add(backdrop);
			addEntity(backdrop);
		}
	}
	
	public void setGround(Texture texture, float start, double[] dimensions) {
		for (Backdrop floors : floor) {
			removeEntity(floors);
		}
		
		for (int i=0; i<BACKDROPCOUNT; i++) {
			Backdrop backdrop = new Backdrop(texture, start, dimensions, 0);
			backdrop.setTexturePosition(TexturePosition.TOP);
			double[] pos = backdrop.getPosition();
			pos[0] += (0 - (Backdrop.WIDTH * i)) + start;
			pos[2] = Cart.ZPOS;
			floor.add(backdrop);
			addEntity(backdrop);
		}
	}
}
