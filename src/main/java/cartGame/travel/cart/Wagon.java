package cartGame.travel.cart;

import cartGame.travel.towns.Town;

public class Wagon {
	int speed;
	
	int position;
	
	Town current;
	
	Town destination;
	
	public Wagon() {
		
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public void setPosition(int position) {
		this.position = position;
	}
	
	public void addPosition(int toAdd) {
		position += toAdd;
	}
	
	public int getPosition() {
		return position;
	}
	
	public void setCurrentTown(Town town) {
		this.current = town;
	}
	
	public Town getCurrentTown() {
		return current;
	}
	
	public void setDestinationTown(Town town) {
		this.destination  = town;
	}
	
	public Town getDestinationTown() {
		return destination;
	}
	
	public boolean isTravelling() {
		return (destination != null);
	}
}
