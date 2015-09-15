/*
 * Main.java, part of the Find File Timer project
 * Created on Sep 15, 2015, 12:46:21 PM
 */
package net.psexton.findfiletimer;

import java.io.IOException;

/**
 *
 * @author PSexton
 */
public class Main {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException { new Main(); }
    
    public Main() throws IOException {
        int numFiles = 100;
        FileCreator fc = new FileCreator(numFiles);
        System.out.println("theDir=" + fc.getDir().toString());
        for (int i = 0; i < numFiles; i++) {
            System.out.println("name[" + i + "]=" + fc.getNames().get(i));
        }
    }
}
