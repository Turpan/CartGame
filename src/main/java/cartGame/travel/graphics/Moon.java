package cartGame.travel.graphics;

import java.awt.Color;

import movement.Light;
import movement.LightType;

public class Moon extends Light {

	private static final double DIFFUSE = 0.2;
	private static final double SPECULAR = 0.2;
	
	private boolean shining;
	
	public Moon() {
		super(LightType.POINT, new Color(200, 200, 255, 255));
		
		setDimensions(new double[] {20, 20, 20});
		setPosition(new double[] {0, 4000, -4000});
		
		setAmbient(DIFFUSE);
		//setDiffuse(DIFFUSE);
		//setSpecular(SPECULAR);
	}
	protected Moon(Moon moon) {
		super(moon);
	}
	
	public void setNight(boolean shining) {
		this.shining = shining;
		
		toggleNight();
	}
	
	public void toggleNight() {
		if (shining) {
			setAmbient(DIFFUSE);
			//setDiffuse(DIFFUSE);
			//setSpecular(SPECULAR);
		} else {
			setAmbient(0);
			setDiffuse(0);
			setSpecular(0);
		}
	}
	
	@Override
	public Moon clone() {
		return new Moon(this);
	}

}
