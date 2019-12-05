package cartGame.ui.travel;

import java.awt.Color;

import amyGraphics.Texture;
import amyInterface.CentreLayout;
import amyInterface.Container;
import amyInterface.Label;
import cartGame.io.ImageCache;

public class TravelClock extends Container {
	
	private Label time;
	
	private int lastHour = -1;
	private int lastMinute = -1;
	
	public TravelClock() {
		super();
		
		Texture clockTexture = ImageCache.getTexture("graphics/ui/travel/clock.png");
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
		
		update(0, 0);
	}
	
	public void update(int hour, int minute) {
		if (lastHour == hour && lastMinute == minute) {
			return;
		}
		
		lastHour = hour;
		lastMinute = minute;
		
		String suffix = "PM";
		if (hour < 12) suffix = "AM";
		
		String mins = String.format("%02d", minute);
		
		String time;
		if (hour == 0) {
			time = "12:" + mins + " ";
		} else if (hour <= 12) {
			time = hour + ":" + mins + " ";
		} else {
			time = (hour - 12) + ":" + mins + " ";
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
