package com.polarbirds.screencapture;

import com.polarbirds.screencapture.plugin.Configuration;
import com.polarbirds.screencapture.view.View;
import com.polarbirds.screencapture.view.swing.CaptureFrame;

import java.awt.*;
import java.io.FileNotFoundException;

public class Main {

    public static final String VERSION = "2.0"; //TODO: Keep this updated

	public static void main(String[] args) {
        if (args.length > 0){
            for (String arg : args){
                if (arg.equals("-v") || arg.equals("--version")){
                    System.out.println("ScreenCapture version "+VERSION);
                    System.out.println("\tGithub repo: https://github.com/krissrex/ScreenCapture/");
                    return;
                }
            }
        }


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
