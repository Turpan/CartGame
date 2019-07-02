package cartGame.combat.player.abilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import amyGraphics.Animation;
import amyGraphics.Texture;
import cartGame.io.ImageCache;
import movement.Movable;

public abstract class Ability {
	
	private String id;
	
	private int delay;
	private int duration;
	private int cooldown;
	
	private boolean activated;
	
	private Texture upAnimation;
	private Texture downAnimation;
	private Texture leftAnimation;
	private Texture rightAnimation;
	
	private String upAnimationLocation;
	private String downAnimationLocation;
	private String leftAnimationLocation;
	private String rightAnimationLocation;
	
	private int durationTimer;
	private int cooldownTimer;
	private int delayTimer;
	
	public Ability() {
		
	}
	
	public Ability(Ability ability) {
		setID(ability.getID());
		setDelay(ability.getDelay());
		setCooldown(ability.getCooldown());
		setDuration(ability.getDuration());
		setUpAnimationLocation(ability.getUpAnimationLocation());
		setDownAnimationLocation(ability.getDownAnimationLocation());
		setLeftAnimationLocation(ability.getLeftAnimationLocation());
		setRightAnimationLocation(ability.getRightAnimationLocation());
		loadAnimation();
	}
	
	public void triggerAbility(Movable self) {
		delayTimer = delay;
		activated = false;
	}
	
	public abstract void collisionEffect(Movable m);	//what happens to the the target when the active parts of a character using this ability hits the corporeal parts of another part.

	public abstract void constantEffect(Movable self);	//occurs every tick after activation

	public void activationEffect(Movable self) { //what happens upon activation of the ability to the character that has this ability.
		durationTimer = duration;
		cooldownTimer = cooldown;
		activated = true;
		self.resetAbilityHits();
	}
		
	public abstract void endEffect(Movable self); 
	
	public boolean prerequisitesMet(Movable self) {
		return !isCooling();
	}	
	
	public void tick() {
		if (delayOn()) {
			delayTimer -= 1;
		}
		else if (isActive()) {
			durationTimer -= 1;
		}
			
		else if (isCooling()) {
			cooldownTimer -= 1;
		}
	}
	
	public boolean delayOn() {
		return delayTimer > 0;
	}
	
	public boolean isActive() {
		return durationTimer > 0;
	}
	
	public boolean isCooling() {
		return cooldownTimer > 0;
	}
	
	public boolean activated() {
		return activated;
	}
	
	public void loadAnimation() {
		upAnimation = ImageCache.getTexture(upAnimationLocation);
		downAnimation = ImageCache.getTexture(downAnimationLocation);
		leftAnimation = ImageCache.getTexture(leftAnimationLocation);
		rightAnimation = ImageCache.getTexture(rightAnimationLocation);
	}
	
	public String getID() {
		return id;
	}

	public void setID(String id) {
		this.id = id;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}
	
	public int getCooldown() {
		return cooldown;
	}

	public void setCooldown(int cooldown) {
		this.cooldown = cooldown;
	}

	public String getUpAnimationLocation() {
		return upAnimationLocation;
	}

	public void setUpAnimationLocation(String upAnimationLocation) {
		this.upAnimationLocation = upAnimationLocation;
	}

	public String getDownAnimationLocation() {
		return downAnimationLocation;
	}

	public void setDownAnimationLocation(String downAnimationLocation) {
		this.downAnimationLocation = downAnimationLocation;
	}

	public String getLeftAnimationLocation() {
		return leftAnimationLocation;
	}

	public void setLeftAnimationLocation(String leftAnimationLocation) {
		this.leftAnimationLocation = leftAnimationLocation;
	}

	public String getRightAnimationLocation() {
		return rightAnimationLocation;
	}

	public void setRightAnimationLocation(String rightAnimationLocation) {
		this.rightAnimationLocation = rightAnimationLocation;
	}

	public Texture getUpAnimation() {
		return upAnimation;
	}

	public Texture getDownAnimation() {
		return downAnimation;
	}

	public Texture getLeftAnimation() {
		return leftAnimation;
	}

	public Texture getRightAnimation() {
		return rightAnimation;
	}
	
	public List<Texture> getTextures() {
		List<Texture> textures = new ArrayList<Texture>();
		textures.add(upAnimation);
		textures.add(downAnimation);
		textures.add(leftAnimation);
		textures.add(rightAnimation);
		return textures;
	}

}
