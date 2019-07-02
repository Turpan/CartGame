package tests.towns;

import cartGame.travel.towns.Town;

public class ShepardTown extends Town {
	public ShepardTown() {
		super();
		
		setID("shepards_crossing");
		setName("Shepard's Crossing");
		setDesc("A small farming town.");
		setBackgroundLocation("graphics/towns/shepards_crossing/background.png");
		setIconLocation("");
		setIntroLocation("");
		
		loadImage();
	}
}
