package edu.ntu.cltk.android.manifest;

import org.dom4j.Element;

public class ManifestUsesSDK extends ManifestElement {

    public final static String TAG = "uses-sdk";

    public final int minSdkVersion;
    public final int targetSdkVersion;
    public final int maxSdkVersion;


    public ManifestUsesSDK() {
        minSdkVersion = targetSdkVersion = maxSdkVersion = 1;
    }

    public ManifestUsesSDK(Element e) {
        String minString = e.attributeValue("android:minSdkVersion");
        minSdkVersion = Utility.tryParseInt(minString, 1);

        String targetString = e.attributeValue("android:targetSdkVersion");
        targetSdkVersion = Utility.tryParseInt(targetString, minSdkVersion);

        //        FIXME not used
        String maxString = e.attributeValue("android:maxSdkVersion");
        maxSdkVersion = Utility.tryParseInt(maxString, targetSdkVersion);

    }
}
