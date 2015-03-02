package com.polarbirds.screenCapture.view.swing;


import com.polarbirds.screenCapture.Controller;
import com.polarbirds.screenCapture.ScreenCapturer;
import com.polarbirds.screenCapture.plugin.PluginInterface;
import com.polarbirds.screenCapture.view.View;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.*;

public class CaptureFrame implements View{
	private JFrame frame; //The parent frame
	private DragPanel dragPanel; //The displayed overlay, also containing the red TextArea. tronds removed the textarea :(
	private MouseHandler mouseHandler;
	private static final Color TRANSPARENT_COLOR = MyColors.TRANSPARENT_COLOR; //RGBA with alpha 0
	private Rectangle frameBounds = new Rectangle();

    private boolean needsSleep = (System.getProperty("os.name").toLowerCase()).startsWith("linux");

    private Controller.OnCaptureListener listener;

    private final static String TITLE = "Screen Capture";


	public CaptureFrame() {
		//Time to get some multi monitor action going on!
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] gd = ge.getScreenDevices();
		
		for (GraphicsDevice monitor : gd) {
			for (GraphicsConfiguration config : monitor.getConfigurations()) {
				frameBounds = frameBounds.union(config.getBounds()); //Union the bounds to get one Rectangle that spans across all monitors.
			}
		}
	}


    @Override
    public void init(){
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
    }

	private void capture() {
		//Some black magic makes the screen capture actually work on Ubuntu's Unity after this call to setVisible.
		frame.setVisible(false);
		frame.dispose();

		try {
			//Ubuntu has window animations with a default 120ms transition time.
            if (needsSleep) {
                Thread.sleep(120);
            }
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

        System.out.println("Returning bounds.");

        if (mouseHandler.getScreenRelativeBounds().width == 0 || mouseHandler.getScreenRelativeBounds().height == 0) {
			listener.onCapture(frameBounds);
		} else {
            listener.onCapture(mouseHandler.getScreenRelativeBounds());
		}

	}


	public void draw(Rectangle bound) {
		dragPanel.setBound(bound);
		dragPanel.repaint();
	}


    @Override
    public void show() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    CaptureFrame.this.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void setCaptureListener(Controller.OnCaptureListener listener) {
        this.listener = listener;
    }
}


