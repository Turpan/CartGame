package cartGame.tools.editor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import cartGame.travel.towns.Town;
import cartGame.travel.towns.WorldMap;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

public class MapEditor extends Canvas {
	
	public static final int WIDTH = 640;
	public static final int HEIGHT = 360;
	
	private WorldMap map;
	
	private Image background;
	
	private List<MapGraphic> graphics;
	private MapGraphic selected;
	
	private int mouseStartX;
	private int mouseStartY;
	
	private int graphicStartX;
	private int graphicStartY;
	
	public MapEditor(WorldMap map) {
		super();
		
		setWidth(WIDTH);
		setHeight(HEIGHT);
		
		this.map = map;
		
		graphics = new ArrayList<MapGraphic>();
		
		try {
			background = new Image(new FileInputStream(new File("graphics/ui/map/map.png")), WIDTH, HEIGHT, false, true);
		} catch (FileNotFoundException e) {
			background = null;
		}
		
		setOnMousePressed((MouseEvent event) -> {
			mouseClick((int) event.getSceneX(), (int) event.getSceneY());
		});
		setOnMouseDragged((MouseEvent event) -> {
			mouseDrag((int) event.getSceneX(), (int) event.getSceneY());
		});
	}
	
	private void mouseDrag(int x, int y) {
		if (selected == null) {
			return;
		}
		
		int movedX = x - mouseStartX;
		int movedY = y - mouseStartY;
		
		selected.setX(graphicStartX + movedX);
		selected.setY(graphicStartY + movedY);
		
		drawMap();
	}
	
	private void mouseClick(int x, int y) {
		selected = null;
		mouseStartX = x;
		mouseStartY = y;
		
		for (MapGraphic graphic : graphics) {
			if (x > graphic.getX() && y > graphic.getY()
					&& x < graphic.getX() + MapGraphic.WIDTH && y < graphic.getY() + MapGraphic.HEIGHT) {
				selected = graphic;
				graphicStartX = graphic.getX();
				graphicStartY = graphic.getY();
				break;
			}
		}
		
		drawMap();
	}
	
	public void finalise() {
		for (MapGraphic graphic : graphics) {
			graphic.finalise();
		}
	}
	
	public void generateMap() {
		graphics.clear();
		selected = null;
		
		for (Town town : map.getTowns()) {
			MapGraphic graphic = new MapGraphic(town);
			graphics.add(graphic);
		}
		
		drawMap();
	}
	
	private void drawMap() {
		GraphicsContext gc = getGraphicsContext2D();
		gc.clearRect(0, 0, getWidth(), getHeight());
		gc.drawImage(background, 0, 0);
		
		for (MapGraphic graphic : graphics) {
			gc.drawImage(graphic.getIcon(), graphic.getX(), graphic.getY());
			
			if (graphic == selected) {
				gc.strokeRect(graphic.getX(), graphic.getY(), MapGraphic.WIDTH, MapGraphic.HEIGHT);
			}
			
			for (MapGraphic connection : graphics) {
				if (graphic.getTown() == connection.getTown()) {
					continue;
				}
				
				if (map.getMap().getVertexConnections(graphic.getTown()).contains(connection.getTown())) {
					gc.strokeLine(graphic.getX() + MapGraphic.WIDTH/2, graphic.getY() + MapGraphic.HEIGHT/2
							, connection.getX() + MapGraphic.WIDTH/2, connection.getY() + MapGraphic.HEIGHT/2);
				}
			}
		}
	}
	
}
