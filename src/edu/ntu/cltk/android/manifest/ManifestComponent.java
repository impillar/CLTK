package edu.ntu.cltk.android.manifest;

import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains:
 * <p/>
 * - list of ManifestIntentFilter
 * - enabled
 * - exported
 * - name
 * - permission
 * - process
 * <p/>
 * and the constructor initializes them
 */
public abstract class ManifestComponent extends ManifestElement {

    public List<ManifestIntentFilter> getIntentFilters() {
        return intentFilters;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isExported() {
        return exported;
    }

    public String getName() {
        return name;
    }

    public String getPermission() {
        return permission;
    }

    public String getProcess() {
        return process;
    }

    protected boolean enabled;
    protected boolean exported;
    protected String name;
    protected String permission;
    protected String process;

    protected List<ManifestIntentFilter> intentFilters = new ArrayList<ManifestIntentFilter>();

    public ManifestComponent(Element e) {
        for (Object intentFilterObj : e.elements(ManifestIntentFilter.TAG)) {
            Element intentFilterEle = (Element) intentFilterObj;
            ManifestIntentFilter mif = new ManifestIntentFilter(intentFilterEle);
            intentFilters.add(mif);
        }
        enabled = e.attributeValue("android:enabled", "true").equalsIgnoreCase("true");
        // has intentFilters => exported defaults to true; otherwise, false
        if (intentFilters.size() != 0) {
            exported = e.attributeValue("android:exported", "true").equalsIgnoreCase("true");
        } else {
            exported = e.attributeValue("android:exported", "false").equalsIgnoreCase("true");
        }

        name = e.attributeValue("android:name");
        permission = e.attributeValue("android:permission");
        process = e.attributeValue("android:process");

    }

}
