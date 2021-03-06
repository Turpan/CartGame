package tests;

import amyGraphics.Texture;
import cartGame.io.ImageCache;
import movement.Movable;
import movement.Obstacle;
import movement.Shapes.Rectangle;

public class TestFloor extends Obstacle {

	public TestFloor() {
		setCoR(1);
		
		setDimensions(new double[] {8000, 200, 2000});
		setPosition(new double[] {-4000, -200, -1000});
		
		Texture texture = ImageCache.getTexture("graphics/combat/main test/arena/yellow.png");
		addTexture(texture);
		setActiveTexture(texture);
		setOutline(new Rectangle(getDimensions()));
		
		setRotationAxis(new double[] {0,1,0});
		setAngle(0);
		setCentreOfRotation(new double[] {500,500,100});
	}
	protected TestFloor(TestFloor tf) {
		super(tf);
	}
	
	@Override
	public TestFloor clone() {
		return new TestFloor(this);
	}
	@Override
	public void collision(Movable m) {
		m.touchFloor();		
	}
}
