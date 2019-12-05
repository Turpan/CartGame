package tests.towns;

import cartGame.travel.towns.Town;

public class EastMighter extends Town {
	public EastMighter() {
		super();
		
		setID("east_mighter");
		setName("East Mighter");
		setDesc("A large metropolis.");
		setBackgroundLocation("graphics/towns/east_mighter/background.png");
		setIconLocation("");
		setIntroLocation("");
		setMapX(195);
		setMapY(100);
		setMapLocation("graphics/towns/east_mighter/map.png");
		setMapHoverLocation("graphics/towns/east_mighter/map-hover.png");
		setMapPresentLocation("graphics/towns/east_mighter/map-present.png");
		setShop(new DefaultShop());
		
		loadImage();
	}
}
