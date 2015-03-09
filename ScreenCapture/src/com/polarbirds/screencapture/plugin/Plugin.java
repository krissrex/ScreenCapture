package com.polarbirds.screencapture.plugin;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tsh on 3/8/2015.
 */
public class Plugin {
    public enum Location { INTERNAL, EXTERNAL, DEFAULT };

    private Location location;
    private String name;
    private Object configuration;

    public Plugin(){
    }

    public Location getLocation(){
        return location;
    }

    public void setLocation(Location loc){
        location = loc;
    }

    public String getName(){
        return name;
    }

    public void setName(String in){
        name = in;
    }

    public Object getConfiguration(){
        return configuration;
    }

    public void setConfiguration(Object in){
        configuration = in;
    }
}
