package com.polarbirds.screenCapture.view.swing;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JPanel;

public class DragPanel extends JPanel {
	/**
	 * The area selected by the user.
	 */
	private Rectangle bound;
	
	private Color BACKGROUND_COLOR = MyColors.BACKGROUND_COLOR;
	private Color ALMOST_TRANSPARENT_COLOR = MyColors.ALMOST_TRANSPARENT_COLOR;
	private Color BORDER_COLOR = MyColors.BORDER_COLOR;
	
	public DragPanel() {
		super();
		this.setBackground(BACKGROUND_COLOR);
		this.setLayout(null);

		bound = new Rectangle();
	}
	
	public void setBound(Rectangle bound) {
		this.bound = bound;
	}

    @Override
    protected void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        Rectangle b = this.getBounds();

        g2.setColor(BACKGROUND_COLOR);

        g2.setComposite(AlphaComposite.Src);
        g2.fillRect(0,0,b.width, b.height);
        //super.paintComponent(g2);

        paintBox(g2);
        g2.dispose();
    }

	private void paintBox(Graphics2D g2) {
		if (bound.width != 0 && bound.height != 0) {
			g2.setColor(BORDER_COLOR);
			g2.drawRect(bound.x-1, bound.y-1, bound.width+1, bound.height+1); //Some borders look pretty.

			g2.setColor(ALMOST_TRANSPARENT_COLOR); //To prevent clicking through on Windows, it can't be alpha 0.
			g2.fillRect(bound.x, bound.y, bound.width, bound.height);
		}

	}
}
