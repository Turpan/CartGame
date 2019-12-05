package cartGame.ui.town;

import amyGLGraphics.IO.MouseEvent;
import amyGLGraphics.IO.MouseEventAction;
import amyInterface.Component;
import amyInterface.InterfaceController;
import cartGame.travel.cart.Inventory;
import cartGame.travel.towns.Town;

public class TownController extends InterfaceController {
	
	private TownRoot root;
	
	private boolean mapOpenPressed;
	
	public TownController() {
		super();
		root = new TownRoot();
		setRoot(root);
		setTickThreshold(10);
	}
	
	public TownRoot getInterface() {
		return root;
	}
	
	public void setHour(int hour) {
		root.setHour(hour);
	}
	
	public void loadTownInfo(Town town) {
		root.setTown(town);
	}
	
	public void setInventory(Inventory inventory) {
		root.getMenu().getTopBar().getResourceBar().setInventory(inventory);
		root.getShop().getShopPanel().setInventory(inventory);
		root.getShop().getResourceBar().setInventory(inventory);
	}
	
	public boolean isMapOpenPressed() {
		boolean value = mapOpenPressed;
		mapOpenPressed = false;
		
		return value;
	}
	
	@Override
	public void tick() {
		super.tick();
		
		root.getMenu().getTopBar().getResourceBar().update();
		root.getShop().getResourceBar().update();
	}

	@Override
	protected Component processInput(MouseEvent event) {
		Component clickSource = super.processInput(event);
		
		if (clickSource == null) {
			return clickSource;
		}
		
		if ((clickSource == root.getMapButton() || clickSource == root.getMenu().getBottomBar().getLeaveButton())
				&& event.getMouseAction() == MouseEventAction.RELEASE) {
			mapOpenPressed = true;
		} else if (clickSource == root.getMenuButton() && event.getMouseAction() == MouseEventAction.RELEASE) {
			
		} else if (root.getBackButtons().contains(clickSource) && event.getMouseAction() == MouseEventAction.RELEASE) {
			root.switchToMenu();
		} else if (clickSource == root.getShopButton() && event.getMouseAction() == MouseEventAction.RELEASE) {
			root.switchToShop();
		} else if (root.getShopAddButtons().contains(clickSource) && event.getMouseAction() == MouseEventAction.RELEASE) {
			root.getShop().getShopPanel().changeAmount(1, root.getShopAddButtons().indexOf(clickSource));
		} else if (root.getShopRemoveButtons().contains(clickSource) && event.getMouseAction() == MouseEventAction.RELEASE) {
			root.getShop().getShopPanel().changeAmount(-1, root.getShopRemoveButtons().indexOf(clickSource));
		} else if (root.getShopPurchaseButtons().contains(clickSource) && event.getMouseAction() == MouseEventAction.RELEASE) {
			root.getShop().getShopPanel().purchase(root.getShopPurchaseButtons().indexOf(clickSource));
		}
		
		return clickSource;
	}
}
