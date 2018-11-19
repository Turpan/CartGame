package cartGame.tools.editor;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EditorMain extends Application {
	
	public static Stage stage;
	
	private EditorGUI gui;
	
	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		EditorMain.stage = stage;
		
		gui = new EditorGUI();
		VBox main = new VBox();
		MenuBar menu = createMenuBar();
		
		stage.setTitle("CartGame Editor");
		
		main.getChildren().addAll(menu, gui);
		VBox.setVgrow(gui, Priority.ALWAYS);
		
		Scene scene = new Scene(main);
		stage.setScene(scene);
		stage.show();
	}
	
	private MenuBar createMenuBar() {
		MenuBar menuBar = new MenuBar();
		
		Menu fileMenu = new Menu("File");
		
		Menu saveMenu = new Menu("Save");
		Menu loadMenu = new Menu("Load");
		
		MenuItem saveGame = new MenuItem("Save As Game Data");
		
		MenuItem loadGame = new MenuItem("Load Game Data");
		
		saveGame.setOnAction((ActionEvent event) -> {
			gui.saveData("data");
		});
		
		loadGame.setOnAction((ActionEvent event) -> {
			gui.loadData("data");
		});
		
		saveMenu.getItems().addAll(saveGame);
		loadMenu.getItems().addAll(loadGame);
		
		fileMenu.getItems().addAll(saveMenu, loadMenu);
		
		menuBar.getMenus().addAll(fileMenu);
		
		return menuBar;
	}
}
