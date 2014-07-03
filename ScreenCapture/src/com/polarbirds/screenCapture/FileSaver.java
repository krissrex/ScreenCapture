package com.polarbirds.screenCapture;

import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class FileSaver {	
	private  static BufferedImage image;
	private JFrame frame;
	private FileDialog dialog;
	
	public FileSaver() {
		init();
	}
	
	public static void setImage(BufferedImage image) {
		FileSaver.image = image;
	}
	
	public static void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FileSaver window = new FileSaver();
					window.query();
					//window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void init() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		dialog = new FileDialog(frame, "Save screenshot", FileDialog.SAVE);
		dialog.setFilenameFilter(
				new FilenameFilter() 
				{
					@Override
					public boolean accept(File dir, String name) {
						if (name.endsWith(".png"))
							return true;
						return false;
				}
		});
		

		
	}
	
	private void query() {
		dialog.setVisible(true);
		if (dialog.getFile() == null) {
			frame.dispose();
			dialog.dispose();
			return;
		}
		
		String path = dialog.getDirectory()+"/"+ dialog.getFile() +
				(dialog.getFile().endsWith(".png")? "":".png");
		File file = new File(path);
		
		try {
			ImageIO.write(image, "png", file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Failed to save file. "+e+"\n\n");
			e.printStackTrace();
		} finally {
			frame.dispose();
			dialog.dispose();
		}
		System.out.println("Done!");
	}
}
