package cartGame.ui.travel;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import amyGraphics.Texture;
import amyInterface.Component;

public class TravelHealthBar extends Component {
	public TravelHealthBar() {
		setPreferredSize(148, 17);
		setVisible(true);
		
		Texture texture = loadTexture();
		addTexture(texture);
		setActiveTexture(texture);
	}
	
	public Texture loadTexture() {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File("graphics/ui/travel/healthplate.png"));
		} catch (IOException e) {
			image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		}
		
		Texture texture = new Texture(image);
		
		return texture;
	}
}
