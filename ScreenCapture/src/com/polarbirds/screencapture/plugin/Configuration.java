package com.polarbirds.screencapture.plugin;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Configuration {

    private List<Plugin> values;
    private String configurationFile;
    
    public Configuration(String configurationFile) {
        this.configurationFile = configurationFile;
        values = new ArrayList<Plugin>();
        load();
    }
    
    private void load() {
        ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
        try {
            Plugin[] plugs = mapper.readValue(new File(configurationFile), Plugin[].class);
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
