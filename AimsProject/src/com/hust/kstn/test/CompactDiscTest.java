package com.hust.kstn.test;

import com.hust.kstn.models.CompactDisc;
import com.hust.kstn.models.Track;
import java.util.ArrayList;
import java.util.List;

public class CompactDiscTest {
    public static void main(String[] args) {
        List<Track> tracks = new ArrayList<>();
        tracks.add(new Track("Do toc 1", 210));
        tracks.add(new Track("Do toc 2", 180));
        tracks.add(new Track("Do toc 03", 240));

        CompactDisc cd = new CompactDisc("Do toc", "Rap", "Dohihi", 15.99, tracks);

        System.out.println(cd.toString());
        System.out.println("Total Length: " + cd.totalLength() + " seconds");

        Track newTrack = new Track("Track 4", 200);
        cd.addTrack(newTrack);
        System.out.println("After adding a new track:");
        System.out.println(cd.toString());
        cd.removeTrack(newTrack);
        cd.removeTrack(newTrack);
        cd.addTrack(new Track("Do toc 2", 180)); 
    }
}
