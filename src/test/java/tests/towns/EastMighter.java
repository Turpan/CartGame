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
		
		loadImage();
	}
}
