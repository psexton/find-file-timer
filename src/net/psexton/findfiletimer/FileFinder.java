/*
 * FileFinder.java, part of the Find File Timer project
 * Created on Sep 15, 2015, 1:43:37 PM
 */
package net.psexton.findfiletimer;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 *
 * @author PSexton
 */
public class FileFinder {
    
    /**
     * Given a full path, checks if the file exists
     * @param dir Parent directory
     * @param name File's name
     * @return True if file exists
     */
    public static boolean matchFullPath(Path dir, String name) {
        return Files.exists(dir.resolve(name));
    }
    
    /**
     * Given a partial path, checks if a file exists that matches
     * @param dir Parent directory
     * @param partialName Start of file's name
     * @return True if a matching file exists
     * @throws java.io.IOException
     */
    public static boolean matchPartialPath(Path dir, String partialName) throws IOException {
        // Create a DirectoryStream that filters based on a glob that starts with partialName
        try(DirectoryStream<Path> stream = Files.newDirectoryStream(dir, partialName + "*")) {
            for(Path entry: stream) {
                return true;
            }
        }
        return false;
    }
}
