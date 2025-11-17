package com.hust.kstn.models;
import java.util.ArrayList;
import java.util.List;
public class CompactDisc extends Disc {
    private String artist;
    private List<Track> tracks = new ArrayList<>();
    public CompactDisc(String title, String category, String artist, double cost, List<Track> tracks) {
        super(title, category, cost);
        this.artist = artist;
        this.tracks = tracks;
    }
    
    public String getArtist() {
        return artist;
    }
    
    public List<Track> getTracks() {
        return tracks;
    }

    @Override
    public String toString() {
        return super.toString() + "]["
                + this.artist + "]"
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