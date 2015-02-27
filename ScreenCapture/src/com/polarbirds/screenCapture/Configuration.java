package com.polarbirds.screenCapture;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Configuration {
    
    private List<String[]> values;
    private String configurationFile;
    
    public Configuration(String configurationFile) throws FileNotFoundException{
        this.configurationFile = configurationFile;
        values = new ArrayList<String[]>();
        load();
    }
    
    private void load() throws FileNotFoundException{
        Scanner in = new Scanner(new File(configurationFile));
        while(in.hasNextLine()){
            values.add(in.nextLine().split("\\ "));
        }
        in.close();
    }
    
    public List<String[]> getValues(){
        return values;
    }

}
