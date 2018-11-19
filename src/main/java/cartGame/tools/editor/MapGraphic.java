package cartGame.tools.editor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import cartGame.travel.towns.Town;
import javafx.scene.image.Image;

public class MapGraphic {
	
	public static final int WIDTH = 40;
	public static final int HEIGHT = 40;
	
	private Town town;
	
	private Image icon;
	
	private int x;
	private int y;
	
	public MapGraphic(Town town) {
		this.town = town;
		try {
			this.icon = new Image(new FileInputStream(new File(town.getIconLocation())), WIDTH, HEIGHT, false, true);
		} catch (FileNotFoundException e) {
			this.icon = null;
		}
		setX(town.getMapX());
		setY(town.getMapY());
	}
	
	public void finalise() {
		town.setMapX(getX());
		town.setMapY(getY());
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Town getTown() {
		return town;
	}

	public Image getIcon() {
		return icon;
	}
}
