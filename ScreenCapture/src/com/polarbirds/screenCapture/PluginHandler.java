package com.polarbirds.screenCapture;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class PluginHandler {
    private List<PluginInterface> loadedPlugins;
    
    public PluginHandler(List<String[]> in) throws MalformedURLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
        loadedPlugins = new ArrayList<PluginInterface>();
        
        for(String[] plugin : in){
            ClassLoader cl = new URLClassLoader(new URL[]{new URL(plugin[0])});
            PluginInterface newPlugin = (PluginInterface) cl.loadClass(plugin[1]).newInstance();
            loadedPlugins.add(newPlugin);
        }
    }
    
    public List<PluginInterface> getLoadedPlugins(){
        return loadedPlugins;
    }
}
