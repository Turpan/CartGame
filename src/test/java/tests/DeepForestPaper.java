package tests;

import amyGraphics.Texture;
import cartGame.io.ImageCache;
import cartGame.travel.graphics.Environment;

public class DeepForestPaper extends Environment{
	public DeepForestPaper() {
		super();
		
		Texture layer1 = new Texture(ImageCache.getTexture("graphics/travel/backgrounds/heavyforest_paper/heavyforest_1_1.png"));
		Texture layer2 = new Texture(ImageCache.getTexture("graphics/travel/backgrounds/heavyforest_paper/heavyforest_1_2.png"));
		Texture layer3 = new Texture(ImageCache.getTexture("graphics/travel/backgrounds/heavyforest_paper/heavyforest_1_3.png"));
		
		Texture floor = new Texture(ImageCache.getTexture("graphics/travel/backgrounds/heavyforest_paper/heavyforest_1_ground.png"));
		
		double[] layer1dim = new double[]{8100, 4500, 0};
		double[] layer2dim = new double[]{8050, 3400, 0};
		double[] layer3dim = new double[]{8050, 2792, 0};
		
		double[] floordim = new double[]{8010, 0, 1500};
		
		addBackdrop(layer3, 0, layer3dim, 0);
		addBackdrop(layer2, 0, layer2dim, 1);
		addBackdrop(layer1, 0, layer1dim, 2);
		
		setGround(floor, 0, floordim);
	}
}
