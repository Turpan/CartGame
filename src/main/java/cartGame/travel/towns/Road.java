package cartGame.travel.towns;

import java.util.LinkedHashMap;
import java.util.Map;

public class Road {
	
	String ID;
	
	int distance;
	
	Map<String, Double> biomes = new LinkedHashMap<String, Double>();
	
	public Road() {
		
	}
	
	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}
	
	public int getDistance() {
		return distance;
	}
	
	public void addBiome(String biomeID, double distance) {
		biomes.put(biomeID, distance);
	}
	
	public void removeBiome(String biomeID) {
		biomes.remove(biomeID);
	}

}
