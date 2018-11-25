package cartGame.combat.player;

import java.util.ArrayList;
import java.util.List;

import amyGraphics.Animation;
import amyGraphics.Texture;
import cartGame.io.ImageCache;
import movement.Movable;
import movement.Obstacle;
import movement.SelfPropelled;

public class Player extends SelfPropelled {
	
	private static final double MASS =5;
	private static final double BASEMOVEFORCE =2000;
	private static final double COEFFICIENT_OF_RESTITUTION = 0.05;	//'bounciness' Used for collisions. See Controllable. Not the whole story, as I've attempted to disallow objects enterring other objects, regardless of CoR
	private static final double COEFFICIENT_OF_DRAG = 0.1;			//coefficient of proportionality between quadratic drag and speed
	private static final double  COEFFICIENT_OF_FRICTION = 8; 	//the coefficient of proportionality between the constant drag and mass
	
	private static final int UP = 0;
	private static final int DOWN = 2;
	private static final int LEFT = 0;
	private static final int RIGHT = 2;
	
	private String ID;
	
	private List<Ability> abilities = new ArrayList<Ability>();
	
	private Ability ability1;
	private Ability ability2;
	private Ability ability3;
	
	private final Texture[] movementTextures = new Texture[8];
	
	private final String[] textureLocations = new String[8];
	
	private int lastX;
	private int lastY;

	public Player() {
		super();
		setBaseMoveForce(BASEMOVEFORCE);
		setMass(MASS);
		setCoF(COEFFICIENT_OF_FRICTION);
		setCoD(COEFFICIENT_OF_DRAG);
		setCoR(COEFFICIENT_OF_RESTITUTION);
	}
	protected Player(Player player) {
		super(player);
		setBaseMoveForce(player.getBaseMoveForce());
		setMass(player.getMass());
		setCoF(player.getCoF());
		setCoD(player.getCoD());
		setCoR(player.getCoR());
	}
	
	@Override
	public Player clone(){
	return new Player(this);	
	}	
	public void updateMovementTexture(int x, int y) {
		for (Ability ability : abilities) {
			if (ability.isActive()) {
				return;
			}
		}
		
		if (x == RIGHT) {
			moveRight();
		} else if (x == LEFT) {
			moveLeft();
		} else if (y == UP) {
			moveUp();
		} else if (y == DOWN) {
			moveDown();
		}
		
		if (x == RIGHT || x == LEFT
				|| y == UP || y == DOWN) {
			lastX = x;
			lastY = y;
			return;
		}
		
		if (lastX == RIGHT) {
			idleRight();
			return;
		} else if (lastX == LEFT) {
			idleLeft();
			return;
		} else if (lastY == UP) {
			idleUp();
			return;
		} else if (lastY == DOWN) {
			idleDown();
			return;
		}
		
		idleRight();
	}
	
	public void useAbility(Ability ability) {
		if (ability == null) {
			return;
		}
		
		if (!abilities.contains(ability)) {
			return;
		}
		
		for (Ability toCheck : abilities) {
			if (toCheck.isActive()) {
				return;
			}
		}
		
		if (ability.isCooling()) {
			return;
		}
		
		ability.abilityUsed();
		
		if (lastX == RIGHT) {
			if (ability.getRightAnimation() instanceof Animation) {
				Animation anim = (Animation) ability.getRightAnimation();
				anim.setFramePosition(0);
			}
			setActiveTexture(ability.getRightAnimation());
			return;
		} else if (lastX == LEFT) {
			if (ability.getLeftAnimation() instanceof Animation) {
				Animation anim = (Animation) ability.getLeftAnimation();
				anim.setFramePosition(0);
			}
			setActiveTexture(ability.getLeftAnimation());
			return;
		} else if (lastY == UP) {
			if (ability.getUpAnimation() instanceof Animation) {
				Animation anim = (Animation) ability.getUpAnimation();
				anim.setFramePosition(0);
			}
			setActiveTexture(ability.getUpAnimation());
			return;
		} else if (lastY == DOWN) {
			if (ability.getDownAnimation() instanceof Animation) {
				Animation anim = (Animation) ability.getDownAnimation();
				anim.setFramePosition(0);
			}
			setActiveTexture(ability.getDownAnimation());
			return;
		}
	}
	
	@Override
	public void tick() {
		super.tick();
		for (Ability ability : abilities) {
			ability.tick();
		}
	}

	public String[] getTextureLocations() {
		return textureLocations;
	}

	public void moveUp() {
		setActiveTexture(movementTextures[0]);
	}

	public void moveDown() {
		setActiveTexture(movementTextures[1]);
	}
	
	public void moveLeft() {
		setActiveTexture(movementTextures[2]);
	}
	
	public void moveRight() {
		setActiveTexture(movementTextures[3]);
	}
	
	public void idleUp() {
		setActiveTexture(movementTextures[4]);
	}
	
	public void idleDown() {
		setActiveTexture(movementTextures[5]);
	}
	
	public void idleLeft() {
		setActiveTexture(movementTextures[6]);
	}
	
	public void idleRight() {
		setActiveTexture(movementTextures[7]);
	}
	
	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}
	
	public void loadTextures() {
		for (int i=0; i<8; i++) {
			setMovementTexture(ImageCache.getTexture(textureLocations[i]), movementTextures[i]);
		}
	}
	
	private void setMovementTexture(Texture newTexture, Texture oldTexture) {
		if (getTextures().contains(oldTexture)) {
			removeTexture(oldTexture);
		}
		
		addTexture(newTexture);
		oldTexture = newTexture;
	}

	public Ability getAbility1() {
		return ability1;
	}

	public void setAbility1(Ability ability1) {
		if (abilities.contains(this.ability1)) {
			abilities.remove(this.ability1);
			for (Texture texture : ability1.getTextures()) {
				removeTexture(texture);
			}
		}
		this.ability1 = ability1;
		abilities.add(ability1);
		
		for (Texture texture : ability1.getTextures()) {
			addTexture(texture);
		}
	}

	public Ability getAbility2() {
		return ability2;
	}

	public void setAbility2(Ability ability2) {
		if (abilities.contains(this.ability2)) {
			abilities.remove(this.ability2);
			for (Texture texture : ability2.getTextures()) {
				removeTexture(texture);
			}
		}
		this.ability2 = ability2;
		abilities.add(ability2);
		for (Texture texture : ability2.getTextures()) {
			addTexture(texture);
		}
	}

	public Ability getAbility3() {
		return ability3;
	}

	public void setAbility3(Ability ability3) {
		if (abilities.contains(this.ability3)) {
			abilities.remove(this.ability3);
			for (Texture texture : ability3.getTextures()) {
				removeTexture(texture);
			}
		}
		this.ability3 = ability3;
		abilities.add(ability3);
		for (Texture texture : ability3.getTextures()) {
			addTexture(texture);
		}
	}

	@Override
	public void collision(Obstacle o) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void collision(Movable m) {
		// TODO Auto-generated method stub
		
	}

}
