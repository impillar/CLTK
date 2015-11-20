package edu.ntu.cltk.android.manifest;

import java.io.FileNotFoundException;

public class ManifestManager {
    public static void main(String[] args) {
        if (args.length == 0) {
            throw new RuntimeException("Please run with AndroidManifest.xml as the 1st arg");

        }
        String fpath = args[0];
        try {
            ManifestDocument document = new ManifestDocument(fpath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(String.format("File %s does not exist\n", fpath));

        }
    }
}
