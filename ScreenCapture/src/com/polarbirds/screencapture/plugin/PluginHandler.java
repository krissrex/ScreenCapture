package com.polarbirds.screencapture.plugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;

public class PluginHandler {
    private List<PluginInterface> loadedPlugins;

    public PluginHandler(List<Plugin> in) {
        loadedPlugins = new ArrayList<PluginInterface>();

        System.out.println("Loading plugins:");

        for(Plugin plugin : in){
            try {

                JarInputStream jarStream = new JarInputStream(plugin.getPath().openStream());
                ClassLoader cl = new URLClassLoader(new URL[]{plugin.getPath()});
                String className = jarStream.getManifest().getMainAttributes().getValue("plugin-class");

                PluginInterface newPlugin = (PluginInterface) cl
                        .loadClass(className)
                        .newInstance();

                newPlugin.setConfiguration(plugin.getConfiguration());

                loadedPlugins.add(newPlugin);
                System.out.println(newPlugin.manifest()); //Debug
            } catch (ClassCastException | InstantiationException | IllegalAccessException ex){
                System.err.println("Failed to load \"" + plugin.getPath() + "\" as a plugin.");
                ex.printStackTrace();
            } catch (ClassNotFoundException | MalformedURLException ex){
                System.err.println("Could not find plugin \"" + plugin.getPath() + "\". Please verify url.");
                ex.printStackTrace();
            } catch (IOException ex){
                System.err.println("An IO bound error occured while loading \"" + plugin.getPath() + "\".");
                ex.printStackTrace();
            } catch (Exception ex){
                /* Plugins may fail to initialize properly, which would
                 * leave them in an invalid state. We should therefore let them throw exceptions
                 * which will make it possible for us to ignore the plugin that failed.
                 */
                System.err.println("Unknown exception occured while loading \"" + plugin.getPath() + "\"" );
                ex.printStackTrace();
            }

        }

        System.out.println("Loaded "+loadedPlugins.size()+" plugin"+(loadedPlugins.size()==1?"":"s")+".");
    }

    public List<PluginInterface> getLoadedPlugins(){
        return loadedPlugins;
    }
}
