package input;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Mouse implements MouseListener {

	private Point pressedPoint;
	private IMouse iMouse;
	
	public Mouse(IMouse iMouse) {
		this.iMouse = iMouse;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		pressedPoint = e.getPoint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Point p = e.getPoint();
		int xo = (int) (p.getX() - pressedPoint.getX());
		int yo = (int) (p.getY() - pressedPoint.getY());
		if(Math.abs(xo) > Math.abs(yo)) {
			if(xo < 0) iMouse.onSwipe(-1, true);
			else if(xo > 0) iMouse.onSwipe(1, true);
		}else {
			if(yo < 0) iMouse.onSwipe(-1, false);
			else if(yo > 0) iMouse.onSwipe(1, false);
		}
	}
	
	public interface IMouse {
		public void onSwipe(int offset, boolean horizontal);
	}

}
