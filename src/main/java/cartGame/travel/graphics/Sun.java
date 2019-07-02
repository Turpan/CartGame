package cartGame.travel.graphics;

import java.awt.Color;

import movement.Light;
import movement.LightType;

public class Sun extends Light {
	
	private static final double DIFFUSE = 1.2;
	private static final double SPECULAR = 1.2;
	
	private int dayLength = 1;
	
	public Sun() {
		super(LightType.DIRECTIONAL, new Color(255, 255, 255, 255));
		
		setDimensions(new double[] {500, 500, 500});
		setPosition(new double[] {2000, 0, (Backdrop.DEPTH * Environment.BACKDROPCOUNT)});
		
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
	
	/*public void moveSun(int hour, int minute) {
		double sunShine = 1.0;
		
		if (hour <= TravelGraphic.SUNRISE + 1) {
			sunShine = (hour + ((double) minute / 60.0) - (TravelGraphic.SUNRISE - 1)) / 2;
		} else if (hour >= TravelGraphic.SUNSET - 2) {
			sunShine = (TravelGraphic.SUNSET - hour + (1 - ((double) minute / 60.0))) / 3;
		}
		setDay(sunShine);
		
		if (hour < TravelGraphic.SUNRISE || hour > TravelGraphic.SUNSET) {
			return;
		}
		
		hour -= TravelGraphic.SUNRISE;
		final double steplength = TravelGraphic.SUNARCWIDTH / (TravelGraphic.SUNSET - TravelGraphic.SUNRISE);
		
		final double Px = TravelGraphic.SUNPEAKX;
		final double Py = TravelGraphic.SUNPEAKY;
		final double Cx = TravelGraphic.SUNSTARTX;
		final double Cy = TravelGraphic.SUNSTARTY;
		
		final double b = (Cy + Py) / (Cx * (1 - (1 / (2 * Px))));
		final double a = -b / (2 * Px);
		final double c = (b * Px) - Py;
		
		final double x = (hour * steplength) + (((double) minute / 60.0) * steplength) + Cx;
		final double y = (a * Math.pow(x, 2)) + (b * x) + c;
		
		getPosition()[0] = x;
		getPosition()[1] = y;
	}*/
	
	public int getDayLength() {
		return dayLength;
	}

	public void setDayLength(int dayLength) {
		if (dayLength < 1) dayLength = 1;
		this.dayLength = dayLength;
	}

	protected void moveSun(int hour, int minute) {
		double degreeStep = 360 / dayLength;
		
		double degreePosition = TravelGraphic.SUNSTARTDEGREES;
		double degreeOffset = hour + ((double) minute / 60.0);
		
		degreePosition += (degreeOffset - TravelGraphic.SUNRISE) * degreeStep;
		
		double z = TravelGraphic.SUNROTATIONRADIUS * Math.cos(Math.toRadians(degreePosition));
		double y = TravelGraphic.SUNROTATIONRADIUS * Math.sin(Math.toRadians(degreePosition));
		
		double[] sunPosition = getPosition();
		sunPosition[1] = y;
		sunPosition[2] = z;
		
		double sunShine = 1.0;
		
		if (hour <= TravelGraphic.SUNRISE + 1) {
			sunShine = (hour + ((double) minute / 60.0) - (TravelGraphic.SUNRISE - 1)) / 2;
		} else if (hour >= TravelGraphic.SUNSET - 2) {
			sunShine = (TravelGraphic.SUNSET - hour + (1 - ((double) minute / 60.0))) / 3;
		}
		setDay(sunShine);
	}
	
	private void setDay(double sunlight) {
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
