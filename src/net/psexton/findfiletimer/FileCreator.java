/*
 * FileCreator.java, part of the Find File Timer project
 * Created on Sep 15, 2015, 12:48:21 PM
 */
package net.psexton.findfiletimer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

/**
 * Creates a crapload of files in a single directory.
 * Contains methods for getting the list of files and for cleaning up after. 
 * @author PSexton
 */
public class FileCreator {
    private final int MIN_FILE_SIZE_KB = 10;
    private final int MAX_FILE_SIZE_KB = 500;
    
    private final Path theDir;
    private final ArrayList<String> filenames;
    
    public FileCreator(int numFiles) throws IOException {
        // Generate a bunch of filenames
        filenames = populateFilenames(numFiles);
        // Populate a temp dir with files matching those names
        theDir = populateTempDir(filenames);
    }
    
    public Path getDir() {
        return theDir;
    }
    
    public ArrayList<String> getNames() {
        return filenames; // @TODO replace this with a defensive copy or a read only view
    }
    
    /**
     * Returns a List of random strings.
     * The entries will be type 4 UUIDs, and are guaranteed to be unique.
     * @param numEntries 
     */
    private ArrayList<String> populateFilenames(int numEntries) {
        ArrayList<String> list = new ArrayList<>(numEntries); // we know what the capacity needs to be
        int numCompleted = 0;
        while(numCompleted < numEntries) {
            String candidate = UUID.randomUUID().toString();
            // Deal with the unlikely possibility that we have a duplicate
            if(!list.contains(candidate)) {
                list.add(candidate);
                numCompleted++;
            }
        }
        return list;
    }
    
    private Path populateTempDir(ArrayList<String> names) throws IOException {
        // Create a directory in system temp dir to put this crap
        Path dir = Files.createTempDirectory("net.psexton.findfiletimer");
        for(String name : names) {
            Path file = Files.createFile(dir.resolve(name));
            fillFile(file);
        }
        return dir;
    }
    
    private void fillFile(Path theFile) throws IOException {
        Random rng = new Random();
        int fileSizeInKB = rng.nextInt(MAX_FILE_SIZE_KB - MIN_FILE_SIZE_KB) + MIN_FILE_SIZE_KB;
        
        // Write to the file in chunks of 1024 bytes
        
        byte[] buffer = new byte[1024];
        for(int i = 0; i < fileSizeInKB; i++) {
            rng.nextBytes(buffer); // fill buffer with random bytes
            Files.write(theFile, buffer, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.APPEND);
        }
    }
}
