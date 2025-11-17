package com.hust.kstn.models;
import java.util.ArrayList;
import java.util.List;
public class CompactDisc {
    private static int nbCDs = 0;
    private int id;
    private String title;
    private String category;
    private String artist;
    private double cost;
    private List<Track> tracks = new ArrayList<>();
    public CompactDisc(String title, String category, String artist, double cost, List<Track> tracks) {
        this.title = title;
        this.category = category;
        this.artist = artist;
        this.cost = cost;
        this.tracks = tracks;
        this.id = ++nbCDs;
    }
    public int getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getCategory() {
        return category;
    }
    public String getArtist() {
        return artist;
    }
    public double getCost() {
        return cost;
    }
    public List<Track> getTracks() {
        return tracks;
    }

    @Override
    public String toString() {
        return "CompactDisc[" + this.title + "]["
                + this.category + "]["
                + this.artist + "]["
                + this.cost + "]"
                + "\nTracks: " + this.tracks;
    }

    public int totalLength() {
        int total = 0;
        for (Track track : tracks) {
            total += track.getLength();
        }
        return total;
    }

    public void addTrack(Track track) {
        if (tracks.contains(track)) {
            System.out.println("Track " + track.getTitle() + " already exists");
        } else {
            tracks.add(track);
            System.out.println("Track " + track.getTitle() + " added successfully");
        }
    }

    public void removeTrack(Track track) {
        if (tracks.contains(track)) {
            tracks.remove(track);
            System.out.println("Track " + track.getTitle() + " removed successfully");
        } else {
            System.out.println("Track " + track.getTitle() + " does not exist");
        }
    }
}