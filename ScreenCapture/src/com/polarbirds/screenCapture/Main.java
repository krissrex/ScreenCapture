package com.polarbirds.screenCapture;

import com.polarbirds.screenCapture.plugin.Configuration;
import com.polarbirds.screenCapture.plugin.PluginHandler;
import com.polarbirds.screenCapture.view.View;
import com.polarbirds.screenCapture.view.swing.CaptureFrame;

import java.awt.*;
import java.io.FileNotFoundException;

public class Main {
    
	public static void main(String[] args) {
	    final String config = "config.txt";
		System.out.println("Started.");

        try {
            View view = new CaptureFrame();
            Configuration configuration = new Configuration(config);

            new Controller(view, configuration).start();
        } catch (AWTException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
