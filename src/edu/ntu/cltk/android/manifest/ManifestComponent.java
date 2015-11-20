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
        enabled = Utility.getAttributeValue(e, "android:enabled", "true").equalsIgnoreCase("true");
        // has intentFilters => exported defaults to true; otherwise, false
        if (intentFilters.size() != 0) {
            exported = Utility.getAttributeValue(e, "android:exported", "true").equalsIgnoreCase("true");
        } else {
            exported = Utility.getAttributeValue(e, "android:exported", "false").equalsIgnoreCase("true");
        }

        name = Utility.getAttributeValue(e, "android:name");

        permission = Utility.getAttributeValue(e, "android:permission");
        process = Utility.getAttributeValue(e, "android:process");

    }

    public String toString() {
        String s = "enabled=" + enabled + "\n"
                + "exported=" + exported + "\n"
                + "name=" + String.valueOf(name) + "\n"
                + "permission=" + String.valueOf(permission) + "\n"
                + "process=" + String.valueOf(process) + "\n";
        return s;
    }

}
