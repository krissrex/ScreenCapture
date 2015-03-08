package com.polarbirds.screencapture.plugin;

import java.io.File;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;

import static com.polarbirds.screencapture.plugin.Plugin.Location.*;

public class PluginHandler {
    private List<PluginInterface> loadedPlugins;

    public PluginHandler(List<Plugin> in) {
        loadedPlugins = new ArrayList<PluginInterface>();

        System.out.println("Loading plugins:");

        for(Plugin plugin : in){
            try {
                ClassLoader cl;
                PluginInterface newPlugin;

                if (plugin.getLocation() == INTERNAL){

                    cl = getClass().getClassLoader();
                    newPlugin = (PluginInterface) cl
                            .loadClass(plugin.getName())
                            .getDeclaredConstructor(Map.class)
                            .newInstance(plugin.getConfiguration());

                } else if(plugin.getLocation() == DEFAULT){

                    File file = new File("plugins" + File.separator + plugin.getName());

                    cl = new URLClassLoader(new URL[]{file.toURI().toURL()});

                    JarFile jf = new JarFile(file);
                    String className = jf.getManifest().getMainAttributes().getValue("plugin-class");
                    newPlugin = (PluginInterface) cl
                            .loadClass(className)
                            .getDeclaredConstructor(Map.class)
                            .newInstance(plugin.getConfiguration());

                } else {

                    URL url = new URL(plugin.getName());

                    cl = new URLClassLoader(new URL[]{url});

                    JarInputStream jarStream = new JarInputStream(url.openStream());
                    String className = jarStream.getManifest().getMainAttributes().getValue("plugin-class");
                    newPlugin = (PluginInterface) cl
                            .loadClass(className)
                            .getDeclaredConstructor(Map.class)
                            .newInstance(plugin.getConfiguration());

                }

                loadedPlugins.add(newPlugin);
                System.out.println(newPlugin.manifest()); //Debug
            } catch (ClassCastException | InstantiationException | IllegalAccessException ex){
                System.err.println("Failed to load \"" + plugin.getName() + "\" as a plugin.");
                ex.printStackTrace();
            } catch (ClassNotFoundException | MalformedURLException ex){
                System.err.println("Could not find plugin \"" + plugin.getName() + "\". Please verify url.");
                ex.printStackTrace();
            } catch (Exception ex){
                /* Plugins may fail to initialize properly, which would
                 * leave them in an invalid state. We should therefore let them throw exceptions
                 * which will make it possible for us to ignore the plugin that failed.
                 */
                System.err.println("Unknown exception occured while loading \"" + plugin.getName() + "\"" );
                ex.printStackTrace();
            }

        }

        System.out.println("Loaded "+loadedPlugins.size()+" plugin"+(loadedPlugins.size()==1?"":"s")+".");
    }

    public List<PluginInterface> getLoadedPlugins(){
        return loadedPlugins;
    }
}
