package com.polarbirds.screenCapture.plugin;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class PluginHandler {
    private List<PluginInterface> loadedPlugins;

    public PluginHandler(List<String[]> in) {
        loadedPlugins = new ArrayList<PluginInterface>();

        System.out.println("Loading plugins:");

        for(String[] plugin : in){
            String path = plugin[0];
            String className = plugin[1];
            try {
                ClassLoader cl;
                if (path.startsWith("internal")){
                    cl = getClass().getClassLoader();
                } else {
                    cl = new URLClassLoader(new URL[]{new URL(path)});
                }

                PluginInterface newPlugin = (PluginInterface) cl.loadClass(className).newInstance();
                loadedPlugins.add(newPlugin);

                System.out.println(newPlugin.Manifest()); //Debug
            } catch (ClassCastException | InstantiationException | IllegalAccessException ex){
                System.err.println("Failed to load \""+plugin+"\" as a plugin.");
                ex.printStackTrace();
            } catch (ClassNotFoundException | MalformedURLException ex){
                System.err.println("Could not find plugin \""+plugin+"\".");
                ex.printStackTrace();
            }

        }

        System.out.println("Loaded "+loadedPlugins.size()+" plugins.");
    }

    public List<PluginInterface> getLoadedPlugins(){
        return loadedPlugins;
    }
}