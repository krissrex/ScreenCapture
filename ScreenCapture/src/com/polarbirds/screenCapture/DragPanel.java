package com.polarbirds.screenCapture;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class DragPanel extends JPanel {
	/**
	 * The area selected by the user.
	 */
	private Rectangle bound;
	
	/**
	 * Contains the red text box.
	 * A buffer is used to avoid any user interaction, as it is not needed.
	 */
	private BufferedImage tutorialBuffer;
	
	private Color BACKGROUND_COLOR = MyColors.BACKGROUND_COLOR;
	private Color BACKGROUND_TEXT_COLOR = MyColors.BACKGROUND_TEXT_COLOR;
	private Color ALMOST_TRANSPARENT_COLOR = MyColors.ALMOST_TRANSPARENT_COLOR;
	private Color BORDER_COLOR = MyColors.BORDER_COLOR;
	

	
	private String tutorialText = "Drag a selection with your mouse.\n"
			+ "Press Enter to save."
			+ "\nPress Esc to exit.";

	private static final long serialVersionUID = 4770885235733090562L; //I have no idea. I just trust Eclipse on this one.
	
	public DragPanel() {
		super(); //I don't know why, but I don't know why not either.
		this.setBackground(BACKGROUND_COLOR);
		this.setLayout(null);

		bound = new Rectangle();
		
		//A handy, red box that tells users how to use the program.
		JTextArea tutorial = new JTextArea(tutorialText);
		tutorial.setBackground(BACKGROUND_TEXT_COLOR);
		tutorial.setSize(tutorial.getPreferredSize());
		tutorial.setForeground(Color.WHITE);
		
		tutorialBuffer = new BufferedImage(tutorial.getWidth(), tutorial.getHeight(), BufferedImage.TYPE_INT_ARGB);
		tutorial.paint(tutorialBuffer.getGraphics());
	}
	
	public void setBound(Rectangle bound) {
		this.bound = bound;
	}
	
	@Override
	public void paint(Graphics g) {
		
		Graphics2D g2 = (Graphics2D)g;
		Rectangle b = this.getBounds();
		
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC));
		int x = b.width/2-tutorialBuffer.getWidth()/2;
		int y = b.height*2/3-tutorialBuffer.getHeight()/2; //Potentially unneccesary calculation on each paint.
		
		if (bound.x < x + tutorialBuffer.getWidth() && bound.y < y + tutorialBuffer.getHeight()) {
			if (x-bound.x < bound.width && y-bound.y < bound.height)
				x = bound.x+bound.width; //Offset the red JTextArea so it is outside the selection.
		}
				
		super.paint(g2);
		g2.drawImage(tutorialBuffer, null, x, y);
		
		if (bound.width != 0 && bound.height != 0) {
			g2.setColor(BORDER_COLOR);
			g2.drawRect(bound.x-1, bound.y-1, bound.width+1, bound.height+1); //Some borders look pretty.

			g2.setColor(ALMOST_TRANSPARENT_COLOR); //To prevent clicking through on Windows, it can't be alpha 0.
			g2.fillRect(bound.x, bound.y, bound.width, bound.height);
		}
		
		g2.dispose();
	}
}
