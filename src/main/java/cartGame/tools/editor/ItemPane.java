package cartGame.tools.editor;

import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class ItemPane extends Tab {
	public ItemPane() {
		super();
		
		setText("Items");
		
		createComponents();
	}
	
	private void createComponents() {
		VBox main = new VBox();
		
		HBox container = new HBox();
		
		Label label = new Label("Items");
		
		VBox.setVgrow(container, Priority.ALWAYS);
		
		main.getChildren().add(container);
		container.getChildren().add(label);
		setContent(main);
	}
}
