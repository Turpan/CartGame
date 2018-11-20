package tests;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import amyGraphics.Texture;
import cartGame.travel.graphics.TravelGraphic;

public class TestForest extends TravelGraphic {
	
	static final private int TREECOUNT = 10;
	static final private int WIDTH = 16000;
	
	public TestForest() {
		super();
		
		Texture tree = loadTree();
		Texture farm = loadFarm();
		
		double[] treeDimen = new double[] {600, 2000, 0};
		double[] farmDimen = new double[] {16000, 8000, 0};
		
		for (int i=0; i<2; i++) {
			float last = 0;
			float start = (WIDTH / TREECOUNT);
			double heightquot = i * 0.2;
			treeDimen[1] *= 1 - heightquot;
			
			for (int j=0; j<TREECOUNT; j++) {
				last += start;
				addBackdrop(tree, last, treeDimen.clone(), i);
			}
		}
		
		addBackdrop(farm, 0, farmDimen, 2);
	}
	
	public Texture loadTree() {
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("graphics/tree.png"));
		} catch (IOException e) {
			System.exit(1);
		}
		Texture texture = new Texture(img);
		texture.setWidth(img.getWidth());
		texture.setHeight(img.getHeight());
		return texture;
	}
	
	public Texture loadFarm() {
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("graphics/farm.png"));
		} catch (IOException e) {
			System.exit(1);
		}
		Texture texture = new Texture(img);
		texture.setWidth(img.getWidth());
		texture.setHeight(img.getHeight());
		return texture;
	}
	
}
