import java.awt.Color;
import java.awt.Graphics;

public class SnakeFood {
	
	public Location foodLocation;
	
	SnakeFood(Location location){
		foodLocation = location;
	}
	
	public void drawSnakeFood(Graphics g){
		Color c = g.getColor();
		
		g.setColor(Color.GRAY);
		g.fillRect(foodLocation.x,foodLocation.y,30,30);
		
		g.setColor(c);
	}
	
}
