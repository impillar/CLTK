package edu.ntu.cltk.android.manifest;

public class ManifestUsesPermission extends ManifestElement {

    public final static String TAG = "uses-permission";

    private final String name = "uses-permissoin";

    private String permission;

    public ManifestUsesPermission(String name) {
        this.permission = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
