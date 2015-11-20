package edu.ntu.cltk.android.manifest;

import org.dom4j.Element;

public class ManifestUsesFeature extends ManifestElement {

    public final static String TAG = "uses-feature";

    private String name;
    //  TODO determine to be true/false
    private String required;
    private String glEsVersion;


    public ManifestUsesFeature(Element element) {
        name = element.attributeValue("android:name");
        required = element.attributeValue("android:required");
        required = element.attributeValue("android:glEsVersion");
    }

    public String getName() {
        return name;
    }
}
