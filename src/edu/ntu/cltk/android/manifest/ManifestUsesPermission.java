package edu.ntu.cltk.android.manifest;

import org.dom4j.Element;

public class ManifestUsesPermission extends ManifestElement {

    public final static String TAG = "uses-permission";

    //  TODO parse to value
    private String maxSdkVersion;
    private String name;

    public ManifestUsesPermission(Element element) {
        name = element.attributeValue("android:name");
        maxSdkVersion = element.attributeValue("android:maxSdkVersion");
    }

    /**
     * @return may be null or ""
     */
    public String getMaxSdkVersion() {
        return maxSdkVersion;
    }

    public String getName() {
        return name;
    }
}
