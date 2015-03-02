package com.polarbirds.screencapture;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

public class ScreenCapturer {

	private Robot robot;
	private BufferedImage image;
	
	public ScreenCapturer()  throws AWTException {
		robot = new Robot(); 
	}
	
	/**
	 * 
	 * @param bound The bounds of the screen
	 * @throws IllegalArgumentException - if width and height < 0
	 * @throws SecurityException - if readDisplayPixels permission is not granted
	 * @throws HeadlessException - if GraphicsEnvironment.isHeadless() returns true.
	 */
	public void capture(Rectangle bound) {
		if (bound == null || bound.width == 0 || bound.height == 0) {
			Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
			image = robot.createScreenCapture(new Rectangle(d));
			return;
		}
		
		image = robot.createScreenCapture(bound);
	}
	
	public BufferedImage getImage() {
		return image;
	}
}
