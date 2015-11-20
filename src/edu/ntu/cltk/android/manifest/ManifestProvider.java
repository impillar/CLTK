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

    public ManifestProvider(Element e, ManifestDocument doc) {
        super(e);
        //        list?
        authorities = e.attributeValue("authorities");
        // TODO ensure correctness
        // rewrite ManifestComponent;
        String exportedString = e.attributeValue("exported");
        //        TODO the official doc is messy, need to confirm
        ManifestUsesSDK sdk = doc.getSDK();
        if (sdk.minSdkVersion < 17 || sdk.targetSdkVersion < 17) {
            exported = e.attributeValue("exported", "true").equalsIgnoreCase("true");
        } else {
            exported = e.attributeValue("exported", "false").equalsIgnoreCase("true");
        }
        grantUriPermission = e.attributeValue("grantUriPermissions", "false").equalsIgnoreCase("true");
        String initOrderString = e.attributeValue("initOrder");
        initOrder = Integer.parseInt(initOrderString);
        multiprocess = e.attributeValue("multiprocess", "false").equalsIgnoreCase("true");
        readPermission = e.attributeValue("readPermission");
        //        FIXME not sure
        syncable = e.attributeValue("syncable", "true").equalsIgnoreCase("true");
        writePermission = e.attributeValue("writePermission");

    }


}
