package com.polarbirds.screenCapture.plugin;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Configuration {
    
    private List<String[]> values;
    private String configurationFile;
    private static final String DEFAULT_CONFIG = "# ScreenCapture config file.\n" +
            "# Consult https://github.com/krissrex/ScreenCapture-Plugins/wiki for help.\n" +
            "#\n" +
            "# Hashtags '#' mark a comment. \n" +
            "# Paths with spaces must use %20 instead of space.\n" +
            "# Internal plugins that are bundled with this project do not use the URL format, but start with the word internal.\n" +
            "# The plugin format is as follows:\n" +
            "# URL package.MainClass\n" +
            "# Here are some examples:" +
            "# file://C:"+File.separatorChar+"Path%20With%20Spaces"+File.separatorChar+"SamplePlugin.jar com.sample.plugin.package.PluginMain\n" +
            "# http://www.somedomain.com/SamplePlugin.jar com.sample.plugin.package.PluginMain\n\n" +
            "# The order of the plugins matter, as they are executed from top to bottom.\n" +
            "\n\n" +
            "# Default file saving plugin\n" +
            "internal com.polarbirds.screenCapture.plugin.bundled.FileSaver\n";
    
    public Configuration(String configurationFile) throws FileNotFoundException{
        this.configurationFile = configurationFile;
        values = new ArrayList<String[]>();
        load();
    }
    
    private void load() throws FileNotFoundException{
        File configFile = new File(configurationFile);

        if (configFile.exists() && configFile.isFile()) {
            if (!configFile.canRead()){
                System.err.println("Failed to read config \""+configFile+"\". \nDo you have the required permissions?");
                return;
            }

            Scanner in = new Scanner(configFile);
            while (in.hasNextLine()) {
                String line = in.nextLine();
                if (line.length() == 0 || line.charAt(0)=='#'){
                    //Skip blank lines and comment lines.
                    continue;
                }
                values.add(line.split("\\s+"));
            }
            in.close();
        } else {
            try {
                if (configFile.createNewFile()){
                    BufferedWriter writer = new BufferedWriter(new FileWriter(configFile));
                    writer.write(DEFAULT_CONFIG);
                    writer.close();
                    load();
                }
            } catch (IOException ex){
                System.err.println("Failed to create config file.");
            }
        }
    }
    
    public List<String[]> getValues(){
        return values;
    }

}
