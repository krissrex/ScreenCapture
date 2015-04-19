package com.polarbirds.screencapture;

import com.polarbirds.screencapture.plugin.Configuration;
import com.polarbirds.screencapture.view.View;
import com.polarbirds.screencapture.view.swing.CaptureFrame;

import java.awt.*;

public class Main {

    public static final String VERSION = "1.0.3"; //TODO: Keep this updated

	public static void main(String[] args) {

        String baseDirectory = System.getProperty("user.home")
                + System.getProperty("file.separator")
                + ".ScreenCapture"
                + System.getProperty("file.separator");

        String config = baseDirectory + "config.txt";

        for (String arg : args){
            if (arg.equals("-v") || arg.equals("--version")){
                System.out.println("ScreenCapture version " + VERSION);
                System.out.println("\tGithub repo: https://github.com/krissrex/ScreenCapture/");
                return;
            }
            else if (arg.equals("-h") || arg.equals("--help")){
                System.out.println("-v --version\tPrints the program version\n" +
                        "-h --help \t\tDisplays this help.\n"+
                        "-c=\"c:\\config.txt\" --config=\"c:\\config.txt\" \t\tSpecify path to config file"
                );
                return;
            }
            else if ((arg.startsWith("-c") || arg.startsWith("--config"))){
                String[] params = arg.split("=");
                if(params.length != 2){
                    return;
                }
                config = params[1];
            }
            else {
                System.out.println("Invalid argument. Use -h or --help for help.");
                return;
            }
        }

		System.out.println("Started using configuration file: " + config);

        try {
            View view = new CaptureFrame();
            Configuration configuration = new Configuration(config);

            new Controller(view, configuration).start();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
}
