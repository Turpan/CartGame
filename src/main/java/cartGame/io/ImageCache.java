package cartGame.io;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import amyGraphics.Animation;
import amyGraphics.Texture;
import amyInterface.FontTexture;
import lucyAnimation.LucyIO;

public class ImageCache {
	private static Map<String, Texture> textures = new HashMap<String, Texture>();
	private static Map<String, Animation> animations = new HashMap<String, Animation>();
	private static Map<String, FontTexture> fonts = new HashMap<String, FontTexture>();
	
	public static Animation getAnimation(String imageFile) {
		if (imageFile == null) {
			imageFile = "";
		}
		
		Animation anim = animations.get(imageFile);
		
		if (anim == null) {
			return new Animation(loadAnimation(imageFile));
		}
		
		return new Animation(anim);
	}
	
	private static Animation loadAnimation(String imageFile) {
		Animation anim = LucyIO.readLucyFile(imageFile);
		
		if (anim == null) {
			BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
			image.setRGB(0, 0, Color.BLACK.getRGB());
			anim = new Animation(image, 1, 1);
		}
		
		animations.put(imageFile, anim);
		
		return anim;
	}
	
	public static Texture getTexture(String imageFile) {
		if (imageFile == null) {
			imageFile = "";
		}
		
		if (imageFile.toLowerCase().endsWith(".lcy")) {
			return loadAnimation(imageFile);
		}
		
		Texture texture = textures.get(imageFile);
		
		if (texture == null) {
			return new Texture(loadTexture(imageFile));
		}
		
		return new Texture(texture);
	}
	
	private static Texture loadTexture(String imageFile) {
		BufferedImage image;
		try {
			image = ImageIO.read(new File(imageFile));
		} catch (IOException e) {
			image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
			image.setRGB(0, 0, Color.BLACK.getRGB());
		}
		Texture texture = new Texture(image);
		
		textures.put(imageFile, texture);
		
		return texture;
	}
	
	public static FontTexture getFont(String imageFile) {
		if (imageFile == null) {
			imageFile = "";
		}
		
		FontTexture texture = fonts.get(imageFile);
		
		if (texture == null) {
			return new FontTexture(loadFont(imageFile));
		}
		
		return new FontTexture(texture);
	}
	
	private static FontTexture loadFont(String imageFile) {
		FontTexture font = FontTexture.createFontTexture(imageFile, true);
		
		fonts.put(imageFile, font);
		
		return font;
	}
	
	public static void removeImage(String imageFile) {
		textures.remove(imageFile);
	}
}
