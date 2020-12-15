// The class is for input handling of snake 
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInput implements KeyListener {
	Level1 level1;
	
	KeyboardInput(Level1 level1){
		this.level1 = level1;
		
		System.out.println("Keyboard event is generated");
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
		// System.out.println("Keyboard event is generated - Key pressed");
														// For up translation
		if(KeyEvent.VK_UP == e.getKeyCode()) {
			if(level1.snakeDirection != Level1.DOWN) 
				level1.snakeDirection = Level1.UP;
		}
														// For Down translation
		if(KeyEvent.VK_DOWN == e.getKeyCode()) {
			if(level1.snakeDirection != Level1.UP) 
				level1.snakeDirection = Level1.DOWN;
		}
														// For Left translation
		if(KeyEvent.VK_LEFT == e.getKeyCode()) {
			if(level1.snakeDirection != Level1.RIGHT) 
				level1.snakeDirection = Level1.LEFT;
		}
														// For Right translation
		if(KeyEvent.VK_RIGHT == e.getKeyCode()) {
			if(level1.snakeDirection != Level1.LEFT) 
				level1.snakeDirection = Level1.RIGHT;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
