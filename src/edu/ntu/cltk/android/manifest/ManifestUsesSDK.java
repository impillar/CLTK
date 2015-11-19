package edu.ntu.cltk.android.manifest;

import org.dom4j.Element;

public class ManifestUsesSDK extends ManifestElement {

    public final static String TAG = "uses-sdk";

    public final int minSdkVersion;
    public final int targetSdkVersion;
    public final int maxSdkVersion;

    public ManifestUsesSDK(Element e) {
        minSdkVersion = Integer.parseInt(e.attributeValue("android:minSdkVersion"));
        targetSdkVersion = Integer.parseInt(e.attributeValue("android:targetSdkVersion"));
        maxSdkVersion = Integer.parseInt(e.attributeValue("android:maxSdkVersion"));
    }
}
