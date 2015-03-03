package com.polarbirds.screencapture;

import com.polarbirds.screencapture.plugin.Configuration;
import com.polarbirds.screencapture.plugin.bundled.FileSaver;
import com.polarbirds.screencapture.view.View;
import com.polarbirds.screencapture.view.swing.CaptureFrame;

import java.awt.*;
import java.io.FileNotFoundException;

public class Main {

    public static final String VERSION = "1.0.1"; //TODO: Keep this updated

	public static void main(String[] args) {
        if (args.length > 0){
            for (String arg : args){
                if (arg.equals("-v") || arg.equals("--version")){
                    System.out.println("ScreenCapture version "+VERSION);
                    System.out.println("\tGithub repo: https://github.com/krissrex/ScreenCapture/");
                    return;
                }
                else if (arg.equals("-l") || arg.equals("--list")){
                    System.out.println("internal "+ FileSaver.class.getName());
                    return;
                }
                else if (arg.equals("-h") || arg.equals("--help")){
                    System.out.println("-v --version\tPrints the program version\n" +
                            "-l --list \t\tLists internal plugins.\n" +
                            "-h --help \t\tDisplays this help.");
                    return;
                }
                else {
                    System.out.println("Invalid argument. Use -h or --help for help.");
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
