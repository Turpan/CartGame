package cartGame.travel.cart;

public class Traveller {
	
	private String name = "N/A";
	private int health = 100;
	
	public Traveller() {
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		if (health > 100) health = 100;
		else if (health < -1) health = 0;
		
		this.health = health;
	}
}
