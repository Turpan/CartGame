package cartGame.travel.graphics;

import java.awt.image.BufferedImage;

import amyGraphics.Texture;
import cartGame.io.ImageCache;
import movement.Entity;

public class Cart extends Entity {
	static final private int BOUNCE = 0;
	
	static final public int ZPOS = -500;
	
	boolean up;
	
	public Cart() {
		super();
		
		Texture texture = loadImage();
		addTexture(texture);
		setActiveTexture(texture);
		setDimensions(new double[] {750, 350, 0});
		setPosition(new double[] {0, 0, 0});
	}
	protected Cart (Cart cart) {
		super(cart);
		up = cart.up;
	}
	
	@Override
	public Cart clone() {
		return new Cart(this);
	}
	public Texture loadImage() {
		Texture texture = ImageCache.getTexture("graphics/travel/cart/cart.png");
		return texture;
	}
	
	public void bounce() {
		double[] position = getPosition();
		double y = position[1];
		
		if (up) {
			y -= BOUNCE;
		} else {
			y += BOUNCE;
		}
		
		up = !up;
		
		position[1] = y;
	}

}
