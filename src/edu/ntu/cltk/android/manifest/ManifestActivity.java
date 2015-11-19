package edu.ntu.cltk.android.manifest;

import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class ManifestActivity extends ManifestElement {

    public final static String TAG = "activity";

    private boolean allowEmbedded;
    private boolean allowTaskReparenting;
    private boolean alwaysRetainTaskState;
    private boolean autoRemoveFromRecents;
    private boolean clearTaskOnLaunch;

    private boolean excludeFromRecents;
    private boolean finishOnTaskLaunch;
    private boolean hardwareAccelerated;
    private boolean multiprocess;
    private boolean noHistory;
    private boolean relinquishTaskIdentity;
    private boolean stateNotNeeded;

    private String configChanges;
    private String documentLaunchMode;
    private String launchMode;
    private String parentActivityName;
    private String process;
    private String screenOrientation;
    private String taskAffinity;
    private String windowSoftInputMode;

    private List<String> aliases;

    private int maxRecents;

    protected boolean enabled;
    protected boolean exported;

    protected String name;
    protected String permission;

    protected List<ManifestIntentFilter> intentFilters = new ArrayList<ManifestIntentFilter>();

    public String getName() {
        return name;
    }

    public ManifestActivity() {
        // preserved for ManifestActivityAlias construction
    }

    public ManifestActivity(Element e) {
        allowEmbedded = e.attributeValue("android:allowEmbedded", "false").equalsIgnoreCase("true");
        allowTaskReparenting = e.attributeValue("android:allowTaskReparenting", "false").equalsIgnoreCase("true");
        alwaysRetainTaskState = e.attributeValue("android:alwaysRetainTaskState", "false").equalsIgnoreCase("true");
        //  FIXME get default
        autoRemoveFromRecents = e.attributeValue("android:autoRemoveFromRecents").equalsIgnoreCase("true");
        clearTaskOnLaunch = e.attributeValue("android:clearTaskOnLaunch", "false").equalsIgnoreCase("true");
        enabled = e.attributeValue("android:enabled", "true").equalsIgnoreCase("true");
        excludeFromRecents = e.attributeValue("android:excludeFromRecents", "false").equalsIgnoreCase("true");
        //  TODO correct when Intent Filter exists
        exported = e.attributeValue("android:exported", "true").equalsIgnoreCase("true");
        finishOnTaskLaunch = e.attributeValue("finishOnTaskLaunch", "false").equalsIgnoreCase("true");
        //  TODO
        hardwareAccelerated = e.attributeValue("android:hardwareAccelerated", "false").equalsIgnoreCase("true");
        multiprocess = e.attributeValue("android:multiprocess", "false").equalsIgnoreCase("true");
        noHistory = e.attributeValue("android:noHistory", "fasle").equalsIgnoreCase("true");
        relinquishTaskIdentity = e.attributeValue("android:relinquishTaskIdentity", "false").equalsIgnoreCase("true");
        stateNotNeeded = e.attributeValue("android:stateNotNeeded", "false").equalsIgnoreCase("true");


        configChanges = e.attributeValue("android:configChanges");
        documentLaunchMode = e.attributeValue("android:documentLaunchMode");
        launchMode = e.attributeValue("android:launchMode");
        name = e.attributeValue("android:name");
        parentActivityName = e.attributeValue("android:parentActivityName");
        permission = e.attributeValue("android:permission");
        process = e.attributeValue("android:process");
        screenOrientation = e.attributeValue("android:screenOrientation");
        taskAffinity = e.attributeValue("android:taskAffinity");
        windowSoftInputMode = e.attributeValue("android:windowSoftInputMode");


        String maxRecentsString = e.attributeValue("android:maxRecents");
        if (maxRecentsString == null || maxRecentsString.equals("")) {
            maxRecents = 16;
        }
        maxRecents = Integer.parseInt(maxRecentsString);
        if (maxRecents > 50 || maxRecents < 1) {
            String msg = String.format("maxRecents=%d", maxRecents);
            throw new InvalidManifestException(msg);
        }

        for (Object intentFilterObj : e.elements(ManifestIntentFilter.TAG)) {
            Element intentFilterEle = (Element) intentFilterObj;
            ManifestIntentFilter mif = new ManifestIntentFilter(intentFilterEle);
            intentFilters.add(mif);
        }

    }

    public void mergeActivityAlias(ManifestActivityAlias alias) {
        assert name.equals(alias.getTargetActivity());
        boolean permissionEqual = permission.equalsIgnoreCase(alias.permission);
        boolean enabledEqual = enabled == alias.enabled;
        boolean exportedEqual = exported == alias.exported;
        //  TODO check whether correct
        if (!(permissionEqual && enabledEqual && exportedEqual)) {
            String msg = String.format("inconsistent property for Activity=%s", name);
            throw new InvalidManifestException(msg);
        }
        //  TODO check
        for (ManifestIntentFilter mif : alias.intentFilters) {
            if (!intentFilters.contains(mif)) {
                intentFilters.add(mif);
            }
        }
        if (!alias.name.equals(name)) {
            if (aliases == null) {
                aliases = new ArrayList<String>();
            }
            aliases.add(alias.name);
        }


    }

    public List<ManifestIntentFilter> getIntentFilters() {
        return intentFilters;
    }

}
