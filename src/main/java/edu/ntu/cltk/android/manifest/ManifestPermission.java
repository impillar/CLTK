package edu.ntu.cltk.android.manifest;

import org.dom4j.Element;

@SuppressWarnings("unused")
public class ManifestPermission extends ManifestElement {

    public final static String TAG = "permission";

    private String name;
    private String permissionGroup;
    private String protectionLevel;

    public ManifestPermission(Element element) {
        this.name = element.attributeValue("android:name");
        this.permissionGroup = element.attributeValue("android:permissionGroup");
        this.protectionLevel = element.attributeValue("android:protectionLevel");
    }

    public String getName() {
        return name;
    }

    public String getPermissionGroup() {
        return permissionGroup;
    }

    public String getProtectionLevel() {
        return protectionLevel;
    }
}
