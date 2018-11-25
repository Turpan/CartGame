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
		
		Texture texture = ImageCache.getTexture("graphics/combat/main test/arena/green.png");
		addTexture(texture);
		setActiveTexture(texture);
		setOutline(new BigRectangle(getDimensions()));
	}

	@Override
	public void collision(Movable m) {
		// TODO Auto-generated method stub
		
	}
	

}
