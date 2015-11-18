package edu.ntu.cltk.android.manifest;

public class ManifestUsesPermission extends ManifestElement {

    public final static String TAG = "uses-permission";

    private String permission;

    public ManifestUsesPermission(String name) {
        super(TAG);
        this.permission = name;
    }
}
