package tests;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import OpenGLTests.TestDirLight;
import OpenGLTests.TestLight;
import cartGame.combat.player.Arena;
import cartGame.combat.player.Being;
import cartGame.combat.player.abilities.Ability;
import cartGame.combat.player.abilities.AttackAbility;
import cartGame.combat.player.abilities.JumpAbility;
import movement.Entity;
import movement.MovableStateWrapper;
import movement.Shapes.Ellipse;
import movement.Shapes.OutlineShape;

public class TestArena extends Arena {
	public TestArena() {
		super();
		Being player = new TestBeing();
		player.setPosition(new double[] {0, 1000, 0});
		HashMap<OutlineShape, MovableStateWrapper> playerState0 = new HashMap<OutlineShape, MovableStateWrapper>();
		HashMap<OutlineShape, MovableStateWrapper> playerState1 = new HashMap<OutlineShape, MovableStateWrapper>();
		playerState0.put(new Ellipse(new double[] {200, 1000, 200}), new MovableStateWrapper(new double[]{0,0,0}, false, false));
		playerState1.put(new Ellipse(new double[] {200, 1000, 200}), new MovableStateWrapper(new double[]{0,0,0}, false, false));
		playerState1.put(new Ellipse(new double[] {100,100,100}), new MovableStateWrapper(new double[]{200,550,50},false, true));
		var playerStates = new ArrayList<Map<OutlineShape, MovableStateWrapper>>();
		playerStates.add(playerState1);
		playerStates.add(playerState1);
		player.setStates(playerStates);
		
		Ability[] abilities = new Ability[3];
		abilities[0] = new AttackAbility();
		abilities[1] = new JumpAbility();
		player.setAbilities(abilities, new int[] {1,0});
		
		player.setRotationAxis(new double[] {0,1,0});
		player.setCentreOfRotation(new double[] {100,500,100});
		setPlayer(player);

		Being player2 = new TestBeing();
		player2.setPosition(new double[] {2500, 1, 0});
		player2.setSimpleOutline(new Ellipse(new double[] {200,1000,200}));
		player2.setRotationAxis(new double[] {0,1,0});
		player2.setAngle(0);
		player2.setCentreOfRotation(new double[] {500,500,100});
//		
//		Player player3 = new TestBeing();
//		player3.setPosition(new double[] {1500, 1, -80});
//		player3.setSimpleOutline(new Ellipse(new double[] {200,1000,200}));
//		player3.setRotationAxis(new double[] {0,1,0});
//		player3.setAngle(0);
//		player3.setCentreOfRotation(new double[] {500,500,100});
//		
//		Player player4 = new TestBeing();
//		player4.setPosition(new double[] {2000, 1, 80});
//		player4.setSimpleOutline(new Ellipse(new double[] {200,1000,200}));
//		player4.setRotationAxis(new double[] {0,1,0});
//		player4.setAngle(0);
//		player4.setCentreOfRotation(new double[] {500,500,100});
//		
//		Player player5 = new TestBeing();
//		player5.setPosition(new double[] {1000, 1, 40});
//		player5.setSimpleOutline(new Ellipse(new double[] {200,1000,200}));
//		player5.setRotationAxis(new double[] {0,1,0});
//		player5.setAngle(0);
//		player5.setCentreOfRotation(new double[] {500,500,100});
//		
//		Player player6 = new TestBeing();
//		player6.setPosition(new double[] {-1000, 1, 20});
//		player6.setSimpleOutline(new Ellipse(new double[] {200,1000,200}));
//		player6.setRotationAxis(new double[] {0,1,0});
//		player6.setAngle(0);
//		player6.setCentreOfRotation(new double[] {500,500,100});
//		
		addEntity(player2);
//		addEntity(player3);
//		addEntity(player4);
//		addEntity(player5);
//		addEntity(player6);
		
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
