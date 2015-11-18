package edu.ntu.cltk.android.manifest;

public class ManifestPermission extends ManifestElement {

    public final static String TAG = "permission";

    private String permissoin;

    public ManifestPermission(String permission) {
        super(TAG);
        this.permissoin = permission;
    }

    @Override
    public String toString() {
        return name + " " + permissoin;
    }
}
