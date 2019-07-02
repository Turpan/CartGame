package cartGame.combat.player;

public interface Alive {
	public int getHealth();
	public void setHealth(int health);
	public int getMaxHealth();
	public void setMaxHealth(int maxHealth);
	
	
	public void damage(int damage);	//also heal, when negatived.
	
	public boolean dead();
}
