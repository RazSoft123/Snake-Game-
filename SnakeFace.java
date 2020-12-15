import java.awt.*;

public class SnakeFace {
	
	public Location faceLocation;
	
	SnakeFace(Location location){
		faceLocation = location;
	}
	
	public void drawSnakeFace(Graphics g){
		
		Color c = g.getColor();
		
		g.fillRect(faceLocation.x,faceLocation.y,30,30);
		g.setColor(Color.BLACK);
		g.fillRect(faceLocation.x+15,faceLocation.y+5,10,5);
		g.fillRect(faceLocation.x+15,faceLocation.y+20,10,5);
		
		g.setColor(c);
		
	}
}
