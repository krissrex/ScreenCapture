package com.polarbirds.screenCapture.view;

import com.polarbirds.screenCapture.Controller;

import java.awt.*;

/**
 * Created by kristian on 02.03.15.
 */
public interface View {
    public void init();
    public void show();
    public void setCaptureListener(Controller.OnCaptureListener listener);
}
