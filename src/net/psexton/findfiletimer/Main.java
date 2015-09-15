/*
 * Main.java, part of the Find File Timer project
 * Created on Sep 15, 2015, 12:46:21 PM
 */
package net.psexton.findfiletimer;

import java.io.IOException;
import java.util.Random;

/**
 *
 * @author PSexton
 */
public class Main {
    int NUM_FILES = 10000;
    int NUM_ITERS = 1000;
    FileCreator fc = new FileCreator(NUM_FILES);
    Random rng = new Random();
    
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException { new Main(); }
    
    public Main() throws IOException {
        System.out.println("theDir=" + fc.getDir().toString());
        System.out.println("numFiles=" + NUM_FILES);
        System.out.println("numIters=" + NUM_ITERS);
        
        if(rng.nextBoolean()) {
            timeMatchFull();
            timeMatchPartial();
        }
        else {
            timeMatchPartial();
            timeMatchFull();
        }
    }
    
    private void timeMatchFull() {
        long elapsedMillis = 0;
        int timesFound = 0;
        
        for(int i = 0; i < NUM_ITERS; i++) {
            // Check both existent and nonexistent paths with equal frequency
            String filename = fc.getNames().get(rng.nextInt(NUM_FILES));
            if(rng.nextBoolean()) {
                // half the time, make it a filename that doesn't exist
                filename = filename + "_foo";
            }
            filename = filename + "." + fc.getExtension();
            long startTime = System.currentTimeMillis();
            boolean exists = FileFinder.matchFullPath(fc.getDir(), filename);
            long stopTime = System.currentTimeMillis();
            if(exists)
                timesFound++;
            elapsedMillis += (stopTime - startTime);
        }
        
        double avgElapsedMillis = ((double) elapsedMillis) / ((double) NUM_ITERS);
        double avgFound = ((double) timesFound) / ((double) NUM_ITERS);
        
        System.out.println("matchFullPath \t avgMs=" + avgElapsedMillis + "\t avgFound=" + avgFound);
    }
    
    private void timeMatchPartial() throws IOException {
        long elapsedMillis = 0;
        int timesFound = 0;
        
        for(int i = 0; i < NUM_ITERS; i++) {
            // Check both existent and nonexistent paths with equal frequency
            String filename = fc.getNames().get(rng.nextInt(NUM_FILES));
            if(rng.nextBoolean()) {
                // half the time, make it a filename that doesn't exist
                filename = filename + "_foo";
            }
            long startTime = System.currentTimeMillis();
            boolean exists = FileFinder.matchPartialPath(fc.getDir(), filename);
            long stopTime = System.currentTimeMillis();
            if(exists)
                timesFound++;
            elapsedMillis += (stopTime - startTime);
        }
        
        double avgElapsedMillis = ((double) elapsedMillis) / ((double) NUM_ITERS);
        double avgFound = ((double) timesFound) / ((double) NUM_ITERS);
        
        System.out.println("matchPartialPath \t avgMs=" + avgElapsedMillis + "\t avgFound=" + avgFound);
    }
}
