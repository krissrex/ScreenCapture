package com.polarbirds.screenCapture.plugin;

import java.awt.image.BufferedImage;

public interface PluginInterface {
    public Manifest manifest();
    public void run(BufferedImage img);
}
