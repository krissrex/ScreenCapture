package com.polarbirds.screencapture.plugin.bundled;

import com.polarbirds.screencapture.plugin.Manifest;
import com.polarbirds.screencapture.plugin.PluginInterface;

import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.*;


/**
 * Created by kristian on 27.02.15.
 */
public class FileSaver implements PluginInterface {

    private JFrame frame;
    private FileDialog dialog;
    private Manifest manifest;

    public FileSaver(Map<String, String> configuration) {
        manifest = new Manifest("Kristian Rekstad", "File saver", "Saves images to local file.", 1.0d);
    }

    @Override
    public Manifest manifest() {
        return manifest;
    }

    @Override
    public void run(BufferedImage img) {
        final BufferedImage finalImg = img;

        Runnable fileSaveDialog = new Runnable() {
            @Override
            public void run() {
                try {
                    new FileSaveDialog(finalImg).query();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        try {
            EventQueue.invokeAndWait(fileSaveDialog);
        } catch (InterruptedException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    private class FileSaveDialog {

        private final BufferedImage image;
        public FileSaveDialog(BufferedImage img){
            image = img;
            init();
        }

        private void init() {
            frame = new JFrame();
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            dialog = new FileDialog(frame, "Save screenshot", FileDialog.SAVE);

            //TODO: Filename filter is not very flexible
            dialog.setFilenameFilter(new FilenameFilter(){
                @Override
                public boolean accept(File dir, String name) {
                    return name.endsWith(".png") || name.endsWith(".jpg");
                }
            });
        }

        private void query() {
            dialog.setVisible(true);
            if (dialog.getFile() == null) {
                frame.dispose();
                dialog.dispose();
                return;
            }
            String fileName = dialog.getFile();

            //TODO: Filename filter is not very flexible
            boolean hasExtension = fileName.endsWith(".png") || fileName.endsWith(".jpg");
            String extension = "png";
            if (hasExtension){
                extension = fileName.substring(fileName.lastIndexOf('.')+1);
            }

            String path = dialog.getDirectory() + fileName + (hasExtension? "":".png");
            File file = new File(path);

            try {
                ImageIO.write(image, extension, file);
                System.out.println("Saved file to \n\t"+path);
            } catch (IOException e) {
                System.err.println("Failed to save file. "+e.getMessage());
                e.printStackTrace();
            } finally {
                frame.dispose();
                dialog.dispose();
            }
        }
    }

}
