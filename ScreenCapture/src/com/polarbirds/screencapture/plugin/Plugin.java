package com.polarbirds.screencapture.plugin;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Tsh on 3/8/2015.
 */
public class Plugin {
    private String path;
    private Object configuration;

    public Plugin(){
    }

    public URL getPath(){
        URL out = null;
        try {
            if(!path.contains("://")){
                if(path.substring(0, 2).equals("~/"))
                    out = new File(System.getProperty("user.home") + path.substring(1)).toURI().toURL();
                else
                    out = new File(path).toURI().toURL();
            } else {
                out = new URL(path);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return out;
    }

    public void setPath(String in){
        path = in;
    }

    public Object getConfiguration(){
        return configuration;
    }

    public void setConfiguration(Object in){
        configuration = in;
    }
}
