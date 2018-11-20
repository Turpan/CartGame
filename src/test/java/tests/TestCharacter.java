package tests;

import amyGraphics.Texture;
import cartGame.combat.player.Player;
import cartGame.io.ImageCache;

public class TestCharacter extends Player {

	public TestCharacter() {
		super();
		setTextures();
	}
	
	private void setTextures() {
		setUpAnimation(ImageCache.getAnimation("graphics/combat/main test/back walk/backwalk.LCY"));
		setDownAnimation(ImageCache.getAnimation("graphics/combat/main test/front walk/frontwalk.LCY"));
		setLeftAnimation(ImageCache.getAnimation("graphics/combat/main test/left walk/leftwalk.LCY"));
		setRightAnimation(ImageCache.getAnimation("graphics/combat/main test/right walk/rightwalk.LCY"));
		
		setUpIdle(new Texture(ImageCache.getImage("graphics/combat/main test/back walk/back1.png")));
		setDownIdle(new Texture(ImageCache.getImage("graphics/combat/main test/front walk/front1.png")));
		setLeftIdle(new Texture(ImageCache.getImage("graphics/combat/main test/left walk/left1.png")));
		setRightIdle(new Texture(ImageCache.getImage("graphics/combat/main test/right walk/right1.png")));
		
		setActiveTexture(getRightIdle());
	}
}
