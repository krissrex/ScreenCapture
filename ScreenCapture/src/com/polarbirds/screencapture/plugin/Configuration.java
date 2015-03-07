package com.polarbirds.screencapture.plugin;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.jar.JarFile;

public class Configuration {

    private List<String[]> values;
    private String configurationFile;

    private static final String sep = System.lineSeparator();
    private static final String DEFAULT_CONFIG = "# ScreenCapture configuration file." + sep +
            "# Consult https://github.com/krissrex/ScreenCapture-Plugins/wiki for help." + sep + sep +
            "# Hashtags '#' mark a comment." + sep +
            "# Paths with spaces must use %20 instead of space." + sep +
            "# Internal plugins that are bundled with this project do not use the URL format, but start with the word internal." + sep +
            "# Plugins put in the 'plugins'-directory can be used by entering the word default first, followed by the filename." + sep +
            "# The plugin format is as follows:" + sep +
            "# URL package.MainClass" + sep +
            "# Here are some examples:" + sep +
            "# file://C:"+File.separatorChar+"Path%20With%20Spaces"+File.separatorChar+"SamplePlugin.jar com.sample.plugin.package.PluginMain" + sep +
            "# http://www.somedomain.com/SamplePlugin.jar com.sample.plugin.package.PluginMain" + sep +
            "# default filename.jar" + sep +
            "# The order of the plugins matter, as they are executed from top to bottom." + sep + sep + sep +
            "# Default file saving plugin" + sep +
            "internal com.polarbirds.screencapture.plugin.bundled.FileSaver" + sep;
    
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
