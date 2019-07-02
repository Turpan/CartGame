package tests;

import amyGraphics.Texture;
import cartGame.combat.player.Being;
import cartGame.io.ImageCache;

public class TestBeing extends Being {
	
	private static final double MASS =1;
	private static final double BASEMOVEFORCE =28;
	private static final double COEFFICIENT_OF_RESTITUTION = 0.2;	//'bounciness' Used for collisions. See Controllable. Not the whole story, as I've attempted to disallow objects enterring other objects, regardless of CoR
	private static final double COEFFICIENT_OF_DRAG = 0.0009;			//coefficient of proportionality between quadratic drag and speed
	private static final double  COEFFICIENT_OF_FRICTION = 7.5; 	//the coefficient of proportionality between the constant drag and mass

	private static final int HEALTH = 100;
	private static final int MAX_HEALTH= 100;
	private static final boolean DEAD = false;
	
	public TestBeing() {
		super();
		setBaseMoveForce(BASEMOVEFORCE);
		setMass(MASS);
		setCoF(COEFFICIENT_OF_FRICTION);
		setCoD(COEFFICIENT_OF_DRAG);
		setCoR(COEFFICIENT_OF_RESTITUTION);
		setMaxHealth(100);
		setHealth(100);

		setTextures();
		loadTextures();
		idleRight();
	}
	
	protected TestBeing(TestBeing tb) {
		super(tb);
		setBaseMoveForce(tb.getBaseMoveForce());
		setMass(tb.getMass());
		setCoF(tb.getCoF());
		setCoD(tb.getCoD());
		setCoR(tb.getCoR());
		setMaxHealth(tb.getMaxHealth());
		setHealth(tb.getHealth());

		setTextures();
		loadTextures();
		idleRight();
	}
	
	@Override
	public TestBeing clone(){
	return new TestBeing(this);	
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
