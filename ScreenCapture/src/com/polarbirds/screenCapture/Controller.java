package com.polarbirds.screenCapture;

import com.polarbirds.screenCapture.plugin.Configuration;
import com.polarbirds.screenCapture.plugin.PluginHandler;
import com.polarbirds.screenCapture.plugin.PluginInterface;
import com.polarbirds.screenCapture.view.View;


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


    /** Used by {@link com.polarbirds.screenCapture.Controller.OnCaptureListener} */
    public void captured(Rectangle bound){
        capturer.capture(bound);
        processImage(capturer.getImage());
    }


    protected void loadPlugins(){
        PluginHandler ph = new PluginHandler(config.getValues());
        plugins = ph.getLoadedPlugins();
    }

    protected void processImage(BufferedImage image){

        new Thread(){
            List<PluginInterface> plugins = Controller.this.plugins;

            @Override
            public void run() {
                for(PluginInterface i : plugins){
                    i.run(image);
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
