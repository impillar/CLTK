package edu.ntu.cltk.android.manifest;

public class ManifestPermission extends ManifestElement {

    public final static String TAG = "permission";

    private final String name = "permission";

    private String permissoin;

    public ManifestPermission(String permission) {
        this.permissoin = permission;
    }

    @Override
    public String getName() {
        return name;
    }

}
