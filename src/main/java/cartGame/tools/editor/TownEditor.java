package cartGame.tools.editor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import cartGame.travel.towns.Road;
import cartGame.travel.towns.Town;
import cartGame.travel.towns.WorldMap;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class TownEditor extends GridPane {
	
	private Town town;
	
	private MapEditor mapEditor;
	
	private ImageBrowser imageBrowser;
	
	private ObservableList<Town> towns;
	private Map<Town, Road> connections;
	private ListView<Town> connectionView;
	
	private TextField idField;
	private TextField nameField;
	private TextField iconField;
	private TextField xField;
	private TextField yField;
	
	private ContextMenu connectionMenu;
	private MenuItem newConnection;
	private MenuItem editTown;
	private MenuItem editRoad;
	private MenuItem removeConnection;
	
	private Image blankImage;
	
	private ImageView iconView;
	
	private ObservableList<Town> availableTowns;
	private ObservableList<Road> availableRoads;
	
	private List<EditorListener> listeners;
	
	private WorldMap map;
	
	public TownEditor(WorldMap map, ImageBrowser imageBrowser,
			ObservableList<Town> availableTowns, ObservableList<Road> availableRoads) {
		super();
		
		this.map = map;
		
		this.imageBrowser = imageBrowser;
		
		this.availableTowns = availableTowns;
		this.availableRoads = availableRoads;
		
		availableTowns.addListener((Change<? extends Town> c) -> {
			while(c.next()) {
				if (c.wasRemoved()) {
					List<? extends Town> removed = c.getRemoved();
					for (Town town : removed) {
						townRemoved(town);
					}
				}
			}
		});
		availableRoads.addListener((Change<? extends Road> c) -> {
			while(c.next()) {
				if (c.wasRemoved()) {
					List<? extends Road> removed = c.getRemoved();
					for (Road road : removed) {
						roadRemoved(road);
					}
				}
			}
		});
		
		listeners = new ArrayList<EditorListener>();
		
		createComponents();
	}
	
	public void showTownInfo(Town town) {
		this.town = town;
		
		if (town == null) {
			setBlankInfo();
			return;
		}
		
		idField.setText(town.getID());
		nameField.setText(town.getName());
		iconField.setText(town.getIconLocation());
		xField.setText(Integer.toString(town.getMapX()));
		yField.setText(Integer.toString(town.getMapY()));
		
		updateIcon(new File(town.getIconLocation()));
		
		towns.clear();
		this.connections.clear();
		
		LinkedList<Town> linkedTowns = map.getMap().getVertexLinkedList(town);
		List<Town> connections = new ArrayList<Town>();
		connections.addAll(linkedTowns);
		
		if (connections.size() == 0) {
			return;
		}
		
		connections.remove(0);
		
		for (Town connection : connections) {
			Road road = map.getMap().getEdgeValue(this.town, connection);
			addConnection(connection, road);
		}
	}
	
	private void setBlankInfo() {
		idField.setText("");
		nameField.setText("");
		iconField.setText("");
		xField.setText(Integer.toString(0));
		yField.setText(Integer.toString(0));
		
		iconView.setImage(blankImage);
	}
	
	public void save() {
		town.setID(idField.getText());
		town.setName(nameField.getText());
		town.setIconLocation(iconField.getText());
		
		for (Town town : towns) {
			Road road = connections.get(town);
			if (!map.getMap().isConnected(this.town, town)) {
				map.addConnection(this.town, town, road);
			}
		}
		
		for (EditorListener listener : listeners) listener.update();
	}
	
	public void addListener(EditorListener listener) {
		listeners.add(listener);
	}
	
	public void createConnection() {
		HBox main = new HBox();
		ListView<Town> townBrowser = createTownBrowser();
		ListView<Road> roadBrowser = createRoadBrowser();
		
		main.getChildren().addAll(townBrowser, roadBrowser);
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Select Connection");
		alert.setHeaderText(null);
		alert.setContentText(null);
		alert.setGraphic(null);
		alert.getDialogPane().setContent(main);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.CANCEL) {
			return;
		}
		
		Town town;
		try {
			town = availableTowns.get(townBrowser.getSelectionModel().getSelectedIndex());
		} catch (IndexOutOfBoundsException e) {
			town = null;
		}
		
		Road road;
		try {
			road = availableRoads.get(roadBrowser.getSelectionModel().getSelectedIndex());
		} catch (IndexOutOfBoundsException e) {
			road = null;
		}
		
		if (road == null || town == null) {
			return;
		}
		
		addConnection(town, road);
	}
	
	public void addConnection(Town town, Road road) {
		if (town == null || road == null) {
			return;
		}
		
		towns.add(town);
		connections.put(town, road);
	}
	
	public void editConnectionTown(int index) {
		ListView<Town> browser = createTownBrowser();
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Select Town");
		alert.setHeaderText(null);
		alert.setContentText(null);
		alert.setGraphic(null);
		alert.getDialogPane().setContent(browser);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.CANCEL) {
			return;
		}
		
		Town town;
		try {
			town = availableTowns.get(browser.getSelectionModel().getSelectedIndex());
		} catch (IndexOutOfBoundsException e) {
			town = null;
		}
		
		if (town == null) {
			return;
		}
		
		Town toReplace = towns.set(index, town);
		Road road = connections.get(toReplace);
		connections.remove(toReplace);
		connections.put(town, road);
		
		update();
	}
	
	public void editConnectionRoad(int index) {
		ListView<Road> browser = createRoadBrowser();
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Select Road");
		alert.setHeaderText(null);
		alert.setContentText(null);
		alert.setGraphic(null);
		alert.getDialogPane().setContent(browser);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.CANCEL) {
			return;
		}
		
		Road road;
		try {
			road = availableRoads.get(browser.getSelectionModel().getSelectedIndex());
		} catch (IndexOutOfBoundsException e) {
			road = null;
		}
		
		if (road == null) {
			return;
		}
		
		Town town = towns.get(index);
		connections.remove(town);
		connections.put(town, road);
		
		update();
	}
	
	public void removeConnection(int index) {
		Town town = towns.get(index);
		towns.remove(index);
		
		connections.remove(town);
	}
	
	private void update() {
		List<Town> storage = new ArrayList<Town>();
		storage.addAll(towns);
		int index = connectionView.getSelectionModel().getSelectedIndex(); 
		
		towns.clear();
		towns.addAll(storage);
		
		connectionView.getSelectionModel().select(index);
	}
	
	private void townRemoved(Town town) {
		if (towns.contains(town)) {
			towns.remove(town);
			connections.remove(town);
		}
	}
	
	private void roadRemoved(Road road) {
		List<Town> toRemove = new ArrayList<Town>();
		
		if (connections.containsValue(road)) {
			for (Town town : connections.keySet()) {
				if (connections.get(town) == road) {
					toRemove.add(town);
				}
			}
		}
		
		for (Town town : toRemove) {
			connections.remove(town);
			towns.remove(town);
		}
	}
	
	private ListView<Town> createTownBrowser() {
		ListView<Town> browser = new ListView<Town>(availableTowns);
		browser.setCellFactory((ListView<Town> list) -> {
			return new TownThumb();
		});
		return browser;
	}
	
	private ListView<Road> createRoadBrowser() {
		ListView<Road> browser = new ListView<Road>(availableRoads);
		browser.setCellFactory((ListView<Road> list) -> {
			return new RoadThumb();
		});
		return browser;
	}
	
	private void updateContextMenu(int index) {
		if (index >= 0) {
			editTown.setDisable(false);
			editRoad.setDisable(false);
			removeConnection.setDisable(false);
		} else {
			editTown.setDisable(false);
			editRoad.setDisable(false);
			removeConnection.setDisable(true);
		}
	}
	
	private void updateIcon(File imageFile) {
		try {
			iconView.setImage(new Image(new FileInputStream(imageFile)));
		} catch (FileNotFoundException e) {
			iconView.setImage(blankImage);
		}
	}
	
	private void createIconFileDialogue() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Select Image");
		alert.setHeaderText(null);
		alert.setGraphic(null);
		alert.setContentText(null);
		alert.getDialogPane().setContent(imageBrowser);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.CANCEL || imageBrowser.getSelectedFile() == null) {
			return;
		}
		
		String filePath = imageBrowser.getSelectedFile();
		File file = new File(filePath);
		
		iconField.setText(filePath);
		updateIcon(file);
	}
	
	private void openMapEdittor() {
		mapEditor.generateMap();
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Edit Map");
		alert.setHeaderText(null);
		alert.setGraphic(null);
		alert.setContentText(null);
		alert.getDialogPane().setContent(mapEditor);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.CANCEL) {
			return;
		}
		
		mapEditor.finalise();
		xField.setText(Integer.toString(town.getMapX()));
		yField.setText(Integer.toString(town.getMapY()));
	}
	
	/*
	 * preserved for reasons of pride
	public void updateIcon(String imageFile) {
		GraphicsContext gc = iconView.getGraphicsContext2D();
		gc.clearRect(0, 0, getWidth(), getHeight());
		
		BufferedImage bimg = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = bimg.createGraphics();
		g2d.drawImage(SwingFXUtils.fromFXImage(new Image(imageFile), null)
				.getScaledInstance(100, 100, BufferedImage.SCALE_SMOOTH), 0, 0, 100, 100, null);
		g2d.dispose();
		
		Image image = SwingFXUtils.toFXImage(bimg, null);
		gc.drawImage(image, 0, 0);
	}
	*/
	
	private void createComponents() {
		mapEditor = new MapEditor(map);
		
		connections = new HashMap<Town, Road>();
		
		setHgap(10.0);
		setVgap(10.0);
		setPadding(new Insets(0, 10, 0, 10));
		
		Label iconImageLabel = new Label("Icon:");
		iconView = new ImageView();
		iconView.setFitWidth(100);
		iconView.setFitHeight(100);
		try {
			blankImage = new Image(new FileInputStream(new File("graphics/editor/blank.png")));
		} catch (FileNotFoundException e) {
			blankImage = null;
		}
		
		Label idLabel = new Label("Town ID:");
		Label nameLabel = new Label("Town Name:");
		Label iconLabel = new Label("Icon Location:");
		Label xyLabel = new Label("Map x,y:");
		
		idField = new TextField();
		nameField = new TextField();
		iconField = new TextField();
		iconField.setEditable(false);
		xField = new TextField();
		xField.setEditable(false);
		xField.setMaxWidth(50);
		yField = new TextField();
		yField.setEditable(false);
		yField.setMaxWidth(50);
		HBox padding = new HBox();
		HBox.setHgrow(padding, Priority.ALWAYS);
		HBox xyContainer = new HBox();
		xyContainer.getChildren().addAll(xField, padding, yField);
		
		Button iconBrowseButton = new Button("Browse...");
		iconBrowseButton.setOnAction((ActionEvent event) -> {
			createIconFileDialogue();
		});
		Button editPositionButton = new Button("Edit...");
		editPositionButton.setOnAction((ActionEvent event) -> {
			openMapEdittor();
		});
		
		towns = FXCollections.observableArrayList();
		ListView<Town> connectionList = new ListView<Town>(towns);
		
		connectionList.setCellFactory((ListView<Town> list) -> {
			return new ConnectionThumb(connections);
		});
		
		connectionList.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Town> observable,
				Town oldValue, Town newValue) -> {
			updateContextMenu(connectionList.getSelectionModel().getSelectedIndex());
		});
		
		connectionList.setOnContextMenuRequested((ContextMenuEvent event) -> {
			connectionMenu.show(connectionList, event.getScreenX(), event.getScreenY());
		});
		
		connectionMenu = new ContextMenu();
		newConnection = new MenuItem("New Connection");
		newConnection.setOnAction((ActionEvent e) -> {
			createConnection();
		});
		Menu editMenu = new Menu("Edit");
		editTown = new MenuItem("Town");
		editTown.setOnAction((ActionEvent e) -> {
			editConnectionTown(connectionList.getSelectionModel().getSelectedIndex());
		});
		editRoad = new MenuItem("Road");
		editRoad.setOnAction((ActionEvent e) -> {
			editConnectionRoad(connectionList.getSelectionModel().getSelectedIndex());
		});
		removeConnection = new MenuItem("Remove Connection");
		removeConnection.setOnAction((ActionEvent e) -> {
			removeConnection(connectionList.getSelectionModel().getSelectedIndex());
		});
		
		editMenu.getItems().addAll(editTown, editRoad);
		connectionMenu.getItems().addAll(newConnection, editMenu, removeConnection);
		
		Button saveButton = new Button("Confirm");
		Button resetButton = new Button("Reset");
		
		saveButton.setOnAction((ActionEvent event) -> {
			save();
		});
		resetButton.setOnAction((ActionEvent event) -> {
			showTownInfo(town);
		});
		
		add(iconImageLabel, 0, 0);
		add(iconView, 0, 1, 1, 3);
		
		add(idLabel, 1, 0);
		add(nameLabel, 1, 1);
		add(iconLabel, 1, 2);
		add(xyLabel, 1, 3);
		
		add(idField, 2, 0);
		add(nameField, 2, 1);
		add(iconField, 2, 2);
		add(xyContainer, 2, 3);
		
		add(iconBrowseButton, 3, 2);
		add(editPositionButton, 3, 3);
		
		add(connectionList, 0, 4, 4, 2);
		
		add(saveButton, 2, 8);
		add(resetButton, 3, 8);
		
		GridPane.setHalignment(iconImageLabel, HPos.CENTER);
		GridPane.setHalignment(iconView, HPos.LEFT);
		
		GridPane.setHalignment(idLabel, HPos.RIGHT);
		GridPane.setHalignment(nameLabel, HPos.RIGHT);
		GridPane.setHalignment(iconLabel, HPos.RIGHT);
		GridPane.setHalignment(xyLabel, HPos.RIGHT);
		
		GridPane.setHalignment(idField, HPos.LEFT);
		GridPane.setHalignment(nameField, HPos.LEFT);
		GridPane.setHalignment(iconField, HPos.LEFT);
		GridPane.setHalignment(xyContainer, HPos.LEFT);
		
		GridPane.setHalignment(iconBrowseButton, HPos.LEFT);
		
		GridPane.setHalignment(saveButton, HPos.RIGHT);
		GridPane.setHalignment(resetButton, HPos.RIGHT);
		
		updateContextMenu(-1);
	}
	
	static private class ConnectionThumb extends ListCell<Town> {
		
		private Map<Town, Road> connections;
		
		private HBox main;
		
		private ImageView image;
		private Label townName;
		private Label roadName;
		
		public ConnectionThumb(Map<Town, Road> connections) {
			super();
			
			this.connections = connections;
			
			createComponents();
		}
		
		private void createComponents() {
			main = new HBox();
			main.setPadding(new Insets(0, 10, 0, 10));
			
			image = new ImageView();
			image.setFitHeight(16);
			image.setFitWidth(16);
			
			Label townLabel = new Label("Town: ");
			townName = new Label();
			HBox padding = new HBox();
			Label roadLabel = new Label("Road: ");
			roadName = new Label();
			
			main.getChildren().addAll(townLabel, townName, image, padding, roadLabel, roadName);
			
			HBox.setHgrow(padding, Priority.ALWAYS);
		}
		
		@Override
		public void updateItem(Town item, boolean empty) {
			super.updateItem(item, empty);
			if (empty || item == null || connections.get(item) == null) {
				
				setGraphic(null);
				setText(null);
			} else {
				try {
					image.setImage(new Image(new FileInputStream(new File(item.getIconLocation()))));
				} catch (FileNotFoundException e) {
					image.setImage(null);
				}
				
				townName.setText(item.getName());
				roadName.setText(connections.get(item).getID());
				
				setGraphic(main);
				setText(null);
			}
		}
	}
	
	static private class TownThumb extends ListCell<Town> {
		private ImageView imageView = new ImageView();
		
		public TownThumb() {
			super();
		}
		
		@Override
		public void updateItem(Town item, boolean empty) {
			super.updateItem(item, empty);
			if (empty || item == null) {
				imageView.setImage(null);
				
				setGraphic(null);
				setText(null);
			} else {
				try {
					imageView.setImage(new Image(new FileInputStream(new File(item.getIconLocation()))));
				} catch (FileNotFoundException e) {
					imageView.setImage(null);
				}
				imageView.setFitWidth(16);
				imageView.setFitHeight(16);
				
				setGraphic(imageView);
				setText(item.getName());
			}
		}
	}
	
	static private class RoadThumb extends ListCell<Road> {
		
		public RoadThumb() {
			super();
		}
		
		@Override
		public void updateItem(Road item, boolean empty) {
			super.updateItem(item, empty);
			if (empty || item == null) {
				
				setGraphic(null);
				setText(null);
			} else {
				
				setGraphic(null);
				setText(item.getID());
			}
		}
	}
}
