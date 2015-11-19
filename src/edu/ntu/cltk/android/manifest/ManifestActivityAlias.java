package edu.ntu.cltk.android.manifest;

import org.dom4j.Element;

public class ManifestActivityAlias extends ManifestComponent {

    public final static String TAG = "activity-alias";

    private String targetActivity;

    public ManifestActivityAlias(Element e) {
        super(e);
        targetActivity = e.attributeValue("android:targetActivity");
    }


    public String getTargetActivity() {
        return targetActivity;
    }
}
