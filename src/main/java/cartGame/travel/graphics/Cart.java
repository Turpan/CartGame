package cartGame.travel.graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import amyGraphics.Texture;
import cartGame.io.ImageCache;
import movement.Entity;

public class Cart extends Entity {
	static final private int BOUNCE = 100;
	
	boolean up;
	
	public Cart() throws MalformedEntityException {
		super();
		
		Texture texture = loadImage();
		addTexture(texture);
		setActiveTexture(texture);
		setDimensions(new double[] {750, 1000, 0});
		setPosition(new float[] {4000, 500, 0});
	}
	
	public Texture loadImage() {
		BufferedImage img = ImageCache.getImage("graphics/cart.png");
		Texture texture = new Texture(img);
		texture.setWidth(img.getWidth());
		texture.setHeight(img.getHeight());
		return texture;
	}
	
	public void bounce() {
		float[] position = getPosition();
		float y = position[1];
		
		if (up) {
			y -= BOUNCE;
		} else {
			y += BOUNCE;
		}
		
		up = !up;
		
		position[1] = y;
	}

}
