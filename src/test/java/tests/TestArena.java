package tests;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import OpenGLTests.TestDirLight;
import OpenGLTests.TestLight;
import amyGraphics.Animation;
import cartGame.combat.player.Arena;
import cartGame.combat.player.Player;
import movement.Entity;
import movement.Shapes.Ellipse;

public class TestArena extends Arena {
	public TestArena() {
		super();
		Player player = new TestCharacter();
		player.setPosition(new double[] {0, 0, 0});
		player.setDimensions(new double[] {1000, 1000, 200});
		setPlayer(player);
		player.setOutline(new Ellipse(player.getDimensions()));
		Entity dirLight = new TestDirLight();
		dirLight.setPosition(new double[] {-6000, 8000, -4000});
		dirLight.setDimensions(new double[] {50, 50, 50});
		Entity pointLight = new TestLight();
		pointLight.setPosition(new double[] {-3000, 500, 200});
		pointLight.setDimensions(new double[] {50, 50, 50});
		addEntity(dirLight);
		addEntity(pointLight);
		Entity floor = new TestFloor();
		addEntity(floor);
		for (int i=0; i<4; i++) {
			Entity wall = new TestWall(i);
			addEntity(wall);
		}
		
		BufferedImage[] background = new BufferedImage[6];
		for (int i=0; i<6; i++) {
			try {
				background[i] = ImageIO.read(new File("graphics/tests/skybox/skybox" + i + ".png"));
			} catch (IOException e) {
				BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
				image.setRGB(0, 0, Color.BLACK.getRGB());
				background[i] = image;
			}
		}
		setBackground(background);
	}
}
