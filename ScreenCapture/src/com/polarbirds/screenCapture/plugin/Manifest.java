package com.polarbirds.screenCapture.plugin;

public class Manifest {

    private String author;
    private String name;
    private String description;
    private double version;

    public Manifest(String author, String name, String description, double version) {
        this.author = author;
        this.name = name;
        this.description = description;
        this.version = version;
    }

    public String getAuthor() {
        return author;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getVersion() {
        return version;
    }

    @Override
    public String toString(){
        return name +" ("+version+")" +"\n\t"+ description + "\nBy: "+author;
    }
}