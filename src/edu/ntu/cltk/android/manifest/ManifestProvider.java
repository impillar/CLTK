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
        authorities = Utility.getAttributeValue(e ,"android:authorities");
        // TODO ensure correctness
        // rewrite ManifestComponent;
        String exportedString = Utility.getAttributeValue(e ,"android:exported");
        //        TODO the official doc is messy, need to confirm
        ManifestUsesSDK sdk = doc.getSDK();
        if (sdk.minSdkVersion < 17 || sdk.targetSdkVersion < 17) {
            exported = Utility.getAttributeValue(e ,"android:exported", "true").equalsIgnoreCase("true");
        } else {
            exported = Utility.getAttributeValue(e ,"android:exported", "false").equalsIgnoreCase("true");
        }
        grantUriPermission = Utility.getAttributeValue(e ,"android:grantUriPermissions", "false").equalsIgnoreCase("true");
        //        FIXME NOT sure
        String initOrderString = Utility.getAttributeValue(e ,"android:initOrder");
        initOrder = Utility.tryParseInt(initOrderString, 0);
        multiprocess = Utility.getAttributeValue(e ,"android:multiprocess", "false").equalsIgnoreCase("true");
        readPermission = Utility.getAttributeValue(e ,"android:readPermission");
        //        FIXME not sure
        syncable = Utility.getAttributeValue(e ,"android:syncable", "true").equalsIgnoreCase("true");
        writePermission = Utility.getAttributeValue(e ,"android:writePermission");

    }


}
