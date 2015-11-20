package edu.ntu.cltk.android.manifest;

import org.dom4j.Element;

public class ManifestUsesFeature extends ManifestElement {

    public final static String TAG = "uses-feature";

    private String name;
    //  TODO determine to be true/false
    private String required;
    private String glEsVersion;


    public ManifestUsesFeature(Element element) {
        name = element.attributeValue("name");
        required = element.attributeValue("required");
        required = element.attributeValue("glEsVersion");
    }

    public String getName() {
        return name;
    }
}
