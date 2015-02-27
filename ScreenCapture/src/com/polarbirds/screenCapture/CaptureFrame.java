package com.polarbirds.screenCapture;


import com.polarbirds.screenCapture.plugin.PluginInterface;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import javax.swing.*;

public class CaptureFrame {
	private JFrame frame; //The parent frame
	private DragPanel dragPanel; //The displayed overlay, also containing the red TextArea. tronds removed the textarea :(
	private MouseHandler mouseHandler;
	private static final Color TRANSPARENT_COLOR = MyColors.TRANSPARENT_COLOR; //RGBA with alpha 0
	private Rectangle frameBounds = new Rectangle();
	private int minRefreshRate = Integer.MAX_VALUE;
	private List<PluginInterface> plugins;

    private final static String TITLE = "Screen Capture";
		
	public CaptureFrame(List<PluginInterface> plugins) {
	    
	    this.plugins = plugins;
	    
		//Time to get some multi monitor action going on!
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] gd = ge.getScreenDevices();
		
		for (GraphicsDevice monitor : gd) {
			minRefreshRate = Math.min(minRefreshRate, monitor.getDisplayMode().getRefreshRate());
			
			for (GraphicsConfiguration config : monitor.getConfigurations()) {
				frameBounds = frameBounds.union(config.getBounds()); //Union the bounds to get one Rectangle that spans across all monitors.
			}
		}
	}
	
	
	public void init() {
		frame = new JFrame(TITLE);
		dragPanel = new DragPanel();
		mouseHandler = new MouseHandler(this);
		
		frame.setUndecorated(true);
		frame.setBackground(TRANSPARENT_COLOR);
		frame.setBounds(frameBounds);
		frame.setLayout(null);

        //frame.setOpacity(0.4f); //The colors are being drawn transparent already.

        dragPanel.setBounds(0, 0, frameBounds.width, frameBounds.height); //FIXME 0,0 may be incorrect. Use frameBounds.x .y instead
		dragPanel.setDoubleBuffered(true);
        dragPanel.setOpaque(false);
        frame.add(dragPanel);

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
		
		frame.addMouseListener(mouseHandler);
		frame.addMouseMotionListener(mouseHandler);
		
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.setVisible(true);
	}
	
	private void capture() {
		//Some black magic makes the screen capture actually work on Ubuntu's Unity after this call to setVisible.
		//I'm not here to make a screen capture of the screen capture program. I ain't that meta.
		//Ironically the window does not get hidden before the screen is captured tho. Therefore the sleep.
		frame.setVisible(false);
		frame.dispose();
		
		try {
			//This whole thing is a bad idea. But it works, sadly.
			//I have to sleep for 10x refresh time since it seems to clear the frame by then.
			int duration = Math.round(10000/minRefreshRate);
			System.out.println("Timeout duration before screen capture: "+duration);

            Thread.sleep(duration);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		ScreenCapturer capt;
		
		try {
			capt = new ScreenCapturer();
		} catch (AWTException e) {
			System.err.println("Error in creating robot. "+e.getMessage());
			e.printStackTrace();
			frame.dispose();
			return; //It all went to hell, so I'm giving up on you, lad!
		}

        System.out.println("Capturing screenshot.");
        if (mouseHandler.getScreenRelativeBounds().width == 0 || mouseHandler.getScreenRelativeBounds().height == 0) {
			capt.capture(frameBounds);
		} else {
			capt.capture(mouseHandler.getScreenRelativeBounds());
		}

        //The loop may block, so it happens on a different thread.
        new Thread(){
            final List<PluginInterface> plugins = CaptureFrame.this.plugins;
            final ScreenCapturer capturer = capt;
            @Override
            public void run() {
                for(PluginInterface i : plugins){
                    i.run(capturer.getImage());
                }
            }
        }.start();
	}
	
	public void draw(Rectangle bound) {
		dragPanel.setBound(bound);
		dragPanel.repaint();
	}
}


