package edu.ntu.cltk.android.manifest;

import org.dom4j.Element;


public class ManifestProvider extends ManifestElement {

    public final static String TAG = "provider";
    private String authorities;
    private boolean enabled;
    private boolean exported;
    private boolean grantUriPermission;
    private int initOrder;
    private boolean multiprocess;
    private String name;
    private String permission;
    private String process;
    private String readPermission;
    private boolean syncable;
    private String writePermission;

    public ManifestProvider(Element e) {
        //        list?
        authorities = e.attributeValue("android:authorities");
        enabled = e.attributeValue("android:enabled", "true").equalsIgnoreCase("true");
        //        FIXME version changes
        exported = e.attributeValue("android:exported", "true").equalsIgnoreCase("true");
        grantUriPermission = e.attributeValue("android:grantUriPermissions", "false").equalsIgnoreCase("true");
        String initOrderString = e.attributeValue("android:initOrder");
        initOrder = Integer.parseInt(initOrderString);
        multiprocess = e.attributeValue("android:multiprocess", "false").equalsIgnoreCase("true");
        name = e.attributeValue("android:name");
        permission = e.attributeValue("android:permission");
        process = e.attributeValue("android:process");
        readPermission = e.attributeValue("android:readPermission");
        //        FIXME not sure
        syncable = e.attributeValue("android:syncable", "true").equalsIgnoreCase("true");
        writePermission = e.attributeValue("android:writePermission");

    }


}
