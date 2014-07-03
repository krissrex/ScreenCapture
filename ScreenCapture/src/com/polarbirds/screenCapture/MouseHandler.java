/**
 * 
 */
package com.polarbirds.screenCapture;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * @author kristian
 *
 */
public class MouseHandler implements MouseListener, MouseMotionListener {

	private boolean lDown = false;
	private Point start;
	private Point current;
	private CaptureFrame frame;
	
	public MouseHandler(CaptureFrame frame) {
		this.frame = frame;
		start = new Point();
		current = new Point();
	}
	public Rectangle getBounds() {
		int tlx = start.x <= current.x ? start.x : current.x;
		int tly = start.y <= current.y ? start.y : current.y;
		int width = current.x-start.x;
		int height = current.y - start.y;
		width = width < 0 ? -width : width;
		height = height < 0 ? -height : height;
		
		Rectangle rect = new Rectangle(tlx, tly, width, height);
		return rect;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if (lDown)
			current = e.getPoint();
		
		frame.draw(getBounds());
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseMoved(MouseEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			lDown = true;
			start = e.getPoint();
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			lDown = false;
			current = e.getPoint();
			frame.draw(getBounds());
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

}
