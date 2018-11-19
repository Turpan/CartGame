package cartGame.tools.editor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import cartGame.travel.towns.Road;
import cartGame.travel.towns.Town;
import cartGame.travel.towns.WorldMap;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class TownPane extends Tab implements EditorListener {
	
	private ImageBrowser imageBrowser;
	
	private ListView<Town> townList;
	private ObservableList<Town> towns;
	private Map<String, Image> images;
	
	private WorldMap worldMap;
	
	private TownEditor townEditor;
	
	private ContextMenu townMenu;
	private MenuItem newTown;
	private MenuItem removeTown;
	
	public TownPane(ImageBrowser imageBrowser, ObservableList<Road> roads) {
		super();
		
		this.imageBrowser = imageBrowser;
		
		setText("Towns");
		
		createComponents(roads);
	}
	
	public TownPane(ImageBrowser imageBrowser, ObservableList<Road> roads, List<Town> towns) {
		this(imageBrowser, roads);
		
		addTown(towns);
	}
	
	public void createTown() {
		Town town = new Town();
		town.setID("new_town");
		town.setName("New Town");
		town.setIconLocation("graphics/editor/town.png");
		
		addTown(town);
	}
	
	public void editTown(int index) {
		/*Town town = towns.get(index);
		townEditor.showTownInfo(town, worldMap);
		
		Alert orderBox = new Alert(AlertType.CONFIRMATION);
		orderBox.setTitle("Town Editor");
		orderBox.setHeaderText(null);
		orderBox.setGraphic(null);
		orderBox.getDialogPane().setContent(townEditor);
		
		Optional<ButtonType> result = orderBox.showAndWait();
		if (result.get() == ButtonType.OK) {
			townEditor.finalize(town, worldMap);
		}*/
	}
	
	public WorldMap getMap() {
		return worldMap;
	}
	
	public void removeTown(int index) {
		Town town = towns.get(index);
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirm Removal");
		alert.setHeaderText(null);
		alert.setGraphic(null);
		alert.setContentText("Are you sure you wish to delete the town '" + town.getName() + "' ?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.CANCEL) {
			return;
		}
		
		worldMap.removeTown(town);
		towns.remove(index);
		boolean shouldRemove = true;
		for (Town toCheck : towns) {
			if (toCheck.getName().equals(town.getName())) {
				shouldRemove = false;
				break;
			}
		}
		if (shouldRemove) {
			images.remove(town.getName());
		}
	}
	
	public void mapLoaded(WorldMap map) {
		addTown(map.getMap().getUnsortedVerticesList());
		
		for (Town town : map.getMap().getUnsortedVerticesList()) {
			for (Town connection : map.getMap().getVertexConnections(town)) {
				Road road = map.getMap().getEdgeValue(town, connection);
				worldMap.addConnection(town, connection, road);
			}
		}
	}
	
	public void addTown(List<Town> towns) {
		for (Town town : towns) {
			addTown(town);
		}
	}
	
	public void addTown(Town town) {
		try {
			images.put(town.getName(), new Image(new FileInputStream(town.getIconLocation())));
		} catch (FileNotFoundException e) {
			images.put(town.getName(), null);
		}
		towns.add(town);
		
		worldMap.addTown(town);
	}
	
	@Override
	public void update() {
		images.clear();

		int index = townList.getSelectionModel().getSelectedIndex(); 
		
		for (Town town : towns) {
			try {
				images.put(town.getName(), new Image(new FileInputStream(new File(town.getIconLocation()))));
			} catch (FileNotFoundException e) {
				images.put(town.getName(), null);
			}
		}
		
		townList.refresh();
		townList.getSelectionModel().select(index);
	}
	
	private void updateContextMenu(int index) {
		if (index >= 0) {
			removeTown.setDisable(false);
		} else {
			removeTown.setDisable(true);
		}
	}
	
	private void createComponents(ObservableList<Road> roads) {
		towns = FXCollections.observableArrayList();
		images = new HashMap<String, Image>();
		townList = new ListView<Town>(towns);
		worldMap = new WorldMap();
		
		townEditor = new TownEditor(worldMap, imageBrowser, towns, roads);
		townEditor.addListener(this);
		
		townList.setCellFactory(new Callback<ListView<Town>,
				ListCell<Town>>() {
            @Override 
            public ListCell<Town> call(ListView<Town> list) {
                return new Thumb(images);
            }
        });
		townList.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Town> observable,
				Town oldValue, Town newValue) -> {
			updateContextMenu(townList.getSelectionModel().getSelectedIndex());
			townEditor.showTownInfo(townList.getSelectionModel().getSelectedItem());
		});
		
		townMenu = new ContextMenu();
		newTown = new MenuItem("New Town");
		newTown.setOnAction((ActionEvent e) -> {
			createTown();
		});
		removeTown = new MenuItem("Remove Town");
		removeTown.setOnAction((ActionEvent e) -> {
			removeTown(townList.getSelectionModel().getSelectedIndex());
		});
		
		townMenu.getItems().addAll(newTown, removeTown);
		
		townList.setOnContextMenuRequested((ContextMenuEvent event) -> {
			townMenu.show(townList, event.getScreenX(), event.getScreenY());
		});
		
		VBox main = new VBox();
		
		HBox container = new HBox();
		
		VBox.setVgrow(container, Priority.ALWAYS);
		
		main.getChildren().add(container);
		container.getChildren().add(townList);
		container.getChildren().add(townEditor);
		setContent(main);
		
		updateContextMenu(-1);
	}
	
	static private class Thumb extends ListCell<Town> {
		private ImageView imageView = new ImageView();
		private Map<String, Image> images;
		
		public Thumb(Map<String, Image> images) {
			super();
			this.images = images;
		}
		
		@Override
		public void updateItem(Town item, boolean empty) {
			super.updateItem(item, empty);
			if (empty || item == null) {
				imageView.setImage(null);
				
				setGraphic(null);
				setText(null);
			} else {
				imageView.setImage(images.get(item.getName()));
				imageView.setFitWidth(20);
				imageView.setFitHeight(20);
				
				setGraphic(imageView);
				setText(item.getName());
			}
		}
	}
}
