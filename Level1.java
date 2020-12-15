import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.util.*;

public class Level1 extends JFrame implements Runnable {
	
	private static final long serialVersionUID = 1L;
	
	private ArrayList<SnakeBody> snakeBody;
	private int snakeSize;
	public int snakeDirection;
	public SnakeFood snakeFood;
	private Location lastTailLocation;
	private Graphics g;
	private boolean gameOver;
	
	public final static   int UP = 1;
	public final static int DOWN = 2;
	public final static int LEFT = 3;
	public final static int RIGHT = 4;
	
	private BufferStrategy bs;
	private volatile boolean running;
	private Thread gameThread;
	private SnakeFace snakeFace;
	
	Level1() {
		snakeDirection = RIGHT;
	}
	
	public void run() {
		
		running = true;
		snakeBody = new ArrayList<SnakeBody>();
		snakeFace = new SnakeFace(new Location(360,210));
		snakeBody.add(new SnakeBody(new Location(330,210)));
		snakeBody.add(new SnakeBody(new Location(300,210)));
		snakeFood = new SnakeFood(new Location(120,90));
		lastTailLocation = new Location(0,0);
		gameOver = false;
		
		while(running) {
			TestLocation();
			levelCollision();
			bodyCollision();
			gameLoop();
			if(gameOver) {
				mode(1000L);
				reset();
			}
			if(isFoodEaten()) {
				foodProcess();
				continue;
			}
			mode(100L);
			moveSnake();
		}
	}		
	
	private void foodProcess() {
		
		snakeBody.add(new SnakeBody(new Location(lastTailLocation.x,lastTailLocation.y)));
		
		// For new location of food
		Location newFoodLocation = new Location();
		Random rand = new Random();
		boolean flag = true;
		while(flag) {
			
		newFoodLocation.x = rand.nextInt(24) * 30;
		newFoodLocation.y = rand.nextInt(14) * 30;
		
		if(snakeFace.faceLocation != newFoodLocation) {
			for(int i = 0; i < snakeSize; i++)
				if(snakeBody.get(i).bodyLocation.x != newFoodLocation.x && snakeBody.get(i).bodyLocation.y != newFoodLocation.y)
					flag = false;
				else 
					flag = true;
		}
		
		snakeFood.foodLocation.x = newFoodLocation.x;
		snakeFood.foodLocation.y = newFoodLocation.y;
		
		}
		
		
	}
	
	private boolean isFoodEaten() {
		if(snakeFace.faceLocation.x == snakeFood.foodLocation.x && snakeFace.faceLocation.y == snakeFood.foodLocation.y)
			return true;
		else 
			return false;
	}
	
	private void mode(long l) {
		
		try {
			gameThread.sleep(l);
		}catch(InterruptedException ie) {
			ie.printStackTrace();
		}
	}
	
	private boolean levelCollision() {
		
		if(snakeFace.faceLocation.x < 0)
			gameOver = true;
		if(snakeFace.faceLocation.y < 0)
			gameOver = true;
		if(snakeFace.faceLocation.x > 720)
			gameOver = true;
		if(snakeFace.faceLocation.y > 390)
			gameOver = true;
		
		return false;
	}
	
	private boolean bodyCollision() {
		
		for(int i = 2; i < snakeSize; i++) {
			if(snakeFace.faceLocation.x == snakeBody.get(i).bodyLocation.x && snakeFace.faceLocation.y == snakeBody.get(i).bodyLocation.y ) {
				gameOver = true;
				return true;
			}
				
		}
		
		return false;
	}
	
	public void reset() {
		
		snakeBody.clear();
		snakeFace = new SnakeFace(new Location(360,210));
		snakeBody.add(new SnakeBody(new Location(330,210)));
		snakeBody.add(new SnakeBody(new Location(300,210)));
		lastTailLocation = new Location(0,0);
		snakeDirection = RIGHT;
		snakeSize = 2;
		gameOver = false;
	}
	
	private void moveSnake() {
		
		lastTailLocation.x = snakeBody.get(snakeSize - 1).bodyLocation.x;
		lastTailLocation.y = snakeBody.get(snakeSize - 1).bodyLocation.y;
		
		for(int i = snakeSize - 1; i >=1; i-- ) {
			snakeBody.get(i).bodyLocation.x = snakeBody.get(i-1).bodyLocation.x;
			snakeBody.get(i).bodyLocation.y = snakeBody.get(i-1).bodyLocation.y;
		}
		
		snakeBody.get(0).bodyLocation.x = snakeFace.faceLocation.x;
		snakeBody.get(0).bodyLocation.y = snakeFace.faceLocation.y;
		
		if(snakeDirection == UP) {
			snakeFace.faceLocation.y = snakeFace.faceLocation.y - 30; 
		}
		else if(snakeDirection == DOWN) {
			snakeFace.faceLocation.y = snakeFace.faceLocation.y + 30;
		}
		else if(snakeDirection == LEFT) {
			snakeFace.faceLocation.x = snakeFace.faceLocation.x - 30;
		}
		else if(snakeDirection == RIGHT) {
			snakeFace.faceLocation.x = snakeFace.faceLocation.x + 30;
		}
	}
	
	private void TestLocation() {
		//System.out.println("Face location X : " + snakeFace.faceLocation.x + " Y : " + snakeFace.faceLocation.y);
		//System.out.println("Last tail location X " + lastTailLocation.x + "Last tail Location Y" + lastTailLocation.y);
		//System.out.println("Food location X : " + snakeFood.foodLocation.x + " Y : " + snakeFood.foodLocation.y);
		//System.out.println("\n");
		//System.out.println("Body2 location X : " + snakeBody.get(1).bodyLocation.x + " Y : " + snakeBody.get(1).bodyLocation.y);
	}

	private void gameLoop() {
		do {
			do {
				 g = null;
				try {
					g = bs.getDrawGraphics();
					g.clearRect(0,0,720,480);
					
					if(gameOver) {
						Font temp = g.getFont();
						
						Font font = new Font("Dialog",Font.BOLD | Font.ITALIC, 25);
						g.setFont(font);
						g.setColor(Color.BLACK);
						g.fillRect(270,180,210,60);
						g.setColor(Color.RED);
						g.drawString("Game Over!!!",290,220);
						
						g.setFont(temp);
					}
					
					renderLevel(g);
					renderSnake(g);
					
				}finally {
					if(g != null) {
						g.dispose();
					}
				}
			}while(bs.contentsRestored());
			bs.show();
		}while(bs.contentsLost());
	}
	
	private void renderLevel(Graphics g) {
		Color c = g.getColor();
		
		g.setColor(Color.RED);
		g.drawRect(0,0,720,420);
		g.drawRect(1,1,718,418);
		g.drawRect(2,2,716,416);
		
		g.drawString("Name  : ",20,440);
		g.drawString("Level : ",20,460);
		g.drawString("Score : ",360,440);
		g.drawString("Mode  : ",360,460);
		
		g.setColor(c);
	}
	
	private void renderSnake(Graphics g) {
		
		snakeFace.drawSnakeFace(g);
		snakeSize = snakeBody.size();
		for(int i = 0; i <= snakeSize-1; i++) {
			snakeBody.get(i).drawSnakeBody(g);
		}
		
		snakeFood.drawSnakeFood(g);
		
	}
	
	protected void onWindowClosing() {
		try {
			running = false;
			gameThread.join();
		}catch(InterruptedException ie) {
			ie.printStackTrace();
		}
		System.exit(0);
	}
	
	private void showLevelMap() {
		Canvas level1Canvas = new Canvas();
		level1Canvas.setSize(720,480);
		level1Canvas.setBackground(Color.WHITE);
		level1Canvas.setIgnoreRepaint(true);
		getContentPane().add(level1Canvas);
		setTitle("Razsoft - Snake Game");
		setIgnoreRepaint(true);
		pack();
		
		addKeyListener(new KeyboardInput(this));
		setVisible(true);
		
		level1Canvas.createBufferStrategy(2);
		bs = level1Canvas.getBufferStrategy();
		
		gameThread = new Thread(this);
		gameThread.start();
		
	}
	
	public static void main(String[] args) {
		
		// TODO Auto-generated method stub
		final Level1 level1 = new Level1();
		
		level1.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				level1.onWindowClosing();
			}
		});
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				level1.showLevelMap();
			}
		});

	}

}
