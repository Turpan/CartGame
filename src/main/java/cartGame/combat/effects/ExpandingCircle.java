package cartGame.combat.effects;

import movement.Movable;
import movement.Obstacle;
import movement.Shapes.Ellipse;

public class ExpandingCircle extends Attack {
	
	public static final double HEIGHT = 10.0;
	
	private double initialSize;
	
	private double growthRate;
	
	private double maxSize;
	
	public ExpandingCircle(double initialSize) {
		super();
		
		setInitialSize(initialSize);
		
		double[] dimensions = new double[3];
		dimensions[0] = initialSize;
		dimensions[1] = HEIGHT;
		dimensions[2] = maxSize;
		
		Ellipse collisionShape = new Ellipse(dimensions);
		
		setSimpleOutline(collisionShape);
		setDimensions(dimensions.clone());
		setPosition(new double[] {0.0, 0.0, 0.0});
	}
	
	public ExpandingCircle(ExpandingCircle expandingCircle) {
		super(expandingCircle);
		
		setInitialSize(expandingCircle.getInitialSize());
		setGrowthRate(expandingCircle.getGrowthRate());
		setMaxSize(expandingCircle.getMaxSize());
	}

	public double getInitialSize() {
		return initialSize;
	}

	public void setInitialSize(double initialSize) {
		this.initialSize = initialSize;
	}

	public double getGrowthRate() {
		return growthRate;
	}

	public void setGrowthRate(double growthRate) {
		this.growthRate = growthRate;
	}

	public double getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(double maxSize) {
		this.maxSize = maxSize;
	}

	@Override
	public ExpandingCircle clone() {
		return new ExpandingCircle(this);
	}

	@Override
	public void collision(Obstacle o) {
		// TODO Auto-generated method stub

	}

	@Override
	public void collision(Movable m, double[] collisionLocationInThis) {
		// TODO Auto-generated method stub
		
	}

}
