package tests;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import amyGraphics.Texture;
import cartGame.io.ImageCache;
import cartGame.travel.graphics.Environment;
import cartGame.travel.graphics.TravelGraphic;

public class TestTravel extends TravelGraphic {
	
	public TestTravel() {
		super();
		
		Environment forest = new DeepForest();
		Environment paper = new DeepForestPaper();
		Environment lightforest = new LightForestPaper();
		
		List<Integer> order = new ArrayList<Integer>();
		order.add(Integer.valueOf(0));
		//order.add(Integer.valueOf(1));
		//order.add(Integer.valueOf(2));
		
		addEnvironment(forest);
		addEnvironment(paper);
		addEnvironment(lightforest);
		
		setEnvironmentOrder(order);
		
		reset();
		
		BufferedImage sky;
		try {
			sky = ImageIO.read(new File("graphics/travel/sky/" + 12 + "am.png"));
		} catch (IOException e) {
			sky = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
			sky.setRGB(0, 0, Color.BLACK.getRGB());
		}
		
		this.addBackground(sky);
		
		for (int i=1; i<12; i++) {
			try {
				sky = ImageIO.read(new File("graphics/travel/sky/" + i + "am.png"));
			} catch (IOException e) {
				sky = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
				sky.setRGB(0, 0, Color.BLACK.getRGB());
			}
			
			this.addBackground(sky);
		}
		
		try {
			sky = ImageIO.read(new File("graphics/travel/sky/" + 12 + "pm.png"));
		} catch (IOException e) {
			sky = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
			sky.setRGB(0, 0, Color.BLACK.getRGB());
		}
		
		this.addBackground(sky);
		
		for (int i=1; i<12; i++) {
			try {
				sky = ImageIO.read(new File("graphics/travel/sky/" + i + "pm.png"));
			} catch (IOException e) {
				sky = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
				sky.setRGB(0, 0, Color.BLACK.getRGB());
			}
			
			this.addBackground(sky);
		}
	}
	
}
