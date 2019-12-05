package tests.towns;

import cartGame.travel.towns.Town;

public class ShepardCrossing extends Town {
	public ShepardCrossing() {
		super();
		
		setID("shepards_crossing");
		setName("Shepard's Crossing");
		setDesc("A small farming town.");
		setBackgroundLocation("graphics/towns/shepards_crossing/background.png");
		setIconLocation("");
		setIntroLocation("");
		setMapX(157);
		setMapY(226);
		setMapLocation("graphics/towns/shepards_crossing/map.png");
		setMapHoverLocation("graphics/towns/shepards_crossing/map-hover.png");
		setMapPresentLocation("graphics/towns/shepards_crossing/map-present.png");
		setShop(new DefaultShop());
		
		loadImage();
	}
}
