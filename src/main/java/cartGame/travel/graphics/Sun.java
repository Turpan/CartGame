package cartGame.travel.graphics;

import java.awt.Color;

import movement.Light;
import movement.LightType;

public class Sun extends Light {

	public Sun() {
		super(LightType.DIRECTIONAL, new Color(255, 255, 255, 255));
		
		setDimensions(new double[] {20, 20, 20});
		setPosition(new double[] {1000, 2000, -1000});
		
		setAmbient(0.05);
		setDiffuse(0.7);
		setSpecular(0.7);
	}

}
