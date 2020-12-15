import java.awt.*;

public class SnakeBody {
	
	public Location bodyLocation;
	
	SnakeBody(Location location){
		bodyLocation = location;
	}
	
	public void drawSnakeBody(Graphics g){
		Color c = g.getColor();
		g.setColor(Color.GREEN);
		g.fillRect(bodyLocation.x,bodyLocation.y,30,30);
		
		g.setColor(Color.RED);
		g.drawRect(bodyLocation.x,bodyLocation.y,30,30);
		g.drawRect(bodyLocation.x+1,bodyLocation.y+1,28,28);
		
		g.setColor(c);
	}
	
}
