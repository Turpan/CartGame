package cartGame.tools.editor;

import java.io.File;
import java.io.IOException;
import java.util.List;

import cartGame.io.RoadIO;
import cartGame.io.TownIO;
import cartGame.travel.towns.Road;
import cartGame.travel.towns.WorldMap;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;

public class EditorGUI extends TabPane{
	
	private static final String townData = "TOWN.DAT";
	private static final String roadData = "ROAD.DAT";
	private static final String shopData = "SHOP.DAT";
	private static final String itemData = "ITEM.DAT";

	private TownPane town;
	private RoadPane road;
	private ShopPane shop;
	private ItemPane item;
	
	private ImageBrowser imageBrowser;
	
	public EditorGUI() {
		super();
		
		setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		
		createComponents();
	}
	
	private void createComponents() {
		imageBrowser = new ImageBrowser();
		imageBrowser.createInterface("graphics");
		
		road = new RoadPane();
		town = new TownPane(imageBrowser, road.getRoads());
		shop = new ShopPane();
		item = new ItemPane();
		
		getTabs().addAll(town, road, shop, item);
	}
	
	public void saveData(String dataLocation) {
		File directory = new File(dataLocation);
		if (!directory.exists()) {
			directory.mkdirs();
		}
		
		File townFile = new File(dataLocation + "/" + townData);
		File roadFile = new File(dataLocation + "/" + roadData);
		
		WorldMap map = town.getMap();
		
		List<Road> roadList = road.getRoads();
		
		try {
			TownIO.writeTownData(map, townFile.getAbsolutePath());
			RoadIO.writeRoadData(roadList, roadFile.getAbsolutePath());
		} catch (IOException e) {
			showAlert("Save Failed", "File failed to save." + System.lineSeparator() +
					"Please check directory is accessible and try again.");
		}
	}
	
	public void loadData(String dataLocation) {
		File townFile = new File(dataLocation + "/" + townData);
		File roadFile = new File(dataLocation + "/" + roadData);
		
		if (!townFile.exists() ||
				!roadFile.exists()) {
			showAlert("Load Failed", "File failed to load." + System.lineSeparator() +
					"Please check if file exists and has read permissions Enabled.");
			return;
		}
		
		List<Road> roadList = RoadIO.readRoadData(roadFile.getAbsolutePath());
		
		WorldMap map = TownIO.readTownData(townFile.getAbsolutePath(), roadList);
		
		road.addRoad(roadList);
		town.mapLoaded(map);
	}
	
	private void showAlert(String title, String body) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(body);
		alert.showAndWait();
	}
	
}
