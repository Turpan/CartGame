package cartGame.travel.graphics;

import amyGraphics.Texture;
import movement.Entity;

public class Backdrop extends Entity {
	
	static final public int WIDTH = 16000;
	static final public float SPEED = 15;
	static final public int DEPTH = 2000;
	
	private float start;
	private int depth;
	
	public Backdrop(Texture texture, float start, double[] dimensions) throws MalformedEntityException {
		super();
		
		setPosition(new float[] {start, 0, (DEPTH * (depth + 1))});
		
		addTexture(texture);
		setActiveTexture(texture);
		setDimensions(dimensions);
		setDepth(0);
	}
	
	public Backdrop(Texture texture, float start, double[] dimensions, int depth) throws MalformedEntityException {
		this(texture, start, dimensions);
		
		setDepth(depth);
	}

	public void setStart(float start) {
		this.start = start;
	}
	
	public float getStart() {
		return start;
	}
	
	public void setDepth(int depth) throws MalformedEntityException {
		float[] pos = getPosition();
		float z = (DEPTH * (depth + 1));
		pos[2] = z;
		
		this.depth = depth;
	}
	
	public int getDepth() {
		return depth;
	}
	
	public void scroll() throws MalformedEntityException {
		float[] pos = getPosition();
		float x = pos[0];
		x += (SPEED / (depth + 1));
		
		if (x >= (WIDTH + start)) {
			x = (0 - (WIDTH * (TravelGraphic.BACKDROPCOUNT - 1))) + start;
		}
		
		pos[0] = x;
	}
}
