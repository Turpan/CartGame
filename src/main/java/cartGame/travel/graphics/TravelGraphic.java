package cartGame.travel.graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import amyGraphics.Texture;
import movement.Room;

public class TravelGraphic extends Room {
	
	static final private int BOUNCEINTERVAL = 50;
	static final public int BACKDROPCOUNT = 3;
	
	Cart cart;
	List<Backdrop> backdrops;
	
	Sun sun;
	
	int tick;
	
	public TravelGraphic() {
		super();
		
		setBackground(loadBackground());
		
		backdrops = new ArrayList<Backdrop>();
		cart = new Cart();
			
		sun = new Sun();
		
		addEntity(cart);
		addEntity(sun);
	}
	
	@Override
	public void tick() {
		tick ++;
		
		if (tick == BOUNCEINTERVAL) {
			tick = 0;
			cart.bounce();
		}
		for (Backdrop backdrop : backdrops) {
			backdrop.scroll();
		}
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
	
	private BufferedImage[] loadBackground() {
		BufferedImage[] background = new BufferedImage[6];
		for (int i=0; i<6; i++) {
			try {
				background[i] = ImageIO.read(new File("graphics/tests/skybox/skybox" + i + ".png"));
			} catch (IOException e) {
				return null;
			}
		}
		return background;
	}
}
