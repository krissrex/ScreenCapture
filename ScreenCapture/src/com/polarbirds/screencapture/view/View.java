package com.polarbirds.screencapture.view;

import com.polarbirds.screencapture.Controller;

/**
 * Created by kristian on 02.03.15.
 */
public interface View {
    public void init();
    public void show();
    public void setCaptureListener(Controller.OnCaptureListener listener);
}
