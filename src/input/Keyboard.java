package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {
	
	private IKeyboard iKeyboard;
	
	public Keyboard(IKeyboard iKeyboard) {
		this.iKeyboard = iKeyboard;
	}

	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP) iKeyboard.up();
		else if(e.getKeyCode() == KeyEvent.VK_DOWN) iKeyboard.down();
		else if(e.getKeyCode() == KeyEvent.VK_LEFT) iKeyboard.left();
		else if(e.getKeyCode() == KeyEvent.VK_RIGHT) iKeyboard.right();
	}

	@Override
	public void keyTyped(KeyEvent e) {}
	
	public interface IKeyboard {
		public void up();
		public void down();
		public void left();
		public void right();
	}

}
