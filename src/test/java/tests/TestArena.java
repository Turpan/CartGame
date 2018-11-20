package tests;

import OpenGLTests.TestDirLight;
import cartGame.combat.player.Arena;
import cartGame.combat.player.Player;
import movement.Entity;
import movement.Entity.MalformedEntityException;
import movement.Shapes.Ellipse;
import movement.mathDS.Vector.MalformedVectorException;

public class TestArena extends Arena {
	public TestArena() {
		super();
		
		try {
			Player player = new TestCharacter();
			player.setPosition(new float[] {0, 0, 0});
			player.setDimensions(new double[] {1000, 1000, 200});
			setPlayer(player);
			player.setOutline(new Ellipse(player.getDimensions()));
			Entity dirLight = new TestDirLight();
			dirLight.setPosition(new float[] {0, 8000, 0});
			dirLight.setDimensions(new double[] {0, 0, 0});
			addEntity(dirLight);
			Entity floor = new TestFloor();
			addEntity(floor);
			for (int i=0; i<4; i++) {
				Entity wall = new TestWall(i);
				addEntity(wall);
			}
		} catch (MalformedVectorException | MalformedEntityException e) {
			this.setPlayer(null);
		}
	}
}
