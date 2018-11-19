package cartGame.ui.town;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import amyGraphics.Texture;
import amyInterface.Button;

public class TownMenuButton extends Button {
	
	public TownMenuButton() {
		setVisible(true);
		setInteractable(true);
		
		setButtonTextures(loadTexture(), loadPressedTexture());
		
		setPreferredSize(61, 12);
	}
	
	public Texture loadTexture() {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File("graphics/ui/town/menubutton.png"));
		} catch (IOException e) {
			image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		}
		
		Texture texture = new Texture(image);
		
		return texture;
	}
	
	public Texture loadPressedTexture() {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File("graphics/ui/town/menubutton-pressed.png"));
		} catch (IOException e) {
			image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		}
		
		Texture texture = new Texture(image);
		
		return texture;
	}
}
