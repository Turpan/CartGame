package cartGame.ui.town;

import java.awt.Color;

import amyGraphics.Texture;
import amyInterface.CentreLayout;
import amyInterface.Container;
import amyInterface.Label;
import cartGame.io.ImageCache;

public class TownClock extends Container {
	
	private Label time;
	private int hour;
	
	public TownClock() {
		super();
		
		Texture clockTexture = ImageCache.getTexture("graphics/ui/town/clock.png");
		addTexture(clockTexture);
		setActiveTexture(clockTexture);
		setVisible(true);
		setBounds(3, 0, 51, 65);
		
		time = createLabel();
		
		Container container = new Container();
		container.setBounds(4, 7, 43, 12);
		container.setLayout(new CentreLayout());
		container.addChild(time);
		
		addChild(container);
		
		update();
	}
	
	public void setHour(int hour) {
		this.hour = hour;
		
		update();
	}
	
	private void update() {
		String suffix = "PM";
		if (hour < 12) suffix = "AM";
		
		String time;
		if (hour == 0) {
			time = "12:00 ";
		} else if (hour <= 12) {
			time = hour + ":00 ";
		} else {
			time = (hour - 12) + ":00 ";
		}
		
		time += suffix;
		this.time.setText(time);
	}
	
	public Label createLabel() {
		Label label = new Label();
		
		label.setFont(ImageCache.getFont("fonts/DSBehrensschrift.ttf"));
		//label.setFont(ImageCache.getFont("fonts/IrishPenny.ttf"));
		
		label.setFontSize(10);
		
		label.setColour(new Color(0, 0, 0, 0));
		
		label.setFontColour(Color.RED);
		
		label.setVisible(true);
		
		return label;
	}
}
