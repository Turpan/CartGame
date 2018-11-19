package cartGame.tools.editor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import cartGame.travel.towns.Road;
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
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.HBox;

public class RoadPane extends Tab implements EditorListener {
	
	private RoadEditor roadEditor;
	
	private ContextMenu roadMenu;
	private MenuItem newRoad;
	private MenuItem removeRoad;
	
	private ListView<Road> roadView;
	private ObservableList<Road> roads;
	
	public RoadPane() {
		super();
		
		setText("Roads");
		
		createComponents();
	}
	
	public RoadPane(List<Road> roads) {
		super();
		
		addRoad(roads);
	}
	
	public ObservableList<Road> getRoads() {
		return roads;
	}
	
	public void createRoad() {
		Road road = new Road();
		road.setID("new_road");
		
		addRoad(road);
	}
	
	public void removeRoad(int index) {
		Road road = roads.get(index);
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirm Removal");
		alert.setHeaderText(null);
		alert.setGraphic(null);
		alert.setContentText("Are you sure you wish to delete the road '" + road.getID() + "' ?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.CANCEL) {
			return;
		}
		
		roads.remove(index);
	}
	
	public void addRoad(List<Road> roads) {
		for (Road road : roads) {
			addRoad(road);
		}
	}
	
	public void addRoad(Road road) {
		roads.add(road);
	}
	
	private void updateContextMenu(int index) {
		if (index >= 0) {
			removeRoad.setDisable(false);
		} else {
			removeRoad.setDisable(true);
		}
	}
	
	private void createComponents() {
		roadEditor = new RoadEditor();
		roadEditor.addListener(this);
		
		roads = FXCollections.observableArrayList();
		roadView = new ListView<Road>(roads);
		
		roadView.setCellFactory((ListView<Road> list) -> {
			return new Thumb();
		});
		
		roadView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Road> observable,
				Road oldValue, Road newValue) -> {
			updateContextMenu(roadView.getSelectionModel().getSelectedIndex());
			roadEditor.showRoadInfo(roadView.getSelectionModel().getSelectedItem());
		});
		
		roadView.setOnContextMenuRequested((ContextMenuEvent event) -> {
			roadMenu.show(roadView, event.getScreenX(), event.getScreenY());
		});
		
		roadMenu = new ContextMenu();
		newRoad = new MenuItem("New Town");
		newRoad.setOnAction((ActionEvent e) -> {
			createRoad();
		});
		removeRoad = new MenuItem("Remove Town");
		removeRoad.setOnAction((ActionEvent e) -> {
			removeRoad(roadView.getSelectionModel().getSelectedIndex());
		});
		
		roadMenu.getItems().addAll(newRoad, removeRoad);
		
		HBox container = new HBox();
		container.getChildren().add(roadView);
		container.getChildren().add(roadEditor);
		
		setContent(container);
		
		updateContextMenu(-1);
	}
	
	static private class Thumb extends ListCell<Road> {
		
		public Thumb() {
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

	@Override
	public void update() {
		int index = roadView.getSelectionModel().getSelectedIndex();
		
		roadView.refresh();
		
		roadView.getSelectionModel().select(index);
	}
}
