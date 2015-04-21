package com.polarbirds.screencapture.plugin;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Configuration {

    private List<Plugin> values;
    private File configurationFile;
    
    public Configuration(String configurationFile) {
        this.configurationFile = new File(configurationFile);
        values = new ArrayList<Plugin>();
        load();
    }
    
    private void load() {
        /*

        Special just for kristian, as he seems to have strong
        opinions in favour of YAML even though he knows JSON
        is better.

        Anyways, JSON is valid YAML.

        */
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {

            if(!configurationFile.exists()){
                configurationFile.getParentFile().mkdirs();

                String line = System.getProperty("line.separator");
                String sampleConfig =
                        "["
                        + line
                        + "    {"
                        + line
                        + "        \"path\":\"http://polarbirds.com/repo/FileSaver.jar\","
                        + line
                        + "        \"configuration\":{}"
                        + line +
                        "    }"
                        + line
                        + "]";

                PrintWriter pw = new PrintWriter(configurationFile);
                pw.write(sampleConfig);
                pw.flush();
                pw.close();
            }

            Plugin[] plugs = mapper.readValue(configurationFile, Plugin[].class);
            values = Arrays.asList(plugs);
        } catch (IOException e) {
            // Do handling here
            e.printStackTrace();
        }
    }
    
    public List<Plugin> getValues(){
        return values;
    }

}
