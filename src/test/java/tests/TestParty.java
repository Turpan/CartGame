package tests;
import cartGame.travel.cart.Party;
import cartGame.travel.cart.Traveller;

public class TestParty extends Party {
	public TestParty() {
		super();
		
		Traveller main = new Traveller();
		main.setName("Main Guy");
		main.setHealth(100);
		
		Traveller amy = new Traveller();
		amy.setName("Amy");
		amy.setHealth(50);
		
		getPartyMembers().add(main);
		getPartyMembers().add(amy);
	}
}
