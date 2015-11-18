package edu.ntu.cltk.android.manifest;

public class ManifestUsesFeature extends ManifestElement {

    public final static String TAG = "uses-feature";

    public ManifestUsesFeature(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public String toString() {
        return this.name;
    }

}
