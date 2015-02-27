package com.polarbirds.screenCapture;

import java.awt.image.BufferedImage;

public interface PluginInterface {
    public Manifest Manifest();
    public void run(BufferedImage img);
}
