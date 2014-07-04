package com.polarbirds.screenCapture;


import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class CaptureFrame {
	private JFrame frame; //The parent frame
	private DragPanel dPanel; //The displayed overlay, also containing the red TextArea
	private MouseHandler mouse;
	private Rectangle bound; //The user selected bound
	private Color TRANSPARENT_COLOR = MyColors.TRANSPARENT_COLOR; //RGBA with alpha 0
	private Rectangle frameBounds = new Rectangle();
	private int minRefreshRate = Integer.MAX_VALUE;
	
	public CaptureFrame() {
		
		//Time to get some multi monitor action going on!
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] gd = ge.getScreenDevices();
		
		for (GraphicsDevice monitor : gd) {
			minRefreshRate = Math.min(minRefreshRate, monitor.getDisplayMode().getRefreshRate());
			
			for (GraphicsConfiguration config : monitor.getConfigurations()) {
				frameBounds = frameBounds.union(config.getBounds()); //Union the bounds to get one Rectangle that spans across all monitors.
			}
		}
		System.out.println(frameBounds); //FIXME debug.
		
		init();
	}
	
	
	public static void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CaptureFrame window = new CaptureFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	private void init() {
		bound = new Rectangle();
		frame = new JFrame();
		dPanel = new DragPanel();
		mouse = new MouseHandler(this);
		
		frame.setUndecorated(true);
		frame.setBackground(TRANSPARENT_COLOR);
		frame.setBounds(frameBounds);
		frame.setLayout(null);
		frame.add(dPanel);
		dPanel.setBounds(0, 0, frameBounds.width, frameBounds.height); //FIXME 0,0 may be incorrect. Use frameBounds instead
		
		
		frame.addKeyListener(
				new KeyListener() {

					@Override
					public void keyTyped(KeyEvent e) {}
		
					@Override
					public void keyPressed(KeyEvent e) {}
		
					@Override
					public void keyReleased(KeyEvent e) {
						if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
							frame.dispose();
						if (e.getKeyCode() == KeyEvent.VK_ENTER)
							capture();
					}
					
				});
		
		frame.addMouseListener(mouse);
		frame.addMouseMotionListener(mouse);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void capture() {
		//Some black magic makes the screen capture actually work on Unity after this call to setVisible.
		//I'm not here to make a screen capture of the screen capture program. I ain't that meta.
		//Ironically the window does not get hidden before the screen is captured tho. Therefore the sleep.
		frame.setVisible(false);
		frame.dispose();
		
		try {
			//This whole thing is a bad idea. But it works, sadly.
			//I have to sleep for 10x refresh time since it seems to clear the frame by then.
			int duration = Math.round(10000/minRefreshRate);
			System.out.println(duration);
			
			Thread.currentThread().sleep(duration);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		ScreenCapturer capt;
		
		try {
			capt = new ScreenCapturer();
		} catch (AWTException e) {
			System.err.println("Error in creating robot. "+e);
			e.printStackTrace();
			frame.dispose();
			return; //It all went to hell, so I'm giving up on you, lad!
		}
		
		if (mouse.getScreenRelativeBounds().width == 0 || mouse.getScreenRelativeBounds().height == 0) {
			capt.capture(frameBounds);
		} else {
			capt.capture(mouse.getScreenRelativeBounds());
		}
		FileSaver.setImage(capt.getImage());
		FileSaver.run();
	}
	
	public void draw(Rectangle bound) {
		this.bound = bound;
		dPanel.setBound(bound);
		dPanel.repaint();
	}
}


