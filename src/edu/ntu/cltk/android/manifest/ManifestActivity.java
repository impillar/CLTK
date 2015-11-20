package edu.ntu.cltk.android.manifest;

import org.apache.log4j.Logger;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

public class ManifestActivity extends ManifestComponent {

    private Logger logger = Logger.getLogger(ManifestActivity.class);

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

        allowEmbedded = e.attributeValue("allowEmbedded", "false").equalsIgnoreCase("true");
        allowTaskReparenting = e.attributeValue("allowTaskReparenting", "false").equalsIgnoreCase("true");
        alwaysRetainTaskState = e.attributeValue("alwaysRetainTaskState", "false").equalsIgnoreCase("true");
        //  FIXME get default
        String autoRemoveFromRecentsString = e.attributeValue("autoRemoveFromRecents");
        if (autoRemoveFromRecentsString == null) {
            logger.warn("no autoRemoveFromRecents, default to false");
            autoRemoveFromRecents = false;
        } else {
            autoRemoveFromRecents = autoRemoveFromRecentsString.equalsIgnoreCase("true");
        }

        clearTaskOnLaunch = e.attributeValue("clearTaskOnLaunch", "false").equalsIgnoreCase("true");
        enabled = e.attributeValue("enabled", "true").equalsIgnoreCase("true");
        excludeFromRecents = e.attributeValue("excludeFromRecents", "false").equalsIgnoreCase("true");
        //  TODO correct when Intent Filter exists
        if (intentFilters.size() != 0) {
            exported = e.attributeValue("exported", "true").equalsIgnoreCase("true");
        } else {
            exported = e.attributeValue("exported", "false").equalsIgnoreCase("true");
        }
        finishOnTaskLaunch = e.attributeValue("finishOnTaskLaunch", "false").equalsIgnoreCase("true");
        //  TODO
        hardwareAccelerated = e.attributeValue("hardwareAccelerated", "false").equalsIgnoreCase("true");
        multiprocess = e.attributeValue("multiprocess", "false").equalsIgnoreCase("true");
        noHistory = e.attributeValue("noHistory", "fasle").equalsIgnoreCase("true");
        relinquishTaskIdentity = e.attributeValue("relinquishTaskIdentity", "false").equalsIgnoreCase("true");
        stateNotNeeded = e.attributeValue("stateNotNeeded", "false").equalsIgnoreCase("true");


        configChanges = e.attributeValue("configChanges");
        documentLaunchMode = e.attributeValue("documentLaunchMode");
        launchMode = e.attributeValue("launchMode");
        name = e.attributeValue("name");
        parentActivityName = e.attributeValue("parentActivityName");
        permission = e.attributeValue("permission");
        process = e.attributeValue("process");
        screenOrientation = e.attributeValue("screenOrientation");
        taskAffinity = e.attributeValue("taskAffinity");
        windowSoftInputMode = e.attributeValue("windowSoftInputMode");


        String maxRecentsString = e.attributeValue("maxRecents");
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

    public List<ManifestIntentFilter> getIntentFilterList() {
        return intentFilters;
    }

}
