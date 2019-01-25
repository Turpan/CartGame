package cartGame.travel.graphics;

import java.awt.Color;

import movement.Light;
import movement.LightType;

public class Sun extends Light {
	
	private static final double DIFFUSE = 1.2;
	private static final double SPECULAR = 1.2;
	
	public Sun() {
		super(LightType.DIRECTIONAL, new Color(255, 255, 255, 255));
		
		setDimensions(new double[] {0, 0, 0});
		setPosition(new double[] {4000, 0, 0});
		
		setAmbient(0.05);
		setDiffuse(DIFFUSE);
		setSpecular(SPECULAR);
	}
	
	protected Sun(Sun sun) {
		super(sun);
	}
	
	@Override
	public Sun clone() {
		return new Sun(this);
	}
	
	public void setDay(double sunlight) {
		if (sunlight < 0) {
			sunlight = 0;
		} else if (sunlight > 1) {
			sunlight = 1;
		}
		
		double red = 255 * (sunlight * 4);
		double gb = 255 * sunlight;
		
		if (red > 255) red = 255;
		
		setColor(new Color((int) red, (int) gb, (int) gb));
		setDiffuse(DIFFUSE * sunlight);
		setSpecular(SPECULAR * sunlight);
	}
}
