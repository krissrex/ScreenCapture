package com.polarbirds.screenCapture;

import java.awt.EventQueue;

public class Main {
    
	public static void main(String[] args) {
	    final String config = "config.txt";
		System.out.println("Started.");
		
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    PluginHandler ph = new PluginHandler(new Configuration(config).getValues());
                    new CaptureFrame(ph.getLoadedPlugins());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
	}
}
