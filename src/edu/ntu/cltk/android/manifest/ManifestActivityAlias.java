package edu.ntu.cltk.android.manifest;

import org.dom4j.Element;

public class ManifestActivityAlias extends ManifestActivity {

    public final static String TAG = "activity-alias";

    private String targetActivity;

    public ManifestActivityAlias(Element e) {
        enabled = e.attributeValue("android:enabled").equalsIgnoreCase("true");
        //  FIXME
        exported = e.attributeValue("android:exported", "true").equalsIgnoreCase("true");
        name = e.attributeValue("android:name");
        permission = e.attributeValue("android:permission");
        targetActivity = e.attributeValue("android:targetActivity");
        for (Object intentFilterObject : e.elements(ManifestIntentFilter.TAG)) {
            Element intentFilterElement = (Element) intentFilterObject;
            ManifestIntentFilter mif = new ManifestIntentFilter(intentFilterElement);
            intentFilters.add(mif);
        }
    }


    public String getTargetActivity() {
        return targetActivity;
    }
}
