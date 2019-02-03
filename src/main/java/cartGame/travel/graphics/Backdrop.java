package cartGame.travel.graphics;

import amyGraphics.Texture;
import movement.Entity;

public class Backdrop extends Entity {
	
	static final public int WIDTH = 8000;
	static final public double SPEED = 5;
	static final public int HEIGHTOFFSET = 150;
	static final public int DEPTH = 800;
	
	private double start;
	private int depth;
	
	private boolean active;
	
	private Direction direction = Direction.LEFT;
	
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
		
		setPosition(new double[] {start, 0, (DEPTH * (depth + 1))});
		
		setStart(bd.getStart());
		setDepth(bd.getDepth());
	}
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
		
		if (active) {
			setVisible(active);
		}
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
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
		double y = (HEIGHTOFFSET * (depth));
		double z = (DEPTH * (depth + 1));
		
		pos[1] = -y;
		pos[2] = z;
		
		this.depth = depth;
	}
	
	public int getDepth() {
		return depth;
	}
	
	public void scroll() {
		double[] pos = getPosition();
		double x = pos[0];
		switch(direction) {
		case LEFT:
			x -= getSpeed(depth);
			if (x <= (0 - (WIDTH * (Environment.BACKDROPCOUNT - 1))) + start) {
				x = WIDTH + start;
				
				if (!active) {
					setVisible(false);
				}
			}
			break;
		case RIGHT:
			x += getSpeed(depth);
			if (x >= (WIDTH + start)) {
				x = (0 - (WIDTH * (Environment.BACKDROPCOUNT - 1))) + start;
				
				if (!active) {
					setVisible(false);
				}
			}
			break;
		}
		
		/*x -= (SPEED / (depth + 1));
		if (x <= 0 - (WIDTH + start)) {
			x = ((WIDTH * (TravelGraphic.BACKDROPCOUNT - 1))) + start;
		}
		break;*/
		
		pos[0] = x;
	}
	
	public static double getSpeed(int depth) {
		int speedFactor = Environment.BACKDROPCOUNT - depth;
		return (SPEED * speedFactor);
	}
	
	public enum Direction {
		LEFT,
		RIGHT;
	}
}
