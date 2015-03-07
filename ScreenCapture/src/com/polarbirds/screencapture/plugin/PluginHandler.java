package com.polarbirds.screencapture.plugin;

import java.io.File;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarFile;

public class PluginHandler {
    private List<PluginInterface> loadedPlugins;

    public PluginHandler(List<String[]> in) {
        loadedPlugins = new ArrayList<PluginInterface>();

        System.out.println("Loading plugins:");

        for(String[] plugin : in){
            String firstValue = plugin[0];
            String secondValue = plugin[1];
            try {
                ClassLoader cl;
                PluginInterface newPlugin;
                if (firstValue.startsWith("internal")){

                    cl = getClass().getClassLoader();
                    newPlugin = (PluginInterface) cl.loadClass(secondValue).newInstance();

                } else if(firstValue.startsWith("default")){
                    File file = new File("plugins" + File.separator + secondValue);

                    cl = new URLClassLoader(new URL[]{file.toURI().toURL()});

                    JarFile jf = new JarFile(file);
                    String className = jf.getManifest().getMainAttributes().getValue("plugin-class");
                    newPlugin = (PluginInterface) cl.loadClass(className).newInstance();

                } else {

                    /*
                    I'm unsure about wheter we should get the entrypoint dynamically from the "plugin-class" variable,
                    or if we should let the user specify in the config.
                     */

                    cl = new URLClassLoader(new URL[]{new URL(firstValue)});
                    newPlugin = (PluginInterface) cl.loadClass(secondValue).newInstance();

                }

                loadedPlugins.add(newPlugin);
                System.out.println(newPlugin.manifest()); //Debug
            } catch (ClassCastException | InstantiationException | IllegalAccessException ex){
                System.err.println("Failed to load \""+plugin[1]+"\" as a plugin.");
                ex.printStackTrace();
            } catch (ClassNotFoundException | MalformedURLException ex){
                System.err.println("Could not find plugin \""+plugin[1]+"\" in path \""+plugin[0]+"\". Please verify url.");
                ex.printStackTrace();
            } catch (Exception ex){
                /* Plugins may fail to initialize properly, which would
                 * leave them in an invalid state. We should therefore let them throw exceptions
                 * which will make it possible for us to ignore the plugin that failed.
                 */
                System.err.println("Unknown exception occured while loading \""+plugin[1]+"\"" );
                ex.printStackTrace();
            }

        }

        System.out.println("Loaded "+loadedPlugins.size()+" plugin"+(loadedPlugins.size()==1?"":"s")+".");
    }

    public List<PluginInterface> getLoadedPlugins(){
        return loadedPlugins;
    }
}
