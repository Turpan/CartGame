package tests;

import amyGraphics.Texture;
import cartGame.io.ImageCache;
import movement.Movable;
import movement.Obstacle;
import movement.Shapes.Rectangle;

public class TestWall extends Obstacle {
	
	private static final double[][] dimensions = 
		{{200, 1000, 2000},
		{7600, 1000, 200},
		{200, 1000, 2000},
		{7600, 1000, 200}};
	
	private static final double[][] positions =
		{{-4000, 0, -1000},
		{-3800, 0, 800},
		{3800, 0, -1000},
		{-3800, 0, -1000}};
	
	public TestWall(int wall) {
		if (wall > 3) wall = 3;
		if (wall < 0) wall = 0;
		setCoR(1);
		
		setDimensions(dimensions[wall]);
		setPosition(positions[wall]);
		setOutline(new Rectangle(getDimensions()));
		

		setRotationAxis(new double[] {0,1,0});
		setAngle(0);
		setCentreOfRotation(new double[] {500,500,100});
		
		Texture texture;
		if (wall != 3) {
			texture =  ImageCache.getTexture("graphics/combat/main test/arena/green.png");
		} else {
			texture = ImageCache.getTexture("graphics/editor/blank.png");
		}
		addTexture(texture);
		setActiveTexture(texture);
	}
	protected TestWall(TestWall tw) {
		super(tw);
	}
	
	@Override
	public TestWall clone() {
		return new TestWall(this);
	}
	@Override
	public void collision(Movable m) {
		// TODO Auto-generated method stub
		
	}
}
