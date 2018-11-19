package cartGame.tools.editor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import cartGame.travel.towns.Road;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.util.converter.IntegerStringConverter;

public class RoadEditor extends GridPane{
	
	private TextField idField;
	private TextField distanceField;
	
	private Road road;
	
	List<EditorListener> listeners;
	
	public RoadEditor() {
		super();
		
		listeners = new ArrayList<EditorListener>();
		
		createComponents();
	}
	
	public void addListener(EditorListener listener) {
		listeners.add(listener);
	}
	
	public void showRoadInfo(Road road) {
		if (road == null) {
			showBlankInfo();
			return;
		}
		
		this.road = road;
		
		idField.setText(road.getID());
		distanceField.setText(Integer.toString(road.getDistance()));
	}
	
	public void save() {
		road.setID(idField.getText());
		road.setDistance(Integer.parseInt(distanceField.getText()));
		
		for (EditorListener listener : listeners) listener.update();
	}
	
	public void reset() {
		showRoadInfo(road);
	}
	
	private void showBlankInfo() {
		idField.setText("");
		distanceField.setText("");
	}
	
	private void createComponents() {
		setHgap(10.0);
		setVgap(10.0);
		setPadding(new Insets(0, 10, 0, 10));
		
		Label idLabel = new Label("ID:");
		Label distanceLabel = new Label("Distance:");
		
		idField = new TextField();
		distanceField = new TextField();
		TextFormatter<Integer> formatter = new TextFormatter<>(
			    new IntegerStringConverter(), 
			    0,  
			    c -> Pattern.matches("\\d*", c.getText()) ? c : null );
		distanceField.setTextFormatter(formatter);
		
		Button saveButton = new Button("Confirm");
		Button resetButton = new Button("Reset");
		
		saveButton.setOnAction((ActionEvent event) -> {
			save();
		});
		resetButton.setOnAction((ActionEvent event) -> {
			reset();
		});
		
		add(idLabel, 0, 0);
		add(distanceLabel, 0, 1);
		
		add(idField, 1, 0);
		add(distanceField, 1, 1);
		
		add(saveButton, 1, 2);
		add(resetButton, 2, 2);
		
		GridPane.setHalignment(idLabel, HPos.RIGHT);
		GridPane.setHalignment(distanceLabel, HPos.RIGHT);
		
		GridPane.setHalignment(idField, HPos.LEFT);
		GridPane.setHalignment(distanceField, HPos.LEFT);
		
		GridPane.setHalignment(saveButton, HPos.RIGHT);
		GridPane.setHalignment(resetButton, HPos.RIGHT);
	}
}
