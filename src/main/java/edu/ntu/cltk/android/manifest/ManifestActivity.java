package edu.ntu.cltk.android.manifest;


import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ManifestActivity extends ManifestComponent {

    final static private Logger logger = LoggerFactory.getLogger(ManifestComponent.class);

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
    private String screenOrientation;
    private String taskAffinity;
    private String windowSoftInputMode;

    public List<String> getAliases() {
        return aliases;
    }

    private List<String> aliases;

    private int maxRecents;

    protected List<ManifestIntentFilter> intentFilters = new ArrayList<ManifestIntentFilter>();


    public ManifestActivity(Element e) {
        super(e);

        allowEmbedded = Utility.getAttributeValue(e, "android:allowEmbedded", "false").equalsIgnoreCase("true");
        allowTaskReparenting = Utility.getAttributeValue(e, "android:allowTaskReparenting", "false").equalsIgnoreCase("true");
        alwaysRetainTaskState = Utility.getAttributeValue(e, "android:alwaysRetainTaskState", "false").equalsIgnoreCase("true");
        //  TODO check correctness
        autoRemoveFromRecents = Utility.getAttributeValue(e, "android:autoRemoveFromRecents", "false").equalsIgnoreCase("true");
        clearTaskOnLaunch = Utility.getAttributeValue(e, "android:clearTaskOnLaunch", "false").equalsIgnoreCase("true");
        enabled = Utility.getAttributeValue(e, "android:enabled", "true").equalsIgnoreCase("true");
        excludeFromRecents = Utility.getAttributeValue(e, "android:excludeFromRecents", "false").equalsIgnoreCase("true");
        //  TODO correct when Intent Filter exists
        if (intentFilters.size() != 0) {
            exported = Utility.getAttributeValue(e, "android:exported", "true").equalsIgnoreCase("true");
        } else {
            exported = Utility.getAttributeValue(e, "android:exported", "false").equalsIgnoreCase("true");
        }
        finishOnTaskLaunch = Utility.getAttributeValue(e, "android:finishOnTaskLaunch", "false").equalsIgnoreCase("true");
        //  TODO
        hardwareAccelerated = Utility.getAttributeValue(e, "android:hardwareAccelerated", "false").equalsIgnoreCase("true");
        multiprocess = Utility.getAttributeValue(e, "android:multiprocess", "false").equalsIgnoreCase("true");
        noHistory = Utility.getAttributeValue(e, "android:noHistory", "fasle").equalsIgnoreCase("true");
        relinquishTaskIdentity = Utility.getAttributeValue(e, "android:relinquishTaskIdentity", "false").equalsIgnoreCase("true");
        stateNotNeeded = Utility.getAttributeValue(e, "android:stateNotNeeded", "false").equalsIgnoreCase("true");


        configChanges = Utility.getAttributeValue(e, "android:configChanges");
        documentLaunchMode = Utility.getAttributeValue(e, "android:documentLaunchMode");
        launchMode = Utility.getAttributeValue(e, "android:launchMode");
        name = Utility.getAttributeValue(e, "android:name");
        parentActivityName = Utility.getAttributeValue(e, "android:parentActivityName");
        permission = Utility.getAttributeValue(e, "android:permission");
        process = Utility.getAttributeValue(e, "android:process");
        screenOrientation = Utility.getAttributeValue(e, "android:screenOrientation");
        taskAffinity = Utility.getAttributeValue(e, "android:taskAffinity");
        windowSoftInputMode = Utility.getAttributeValue(e, "android:windowSoftInputMode");


        String maxRecentsString = Utility.getAttributeValue(e, "android:maxRecents");
        if (maxRecentsString == null || maxRecentsString.equals("")) {
            maxRecents = 16;
        } else {
            maxRecents = Integer.parseInt(maxRecentsString);
            if (maxRecents > 50 || maxRecents < 1) {
                String msg = String.format("maxRecents=%d", maxRecents);
                throw new InvalidManifestException(msg);
            }
        }

    }

    public void mergeActivityAlias(ManifestActivityAlias alias) {
        assert name.equals(alias.getTargetActivity());
        boolean permissionEqual = Utility.equalsOrOneEmpty(permission, alias.permission);
        boolean enabledEqual = enabled == alias.enabled;
        boolean exportedEqual = exported == alias.exported;
        //  TODO check whether correct
        if (!(permissionEqual && enabledEqual && exportedEqual)) {
            String msg = String.format("inconsistent property for Activity=%s", name);
            //            FIXME
            //            throw new InvalidManifestException(msg);
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

    public List<ManifestIntentFilter> getIntentFilterList() {
        return intentFilters;
    }

}
