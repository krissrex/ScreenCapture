package com.polarbirds.screencapture;

import com.polarbirds.screencapture.plugin.Configuration;
import com.polarbirds.screencapture.plugin.PluginHandler;
import com.polarbirds.screencapture.plugin.PluginInterface;
import com.polarbirds.screencapture.view.View;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Created by kristian on 02.03.15.
 */
public class Controller {

    public class OnCaptureListener{
        public void onCapture(Rectangle bound){
            captured(bound);
        }
    }

    private View view;
    private Configuration config;
    private List<PluginInterface> plugins;
    private ScreenCapturer capturer;


    public Controller(View view, Configuration config) throws AWTException{
        this.view = view;
        this.config = config;
        capturer = new ScreenCapturer();

        view.setCaptureListener(new OnCaptureListener());
    }


    /** Used by {@link com.polarbirds.screencapture.Controller.OnCaptureListener} */
    protected void captured(Rectangle bound){
        capturer.capture(bound);
        processImage(capturer.getImage());
    }


    protected void loadPlugins(){
        PluginHandler ph = new PluginHandler(config.getValues());
        plugins = ph.getLoadedPlugins();
    }

    protected void processImage(BufferedImage image){
        final BufferedImage finalImg = image;
        new Thread(){
            List<PluginInterface> plugins = Controller.this.plugins;
            @Override
            public void run() {
                for(PluginInterface i : plugins){
                    i.run(finalImg);
                }
            }
        }.start();

    }

    public void start(){
        loadPlugins();
        view.init();
        view.show();
    }


}
