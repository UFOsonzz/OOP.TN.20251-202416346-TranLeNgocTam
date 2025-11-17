package com.hust.kstn.garbage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class NoGarbage {
    public static void main(String[] args) throws IOException {
        String filename = "test.exe";
        byte[] inputBytes = { 0 };
        long startTime, endTime;

        inputBytes = Files.readAllBytes(Paths.get(filename));
        startTime = System.currentTimeMillis();
        String outputString = "";
        StringBuilder temp = new StringBuilder();
        for (int i = 1; i <= 1000000; i++) {
            for (byte b : inputBytes) {
                temp.append((char)b);
            }
        }
        outputString = temp.toString();
        endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
    }
}

