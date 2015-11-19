package edu.ntu.cltk.android.manifest;

import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class ManifestProvider extends ManifestElement {

    public final static String TAG = "provider";
    protected List<ManifestIntentFilter> intentFilters = new ArrayList<ManifestIntentFilter>();
    private String grantUriPermission;
    private String metaData;
    private String pathPermission;

    protected ManifestProvider(Element element) {
    }

    public String getGrantUriPermission() {
        return grantUriPermission;
    }

    public void setGrantUriPermission(String grantUriPermission) {
        this.grantUriPermission = grantUriPermission;
    }

    public String getMetaData() {
        return metaData;
    }

    public void setMetaData(String metaData) {
        this.metaData = metaData;
    }

    public String getPathPermission() {
        return pathPermission;
    }

    public void setPathPermission(String pathPermission) {
        this.pathPermission = pathPermission;
    }


    public void addIntentFilter(ManifestIntentFilter intentFilter) {
        this.intentFilters.add(intentFilter);
    }

}
