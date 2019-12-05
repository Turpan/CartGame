package tests.towns;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import cartGame.travel.graphics.Environment;
import tests.DeepForestPaper;
import tests.LightForestPaper;

public class TestEnvironmentPool extends LinkedHashMap<String, List<Environment>> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public TestEnvironmentPool() {
		super();
		
		List<Environment> deepforest = new ArrayList<Environment>();
		List<Environment> lightforest = new ArrayList<Environment>();
		
		deepforest.add(new DeepForestPaper());
		lightforest.add(new LightForestPaper());
		
		put("deep_forest", deepforest);
		put("light_forest", lightforest);
	}
}
