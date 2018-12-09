package tests;

import amyGraphics.Texture;
import cartGame.combat.player.Player;
import cartGame.io.ImageCache;

public class TestCharacter extends Player {

	public TestCharacter() {
		super();
		setTextures();
		loadTextures();
		idleRight();
	}
	
	private void setTextures() {
		String[] textures = new String[8];
		
		textures[0] = "graphics/combat/main test/back walk/backwalk.LCY";
		textures[1] = "graphics/combat/main test/front walk/frontwalk.LCY";
		textures[2] = "graphics/combat/main test/left walk/leftwalk.LCY";
		textures[3] = "graphics/combat/main test/right walk/rightwalk.LCY";
		
		textures[4] = "graphics/combat/main test/back walk/back1.png";
		textures[5] = "graphics/combat/main test/front walk/front1.png";
		textures[6] = "graphics/combat/main test/left walk/left1.png";
		textures[7] = "graphics/combat/main test/right walk/right1.png";
		
		setTextureLocations(textures);
	}
}
