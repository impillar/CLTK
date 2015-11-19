package edu.ntu.cltk.android.manifest;

import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

public class IntentFilterContainer extends ManifestElement {

    protected boolean enabled;
    protected boolean exported;
    protected String name;
    protected String permission;
    protected String process;

    protected List<ManifestIntentFilter> intentFilters = new ArrayList<ManifestIntentFilter>();

    public IntentFilterContainer(Element e) {
        for (Object intentFilterObj : e.elements(ManifestIntentFilter.TAG)) {
            Element intentFilterEle = (Element) intentFilterObj;
            ManifestIntentFilter mif = new ManifestIntentFilter(intentFilterEle);
            intentFilters.add(mif);
        }
        enabled = e.attributeValue("android:enabled", "true").equalsIgnoreCase("true");
        //        FIXME
        exported = e.attributeValue("android:exported", "true").equalsIgnoreCase("true");
        name = e.attributeValue("android:name");
        permission = e.attributeValue("android:permission");
        process = e.attributeValue("android:process");

    }

}
