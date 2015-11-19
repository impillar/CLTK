package edu.ntu.cltk.android.manifest;

import org.dom4j.Element;


public class ManifestProvider extends ManifestComponent {

    public final static String TAG = "provider";
    private String authorities;
    private boolean grantUriPermission;
    private int initOrder;
    private boolean multiprocess;

    private String readPermission;
    private boolean syncable;
    private String writePermission;

    public ManifestProvider(Element e) {
        super(e);
        //        list?
        authorities = e.attributeValue("android:authorities");
        //        FIXME version changes for exported; shold rewrite ManifestComponent
        grantUriPermission = e.attributeValue("android:grantUriPermissions", "false").equalsIgnoreCase("true");
        String initOrderString = e.attributeValue("android:initOrder");
        initOrder = Integer.parseInt(initOrderString);
        multiprocess = e.attributeValue("android:multiprocess", "false").equalsIgnoreCase("true");
        readPermission = e.attributeValue("android:readPermission");
        //        FIXME not sure
        syncable = e.attributeValue("android:syncable", "true").equalsIgnoreCase("true");
        writePermission = e.attributeValue("android:writePermission");

    }


}
