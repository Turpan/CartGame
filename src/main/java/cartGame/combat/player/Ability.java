package cartGame.combat.player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import amyGraphics.Animation;
import amyGraphics.Texture;
import cartGame.io.ImageCache;

public class Ability {
	
	private String id;
	
	private int duration;
	private int cooldown;
	
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
	
	public Ability() {
		
	}
	
	public Ability(Ability ability) {
		setID(ability.getID());
		setCooldown(ability.getCooldown());
		setDuration(ability.getDuration());
		setUpAnimationLocation(ability.getUpAnimationLocation());
		setDownAnimationLocation(ability.getDownAnimationLocation());
		setLeftAnimationLocation(ability.getLeftAnimationLocation());
		setRightAnimationLocation(ability.getRightAnimationLocation());
		loadAnimation();
	}

	public void resetDuration() {
		durationTimer = 0;
	}
	
	public void resetCooldown() {
		cooldownTimer = 0;
	}
	
	public void abilityUsed() {
		durationTimer = duration;
		cooldownTimer = cooldown;
	}
	
	public void tick() {
		if (isActive()) {
			durationTimer -= 1;
		}
		
		if (isCooling()) {
			cooldownTimer -= 1;
		}
	}
	
	public boolean isActive() {
		return durationTimer > 0;
	}
	
	public boolean isCooling() {
		return cooldownTimer > 0;
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
