package edu.ntu.cltk.android.manifest;

public abstract class ManifestElement {

    protected ManifestElement() {
    }

    protected ManifestElement(String name) {
        this.name = name;
    }

    protected String name;

    public String getName() {
        return name;
    }

    public String toString() {
        return name;
    }

}
