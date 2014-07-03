package com.polarbirds.screenCapture;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class DragPanel extends JPanel {
	private Rectangle bound;
	private Color BACKGROUND_COLOR = MyColors.BACKGROUND_COLOR;
	private Color BACKGROUND_TEXT_COLOR = MyColors.BACKGROUND_TEXT_COLOR;
	private Color TRANSPARENT_COLOR = MyColors.TRANSPARENT_COLOR;
	
	private JTextArea tutorial;
	private String tutorialText = "Drag a selection with your mouse.\n"
			+ "Press Enter to save."
			+ "\nPress Esc to exit.";

	private static final long serialVersionUID = 4770885235733090562L;
	
	public DragPanel() {
		super();
		bound = new Rectangle();
		this.setLayout(null);
		
		tutorial = new JTextArea(tutorialText);
		tutorial.setEditable(false);
		tutorial.setFocusable(false);
		super.setBackground(BACKGROUND_COLOR);
		
		tutorial.setBackground(BACKGROUND_TEXT_COLOR);
		tutorial.setSize(tutorial.getPreferredSize());
		tutorial.setForeground(Color.WHITE);
		
		this.add(tutorial);
	}
	
	public void setBound(Rectangle bound) {
		this.bound = bound;
	}
	
	@Override
	public void paint(Graphics g) {
		
		Graphics2D g2 = (Graphics2D)g;
		Rectangle b = this.getBounds();
		
		//g2.setColor(BACKGROUND_COLOR);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC));
		//g2.fillRect(b.x, b.y, b.width, b.height);
		int x = b.width/2-tutorial.getWidth()/2;
		int y = b.height*2/3-tutorial.getHeight()/2;
		if (bound.x < x + tutorial.getWidth() && bound.y < y + tutorial.getHeight()) {
			if (x-bound.x < bound.width && y-bound.y < bound.height)
				x = bound.x+bound.width;
		}
		tutorial.setLocation(x, y);
		
		super.paint(g2);
		
		
		if (bound.width != 0 && bound.height != 0) {
			g2.setColor(TRANSPARENT_COLOR);
			g2.fillRect(bound.x, bound.y, bound.width, bound.height);
		}
		
		g2.dispose();
	}
}
