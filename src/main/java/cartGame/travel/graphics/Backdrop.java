package cartGame.travel.graphics;

import amyGraphics.Texture;
import movement.Entity;

public class Backdrop extends Entity {
	
	static final public int WIDTH = 16000;
	static final public double SPEED = 15;
	static final public int DEPTH = 2000;
	
	private double start;
	private int depth;
	
	public Backdrop(Texture texture, double start, double[] dimensions) {
		super();
		
		setPosition(new double[] {start, 0, (DEPTH * (depth + 1))});
		
		addTexture(texture);
		setActiveTexture(texture);
		setDimensions(dimensions);
		setDepth(0);
	}
	
	public Backdrop(Texture texture, double start, double[] dimensions, int depth) {
		this(texture, start, dimensions);
		
		setDepth(depth);
	}
	
	protected Backdrop(Backdrop bd) {
		super(bd);
		setStart(bd.getStart());
		setDepth(bd.getDepth());
	}
	
	@Override
	public Backdrop clone() {
		return new Backdrop(this);
	}
	public void setStart(double start) {
		this.start = start;
	}
	
	public double getStart() {
		return start;
	}
	
	public void setDepth(int depth) {
		double[] pos = getPosition();
		double z = (DEPTH * (depth + 1));
		pos[2] = z;
		
		this.depth = depth;
	}
	
	public int getDepth() {
		return depth;
	}
	
	public void scroll() {
		double[] pos = getPosition();
		double x = pos[0];
		x += (SPEED / (depth + 1));
		
		if (x >= (WIDTH + start)) {
			x = (0 - (WIDTH * (TravelGraphic.BACKDROPCOUNT - 1))) + start;
		}
		
		pos[0] = x;
	}
}
