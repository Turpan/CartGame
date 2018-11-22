package tests;

import amyGraphics.Texture;
import cartGame.io.ImageCache;
import movement.Movable;
import movement.Obstacle;
import movement.Shapes.BigRectangle;

public class TestFloor extends Obstacle {

	public TestFloor() {
		setCoR(1);
		
		setDimensions(new double[] {8000, 200, 2000});
		setPosition(new double[] {-4000, -200, -1000});
		
		Texture texture = new Texture(ImageCache.getImage("graphics/combat/main test/arena/green.png"));
		addTexture(texture);
		setActiveTexture(texture);
		setOutline(new BigRectangle(getDimensions()));
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
		// TODO Auto-generated method stub
		
	}
	

}
