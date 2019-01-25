package cartGame.travel.graphics;

import java.awt.Color;

import movement.Light;
import movement.LightType;

public class Lantern extends Light {

	private static final double DIFFUSE = 0.4;
	private static final double SPECULAR = 0.4;
	
	public Lantern() {
		super(LightType.POINT, new Color(255, 69, 0, 255));
		
		setDimensions(new double[] {0, 0, 0});
		setPosition(new double[] {400, 200, -20});
		
		setAmbient(0.0);
		setDiffuse(DIFFUSE);
		setSpecular(SPECULAR);
	}
	
	protected Lantern(Lantern lantern) {
		super(lantern);
	}
	
	@Override
	public Lantern clone() {
		return new Lantern(this);
	}
	
	public void setNight(boolean shining) {
		if (shining) {
			setDiffuse(DIFFUSE);
			setSpecular(SPECULAR);
		} else {
			setDiffuse(0);
			setSpecular(0);
		}
	}
}
