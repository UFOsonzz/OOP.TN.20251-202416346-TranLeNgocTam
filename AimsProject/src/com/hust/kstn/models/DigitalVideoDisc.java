package com.hust.kstn.models;

public class DigitalVideoDisc extends Disc {
    private String director;
    private int length;

    public DigitalVideoDisc(String title, String category, double cost, String director, int length) {
        super(title, category, cost);
        this.director = director;
        this.length = length;
    }

    public String getDirector() {
        return director;
    }

    public int getLength() {
        return length;
    }

    @Override
    public String toString() {
        return super.toString() 
                + "[" + this.director + "]["
                + this.length + " seconds]";
    }
}
