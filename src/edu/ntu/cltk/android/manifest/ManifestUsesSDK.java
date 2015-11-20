package edu.ntu.cltk.android.manifest;

import org.dom4j.Element;

public class ManifestUsesSDK extends ManifestElement {

    public final static String TAG = "uses-sdk";

    public final int minSdkVersion;
    public final int targetSdkVersion;
    public final int maxSdkVersion;

    //    TODO make it utility
    int tryParseInt(String str, int defaultVal) {
        if (str == null) {
            return defaultVal;
        }
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            throw new InvalidManifestException(e.getMessage());
        }
    }

    public ManifestUsesSDK() {
        minSdkVersion = targetSdkVersion = maxSdkVersion = 1;
    }

    public ManifestUsesSDK(Element e) {
        String minString = e.attributeValue("minSdkVersion");
        minSdkVersion = tryParseInt(minString, 1);

        String targetString = e.attributeValue("targetSdkVersion");
        targetSdkVersion = tryParseInt(targetString, minSdkVersion);

        //        FIXME not used
        String maxString = e.attributeValue("maxSdkVersion");
        maxSdkVersion = tryParseInt(maxString, targetSdkVersion);

    }
}
