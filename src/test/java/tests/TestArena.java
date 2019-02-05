package tests;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import OpenGLTests.TestDirLight;
import OpenGLTests.TestLight;
import amyGraphics.Animation;
import cartGame.combat.player.Arena;
import cartGame.combat.player.Player;
import movement.Entity;
import movement.MovableStateWrapper;
import movement.Shapes.Ellipse;
import movement.Shapes.Parabaloid;
import movement.Shapes.StandardShape;

public class TestArena extends Arena {
	public TestArena() {
		super();
		Player player = new TestCharacter();
		player.setPosition(new double[] {0, 0, 0});
		player.setDimensions(new double[] {1000, 1000, 200});
		HashMap<StandardShape, MovableStateWrapper> playerState0 = new HashMap<StandardShape, MovableStateWrapper>();
		playerState0.put(new Ellipse(player.getDimensions()), new MovableStateWrapper(new double[]{0,0,0}, false));
		HashMap<StandardShape, MovableStateWrapper> playerState1 = new HashMap<StandardShape, MovableStateWrapper>();
		playerState1.put(new Ellipse(player.getDimensions()), new MovableStateWrapper(new double[]{0,0,0}, false));
		playerState1.put(new Parabaloid(new double[] {100,100,100},1,0,5,false), new MovableStateWrapper(new double[]{1000,600,50}, true ));
		var playerStates = new ArrayList<Map<StandardShape, MovableStateWrapper>>();
		playerStates.add(playerState0);
		playerStates.add(playerState1);
		player.setStates(playerStates);
		
		player.setRotationAxis(new double[] {0,1,0});
		player.setAngle(0);
		player.setCentreOfRotation(new double[] {500,500,100});
		setPlayer(player);
		
//		Player player2 = new TestCharacter();
//		player2.setPosition(new double[] {0, 2000, 0});
//		player2.setDimensions(new double[] {1000, 1000, 200});
//		player2.setSimpleOutline(new Ellipse(player.getDimensions()));
//		player2.setRotationAxis(new double[] {0,1,0});
//		player2.setAngle(0);
//		player2.setCentreOfRotation(new double[] {500,500,100});
//		addEntity(player2);
		
		Entity dirLight = new TestDirLight();
		dirLight.setPosition(new double[] {-6000, 8000, -4000});
		dirLight.setDimensions(new double[] {50, 50, 50});
		Entity pointLight = new TestLight();
		pointLight.setPosition(new double[] {-3000, 500, 200});
		pointLight.setDimensions(new double[] {50, 50, 50});
		addEntity(dirLight);
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
		addBackground(background);
		setActiveBackground1(background);
		setActiveBackground2(background);
	}
}
