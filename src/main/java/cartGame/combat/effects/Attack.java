package cartGame.combat.effects;

import movement.Movable;

public abstract class Attack extends Movable {

	private String ID;
	
	private String textureLocation;

	private int faction;
	private int damage;
	private int hitLimit;
	
	public Attack() {
		super();
	}
	
	public Attack(Attack attack) {
		super(attack);
		
		setID(attack.getID());
		setFaction(attack.getFaction());
		setDamage(attack.getDamage());
		setHitLimit(attack.getHitLimit());
	}
	
	public abstract Attack clone();
	
	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}
	
	public String getTextureLocation() {
		return textureLocation;
	}

	public void setTextureLocation(String textureLocation) {
		this.textureLocation = textureLocation;
	}

	public int getFaction() {
		return faction;
	}

	public void setFaction(int faction) {
		this.faction = faction;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getHitLimit() {
		return hitLimit;
	}

	public void setHitLimit(int hitLimit) {
		this.hitLimit = hitLimit;
	}

}
