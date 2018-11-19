package cartGame.tools.editor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class ImageBrowser extends HBox{
	
	private static final double MAX = 200;
	
	TreeView<String> imageBrowser;
	
	ImageView imagePreview;
	
	Image folderIcon;
	
	public ImageBrowser() {
		super();
		
		File folderLocation = new File("graphics/editor/folder.png");
		try {
			folderIcon = new Image(new FileInputStream(folderLocation));
		} catch (FileNotFoundException e) {
			folderIcon = null;
		}
	}
	
	public String getSelectedFile() {
		TreeItem<String> item = imageBrowser.getSelectionModel().getSelectedItem();
		
		if (item == null) {
			return null;
		}
		
		String directory = item.getValue();
		
		if (item.getParent() != null) {
			item = item.getParent();
		}
		
		boolean shouldEnd = false;
		while (!shouldEnd) {
			directory = item.getValue() + "/" + directory;
			
			if (item.getParent() != null) {
				item = item.getParent();
			} else {
				shouldEnd = true;
			}
		}
		
		if (directory.endsWith(".png")) {
			return directory;
		}
		
		return null;
	}
	
	public void createInterface(String rootDirectory) {
		imageBrowser = new TreeView<String>();
		imagePreview = new ImageView();
		
		imageBrowser.getSelectionModel().selectedItemProperty().addListener(
				(ObservableValue<? extends TreeItem<String>> observable, TreeItem<String> oldValue, TreeItem<String> newValue) -> {
					if (newValue.getGraphic() instanceof ImageView) {
						ImageView image = (ImageView) newValue.getGraphic();
						if (image.getImage() != folderIcon) {
							updateImage(image.getImage());
						}
					}
				});
		
		ImageView iconView = new ImageView(folderIcon);
		iconView.setFitWidth(16);
		iconView.setFitHeight(16);
		
		File root = new File(rootDirectory);
		File gameFolder = new File(".");
		String name = root.getAbsolutePath().substring(gameFolder.getAbsolutePath().length() - 1);
		
		TreeItem<String> item = new TreeItem<String>(name);
		item.setGraphic(iconView);
		imageBrowser.setRoot(item);
		
		getChildren().add(imageBrowser);
		getChildren().add(imagePreview);
		
		setPrefWidth(500);
		
		findImages(root, item);
	}
	
	private void updateImage(Image image) {
		imagePreview.setImage(image);
		
		double width = image.getWidth();
		double height = image.getHeight();
		
		double ratio = MAX / width;
		
		double newheight = height * ratio;
		
		if (newheight <= MAX) {
			imagePreview.setFitWidth(MAX);
			imagePreview.setFitHeight(newheight);
			
			return;
		}
		
		ratio = MAX / height;
		
		double newwidth = width * ratio;
		
		imagePreview.setFitWidth(newwidth);
		imagePreview.setFitHeight(MAX);
	}
	
	private void findImages(File root, TreeItem<String> item) {
		File[] files = root.listFiles();
		
		for (File file : files) {
			if (file.isDirectory()) {
				ImageView iconView = new ImageView(folderIcon);
				iconView.setFitWidth(16);
				iconView.setFitHeight(16);
				
				String name = file.getAbsolutePath().substring(root.getAbsolutePath().length() + 1);
				
				TreeItem<String> folder = new TreeItem<String>(name);
				folder.setGraphic(iconView);
				
				item.getChildren().add(folder);
				
				findImages(file, folder);
			}
		}
		
		for (File file : files) {
			if (file.getAbsolutePath().endsWith(".png")) {
				Image icon;
				
				try {
					icon = new Image(new FileInputStream(file));
				} catch (FileNotFoundException e) {
					continue;
				}
				
				String name = file.getAbsolutePath().substring(root.getAbsolutePath().length() + 1);
				
				ImageView iconView = new ImageView(icon);
				iconView.setFitWidth(16);
				iconView.setFitHeight(16);
				
				TreeItem<String> image = new TreeItem<String>(name);
				image.setGraphic(iconView);
				item.getChildren().add(image);
			}
		}
	}
}
