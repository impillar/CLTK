package edu.ntu.cltk.android.manifest;

public class ManifestLibrary extends ManifestElement {

    public final static String TAG = "uses-library";

    public ManifestLibrary(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
