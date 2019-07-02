package cartGame.combat.player;

import amyGraphics.Animation;
import amyGraphics.Texture;
import cartGame.combat.player.abilities.Ability;
import cartGame.io.ImageCache;
import movement.Movable;
import movement.SelfPropelled;

public abstract class Being extends SelfPropelled implements Alive{
	//Beings are selfPropelleds that are /people/. Or similar creatures. They are fully
	//fleshed, with a variety of animations, the capacity to take damage, and activate abilities.
	//As such, they require health, animations, and abilities.
	
	//this class will include the player and most enemies.
	
	private static final int UP = 0;
	private static final int DOWN = 2;
	private static final int LEFT = 0;
	private static final int RIGHT = 2;

	private int health;
	private int maxHealth;
	private boolean dead;
	
	private String ID;
	
	private Ability[] abilities = new Ability[3];
	private int[] abilityMovableStateDictionary = new int[3]; //pretty much what it says on the tin. index = ability number. value = stateNumber. I'm going to need something more complex if I ever want multi-state hitboxes for one attack. 
	private int activeAbility = -1;
	
	private final Texture[] movementTextures = new Texture[8];
	
	private final String[] textureLocations = new String[8];
	
	private int lastX;
	private int lastY;
	
	public Being() {
	}
	
	public Being (Being b) {
		setTextureLocations(b.getTextureLocations());
		setAbilities(b.getAbilities().clone(), b.abilityMovableStateDictionary.clone());
		loadTextures();
	}
	
	public void updateMovementTexture(int x, int y) {
		for (Ability ability : abilities) {
			if (ability == null ||ability.isActive() ) {
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
	
	public void useAbility(int i) {
		if (i > 2 || i<0 || getAbilities()[i] == null || getActiveAbility() != -1 ) {
			return;
		}
		var ability = getAbilities()[i];
		if (ability.prerequisitesMet(this)) {
			ability.triggerAbility(this);
			setActiveAbility(i);
			
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
	}
	
	@Override
	public void tick() {
		super.tick();
		if (getActiveAbility() != -1) {
			var ability = getAbilities()[getActiveAbility()]; 
			if (!ability.delayOn()) {
				if (!ability.activated()) {
					ability.activationEffect(this);
				}
				else if (!ability.isActive()){
					getAbilities()[getActiveAbility()].endEffect(this);
					setActiveAbility(-1);
				}
				else {
				getAbilities()[getActiveAbility()].constantEffect(this);
				}
			}
		}
		for (Ability ability : abilities) {
			if (ability != null) {
				ability.tick();
			}
		}
	}

	public String[] getTextureLocations() {
		return textureLocations;
	}
	
	public void setTextureLocations(String[] textureLocations) {
		int last = 0;
		for (int i=0; i<textureLocations.length; i++) {
			if (i >= 8) {
				break;
			}
			
			this.textureLocations[i] = textureLocations[i];
			last = i;
		}
		
		for (int i=last+1; i<8; i++) {
			this.textureLocations[i] = null;
		}
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
			setMovementTexture(ImageCache.getTexture(textureLocations[i]), i);
		}
	}
	
	private void setMovementTexture(Texture newTexture, int index) {
		if (getTextures().contains(movementTextures[index])) {
			removeTexture(movementTextures[index]);
		}
		
		addTexture(newTexture);
		movementTextures[index] = newTexture;
	}

	
	private Ability[] getAbilities() {
		return abilities;
	}
	public void setAbilities(Ability[] abilities, int[] abilityStateDictionary) {
		this.abilities = new Ability[3];
		if (abilityStateDictionary.length<3) {
			var tmp = new int[3];
			for (int i = 0; i< abilityStateDictionary.length && i < 3; i++) {
				tmp[i] = abilityStateDictionary[i];
			}
			abilityStateDictionary = tmp;
		}
		for (int i = 0; i < abilities.length && i < 3;i++) {
			setAbility(abilities[i],i, abilityStateDictionary[i]);
		}
	}

	public void setAbility(Ability ability, int abilityNum, int state) {	//can't just edit abilities using getAbilities!
		if (abilityNum<0||abilityNum>2) {
			return;
		}
		if (getAbilities()[abilityNum] != null) {
			for (Texture texture : getAbilities()[abilityNum].getTextures()) {
				removeTexture(texture);
			}
		}
		if (ability != null){
			getAbilities()[abilityNum] = ability;
			for (Texture texture : ability.getTextures()) {
				addTexture(texture);
			}
		}
	}
	public int getActiveAbility() {	
		return activeAbility;		//we can take it as given that this outputs a value that's either a valid ability number or -1;
	}
	private void setActiveAbility(int i) {
		activeAbility = i;
		if (i == -1) {
			setState(0);
		}
		else {setState(abilityMovableStateDictionary[i]);}
		
	}
	
	@Override
	public void applyCollisionEffect(Movable m) {
		if (getActiveAbility() != -1) {
			getAbilities()[getActiveAbility()].collisionEffect(m);
		}
	}
	@Override
	public int getHealth() {
		return health;
	}
	@Override
	public void setHealth(int health) {
		if (health > maxHealth) {
			this.health = maxHealth; 
		} else if (health < 0) {
			this.health = 0;
			dead = true; 
		} else {
			this.health = health;
		}
		
	}
	@Override
	public int getMaxHealth() {
		return maxHealth;
	}
	@Override
	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
		
	}
	@Override
	public void damage(int damage) {
		setHealth(getHealth()- damage);
	}
	@Override
	public boolean dead() {
		return dead;
	}
}
