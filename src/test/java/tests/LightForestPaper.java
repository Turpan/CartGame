package tests;

import amyGraphics.Texture;
import cartGame.io.ImageCache;
import cartGame.travel.graphics.Environment;

public class LightForestPaper extends Environment {
	public LightForestPaper() {
		super();
		
		Texture layer1 = new Texture(ImageCache.getTexture("graphics/travel/backgrounds/lightforest_paper/lightforest_1_1.png"));
		Texture layer2 = new Texture(ImageCache.getTexture("graphics/travel/backgrounds/lightforest_paper/lightforest_1_2.png"));
		Texture layer3 = new Texture(ImageCache.getTexture("graphics/travel/backgrounds/lightforest_paper/lightforest_1_3.png"));
		
		Texture floor = new Texture(ImageCache.getTexture("graphics/travel/backgrounds/lightforest_paper/lightforest_1_ground.png"));
		
		double[] layer1dim = new double[]{8100, 1903, 0};
		double[] layer2dim = new double[]{8050, 2508, 0};
		double[] layer3dim = new double[]{8050, 2746, 0};
		
		double[] floordim = new double[]{8010, 0, 1500};
		
		addBackdrop(layer3, 0, layer3dim, 0);
		addBackdrop(layer2, 0, layer2dim, 1);
		addBackdrop(layer1, 0, layer1dim, 2, 200);
		
		setGround(floor, 0, floordim);
	}
}
