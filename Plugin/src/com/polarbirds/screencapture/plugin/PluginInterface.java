package com.polarbirds.screencapture.plugin;

import java.awt.image.BufferedImage;

/**
 * The required interface for plugins.
 */
public interface PluginInterface {
    /**
     * Gets the {@link com.polarbirds.screencapture.plugin.Manifest} for the plugin,
     * which contains basic info about the plugin.<br/>
     * This is called when the plugin is loaded, to print information about it.
     * @return the {@link com.polarbirds.screencapture.plugin.Manifest}
     */
    public Manifest manifest();


    /**
     * Executes the desired code on the {@link java.awt.image.BufferedImage}. <br/><br/>
     * This is the main part of the plugin.<br/>
     * This is called when the image has been captured.<br/><br/>
     * The order of execution among plugins is defined in a config-file, and can not be
     * detected at run-time. <br/><br/>
     * If the order is required, please fill a request at
     * <a href="https://github.com/krissrex/ScreenCapture/issues">Github issues</a>.
     * @param img
     */
    public void run(BufferedImage img);
}
