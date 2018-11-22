package cartGame.travel.graphics;

import java.awt.image.BufferedImage;

import amyGraphics.Texture;
import cartGame.io.ImageCache;
import movement.Entity;

public class Cart extends Entity {
	static final private int BOUNCE = 100;
	
	boolean up;
	
	public Cart() {
		super();
		
		Texture texture = loadImage();
		addTexture(texture);
		setActiveTexture(texture);
		setDimensions(new double[] {750, 1000, 0});
		setPosition(new double[] {4000, 500, 0});
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
		BufferedImage img = ImageCache.getImage("graphics/cart.png");
		Texture texture = new Texture(img);
		texture.setWidth(img.getWidth());
		texture.setHeight(img.getHeight());
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
