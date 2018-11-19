package cartGame.tools.editor;

import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class ShopPane extends Tab {
	public ShopPane() {
		super();
		
		setText("Shops");
		
		createComponents();
	}
	
	private void createComponents() {
		VBox main = new VBox();
		
		HBox container = new HBox();
		
		Label label = new Label("Shops");
		
		VBox.setVgrow(container, Priority.ALWAYS);
		
		main.getChildren().add(container);
		container.getChildren().add(label);
		setContent(main);
	}
}
