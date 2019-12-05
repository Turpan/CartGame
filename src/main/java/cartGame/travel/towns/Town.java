package cartGame.travel.towns;

import amyGraphics.Texture;
import cartGame.io.ImageCache;

public class Town {
	
	private String id;
	private String name;
	
	private int mapX;
	private int mapY;
	
	private String desc;
	
	private String iconLocation;
	private Texture icon;
	
	private String backgroundLocation;
	private Texture background;
	
	private String introLocation;
	private Texture intro;
	
	private String mapLocation;
	private Texture map;
	
	private String mapHoverLocation;
	private Texture mapHover;
	
	private String mapPresentLocation;
	private Texture mapPresent;
	
	private Shop shop;
	
	public Town() {
		shop = new Shop();
	}
	
	public Town(String name) {
		this();
		
		setName(name);
	}
	
	public void loadImage() {
		if (icon == null) {
			icon = ImageCache.getTexture(iconLocation);
		}
		if (background == null) {
			background = ImageCache.getTexture(backgroundLocation);
		}
		if (intro == null) {
			intro = ImageCache.getTexture(introLocation);
		}
		if (map == null) {
			map = ImageCache.getTexture(mapLocation);
		}
		if (mapHover == null) {
			mapHover = ImageCache.getTexture(mapHoverLocation);
		}
		if (mapPresent == null) {
			mapPresent = ImageCache.getTexture(mapPresentLocation);
		}
	}
	
	public String getID() {
		return id;
	}

	public void setID(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public int getMapX() {
		return mapX;
	}

	public void setMapX(int mapX) {
		this.mapX = mapX;
	}

	public int getMapY() {
		return mapY;
	}

	public void setMapY(int mapY) {
		this.mapY = mapY;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public String getIconLocation() {
		return iconLocation;
	}

	public void setIconLocation(String iconLocation) {
		this.iconLocation = iconLocation;
	}

	public Texture getIcon() {
		return icon;
	}

	public void setIcon(Texture icon) {
		this.icon = icon;
	}

	public String getBackgroundLocation() {
		return backgroundLocation;
	}

	public void setBackgroundLocation(String backgroundLocation) {
		this.backgroundLocation = backgroundLocation;
	}

	public Texture getBackground() {
		return background;
	}

	public void setBackground(Texture background) {
		this.background = background;
	}

	public String getIntroLocation() {
		return introLocation;
	}

	public void setIntroLocation(String introLocation) {
		this.introLocation = introLocation;
	}

	public Texture getIntro() {
		return intro;
	}

	public void setIntro(Texture intro) {
		this.intro = intro;
	}

	public String getMapLocation() {
		return mapLocation;
	}

	public void setMapLocation(String mapLocation) {
		this.mapLocation = mapLocation;
	}

	public String getMapHoverLocation() {
		return mapHoverLocation;
	}

	public void setMapHoverLocation(String mapHoverLocation) {
		this.mapHoverLocation = mapHoverLocation;
	}

	public String getMapPresentLocation() {
		return mapPresentLocation;
	}

	public void setMapPresentLocation(String mapPresentLocation) {
		this.mapPresentLocation = mapPresentLocation;
	}

	public Texture getMap() {
		return map;
	}

	public Texture getMapHover() {
		return mapHover;
	}

	public Texture getMapPresent() {
		return mapPresent;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}
	
	public Shop getShop() {
		return shop;
	}
}
