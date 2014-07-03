package com.polarbirds.screenCapture;


import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class CaptureFrame {
	private JFrame frame;
	private DragPanel dPanel;
	private MouseHandler mouse;
	private Rectangle bound;
	private Color TRANSPARENT_COLOR = MyColors.TRANSPARENT_COLOR;
	public static int WIDTH;
	public static int HEIGHT;
	
	public CaptureFrame() {
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		WIDTH = d.width;
		HEIGHT = d.height;
		init();
	}
	
	
	public static void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CaptureFrame window = new CaptureFrame();
					window.frame.setVisible(true);
					//window.dPanel.repaint();
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
		frame.setBounds(0, 0, WIDTH, HEIGHT);
		frame.setLayout(null);
		frame.add(dPanel);
		dPanel.setBounds(0, 0, WIDTH, HEIGHT);
		
		
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
		
		dPanel.repaint();
		
	}
	
	private void capture() {
		ScreenCapturer capt;
		
		try {
			capt = new ScreenCapturer();
		} catch (AWTException e) {
			System.err.println("Error in creating robot. "+e);
			e.printStackTrace();
			frame.dispose();
			return;
		}
		
		capt.capture(bound);
		FileSaver.setImage(capt.getImage());
		FileSaver.run();
		frame.dispose();
	}
	
	public void draw(Rectangle bound) {
		this.bound = bound;
		dPanel.setBound(bound);
		dPanel.repaint();
	}
}


