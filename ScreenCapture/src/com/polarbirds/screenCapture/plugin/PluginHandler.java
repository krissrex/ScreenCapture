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
            } catch (Exception ex){
                /* Plugins may fail to initialize properly, which would
                 * leave them in an invalid state. We should therefore let them throw exceptions
                 * which will make it possible for us to ignore the plugin that failed.
                 */
                System.err.println("Other exception occured while loading \""+plugin+"\"" );
                ex.printStackTrace();
            }

        }

        System.out.println("Loaded "+loadedPlugins.size()+" plugin"+(loadedPlugins.size()>1?"s":"")+".");
    }

    public List<PluginInterface> getLoadedPlugins(){
        return loadedPlugins;
    }
}
