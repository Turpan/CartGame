package cartGame.travel.graphics;

import java.util.ArrayList;
import java.util.List;

import amyGraphics.Texture;
import amyGraphics.TexturePosition;

public class Environment {
	
	static final public int BACKDROPCOUNT = 3;
	static final public int MAXDEPTH = BACKDROPCOUNT - 1;
	
	private List<List<Backdrop>> layers;
	private List<Backdrop> floor;
	
	public Environment() {
		layers = new ArrayList<List<Backdrop>>();
		for (int i=0; i<BACKDROPCOUNT; i++) {
			List<Backdrop> layer = new ArrayList<Backdrop>();
			layers.add(layer);
		}
		
		floor = new ArrayList<Backdrop>();
	}
	
	public List<Backdrop> getBackdrops() {
		List<Backdrop> backdrops = new ArrayList<Backdrop>();
		for (List<Backdrop> layer : layers) {
			for (Backdrop backdrop : layer) {
				backdrops.add(backdrop);
			}
		}
		
		return backdrops;
	}
	
	public List<Backdrop> getFloor() {
		return floor;
	}
	
	public void reset() {
		for (int i=0; i<BACKDROPCOUNT; i++) {
			for (Backdrop backdrop : layers.get(i)) {
				double[] pos = backdrop.getPosition();
				pos[0] = (0 - (Backdrop.WIDTH * i)) + backdrop.getStart();
				backdrop.setActive(false);
				backdrop.setVisible(false);
			}
			
			Backdrop ground = floor.get(i);
			double[] pos = ground.getPosition();
			pos[0] = (0 - (Backdrop.WIDTH * i)) + ground.getStart();
			ground.setActive(false);
			ground.setVisible(false);
		}
	}
	
	public void setFloorActive(int floor, boolean active) {
		if (floor < 0) {
			floor = 0;
		} else if (floor >= BACKDROPCOUNT) {
			floor = BACKDROPCOUNT - 1;
		}
		
		this.floor.get(floor).setActive(active);
	}
	
	public void setLayerActive(int layer, boolean active) {
		if (layer < 0) {
			layer = 0;
		} else if (layer >= BACKDROPCOUNT) {
			layer = BACKDROPCOUNT - 1;
		}
		
		for (Backdrop backdrop : layers.get(layer)) {
			backdrop.setActive(active);
		}
	}
	
	public void setLayerActive(int layer, int depth, boolean active) {
		if (layer < 0) {
			layer = 0;
		} else if (layer >= BACKDROPCOUNT) {
			layer = BACKDROPCOUNT - 1;
		}
		
		for (Backdrop backdrop : layers.get(layer)) {
			if (backdrop.getDepth() == depth) {
				backdrop.setActive(active);
			}
		}
	}
	
	public void addBackdrop(Texture texture, float start, double[] dimensions) {
		addBackdrop(texture, start, dimensions, 0);
	}
	
	public void addBackdrop(Texture texture, float start, double[] dimensions, int depth) {
		if (depth > MAXDEPTH) {
			depth = MAXDEPTH;
		}
		
		for (int i=0; i<BACKDROPCOUNT; i++) {
			Backdrop backdrop = new Backdrop(texture, start, dimensions, depth);
			double[] pos = backdrop.getPosition();
			pos[0] += (0 - (Backdrop.WIDTH * i)) + start;
			layers.get(i).add(backdrop);
		}
	}
	
	public void setGround(Texture texture, float start, double[] dimensions) {	
		for (int i=0; i<BACKDROPCOUNT; i++) {
			Backdrop backdrop = new Backdrop(texture, start, dimensions, 0);
			backdrop.setTexturePosition(TexturePosition.TOP);
			double[] pos = backdrop.getPosition();
			pos[0] += (0 - (Backdrop.WIDTH * i)) + start;
			pos[2] = Cart.ZPOS;
			floor.add(backdrop);
		}
	}
}
