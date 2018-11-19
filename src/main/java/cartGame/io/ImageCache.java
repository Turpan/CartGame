package cartGame.io;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import amyGraphics.Animation;
import amyGraphics.Animation.MalformedAnimationException;
import lucyAnimation.LucyIO;

public class ImageCache {
	private static Map<String, BufferedImage> images = new HashMap<String, BufferedImage>();
	private static Map<String, Animation> animations = new HashMap<String, Animation>();
	
	public static Animation getAnimation(String imageFile) {
		Animation anim = animations.get(imageFile);
		
		if (anim == null) {
			return loadAnimation(imageFile);
		}
		
		return anim;
	}
	
	private static Animation loadAnimation(String imageFile) {
		Animation anim = LucyIO.readLucyFile(imageFile);
		
		if (anim == null) {
			BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
			image.setRGB(0, 0, Color.BLACK.getRGB());
			try {
				anim = new Animation(image, 1, 1);
			} catch (MalformedAnimationException e) {
				anim = null;
			}
		}
		
		animations.put(imageFile, anim);
		
		return anim;
	}
	
	public static BufferedImage getImage(String imageFile) {
		BufferedImage image = images.get(imageFile);
		
		if (image == null) {
			return loadImage(imageFile);
		}
		
		return image;
	}
	
	private static BufferedImage loadImage(String imageFile) {
		BufferedImage image;
		try {
			image = ImageIO.read(new File(imageFile));
		} catch (IOException e) {
			image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
			image.setRGB(0, 0, Color.BLACK.getRGB());
		}
		
		images.put(imageFile, image);
		
		return image;
	}
	
	public static void removeImage(String imageFile) {
		images.remove(imageFile);
	}
}
