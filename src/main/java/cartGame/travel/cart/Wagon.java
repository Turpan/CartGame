package cartGame.travel.cart;

import cartGame.travel.towns.Town;

public class Wagon {
	double speed = 10;
	
	double position;
	
	Town current;
	
	Town destination;
	
	public Wagon() {
		
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public double getSpeed() {
		return speed;
	}
	
	public void setPosition(double position) {
		this.position = position;
	}
	
	public void addPosition(double toAdd) {
		position += toAdd;
	}
	
	public double getPosition() {
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
