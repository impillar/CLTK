package edu.ntu.cltk.android.manifest;

import edu.ntu.cltk.data.ArrayUtil;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

public class ManifestApplication extends ManifestElement {

    public final static String TAG = "application";

    private ManifestDocument parent;

    private String backupAgent;  //  no default
    private String manageSpaceActivity;
    private String name;
    private String permission;
    private String process;
    private String requiredAccountType;
    private String restrictedAccountType;
    private String taskAffinity;

    private boolean allowBackup;
    private boolean debuggable;
    private boolean enabled;
    private boolean hasCode;
    private boolean hardwareAccelerated;
    private boolean killAfterRestore;
    private boolean largeHeap;
    private boolean persistent;
    private boolean restoreAnyVersion;  //  default to false
    private boolean supportsRtl;
    private boolean testOnly;
    private boolean usesCleartextTraffic;
    private boolean vmSafeMode;


    private List<ManifestActivity> activities = new ArrayList<ManifestActivity>();
    private List<ManifestService> services = new ArrayList<ManifestService>();
    private List<ManifestReceiver> receivers = new ArrayList<ManifestReceiver>();
    private List<ManifestProvider> providers = new ArrayList<ManifestProvider>();
    private List<ManifestLibrary> libraries = new ArrayList<ManifestLibrary>();


    public ManifestApplication(Element e, ManifestDocument parent) {
        this.parent = parent;

        backupAgent = Utility.getAttributeValue(e, "android:backupAgent");
        manageSpaceActivity = Utility.getAttributeValue(e, "android:manageSpaceActivity");
        name = Utility.getAttributeValue(e, "android:name");
        permission = Utility.getAttributeValue(e, "android:permission");
        process = Utility.getAttributeValue(e, "android:process");
        requiredAccountType = Utility.getAttributeValue(e, "android:requiredAccountType");
        restrictedAccountType = Utility.getAttributeValue(e, "android:restrictedAccountType");
        taskAffinity = Utility.getAttributeValue(e, "android:taskAffinity");


        allowBackup = Utility.getAttributeValue(e, "android:allowBackup", "true").equalsIgnoreCase("true");
        debuggable = Utility.getAttributeValue(e, "android:debuggable", "false").equalsIgnoreCase("true");
        enabled = Utility.getAttributeValue(e, "android:enabled", "true").equalsIgnoreCase("true");

        hasCode = Utility.getAttributeValue(e, "android:hasCode", "true").equalsIgnoreCase("true");
        ManifestUsesSDK sdk = parent.getSDK();
        if (sdk.minSdkVersion >= 14 || sdk.targetSdkVersion >= 14) {
            hardwareAccelerated = Utility.getAttributeValue(e, "android:hardwareAccelerated", "true").equalsIgnoreCase("true");
        } else {
            hardwareAccelerated = Utility.getAttributeValue(e, "android:hardwareAccelerated", "false").equalsIgnoreCase("true");
        }
        killAfterRestore = Utility.getAttributeValue(e, "android:killAfterRestore", "true").equalsIgnoreCase("true");
        //  TODO: ensure correctness
        largeHeap = Utility.getAttributeValue(e, "android:largeHeap", "false").equalsIgnoreCase("true");
        persistent = Utility.getAttributeValue(e, "android:persistent", "false").equalsIgnoreCase("true");
        restoreAnyVersion = Utility.getAttributeValue(e, "android:restoreAnyVersion", "false").equalsIgnoreCase("true");
        //  TODO ensure correctness
        supportsRtl = Utility.getAttributeValue(e, "android:supportsRtl", "false").equalsIgnoreCase("true");
        //  TODO
        testOnly = Utility.getAttributeValue(e, "android:testOnly", "false").equalsIgnoreCase("true");
        usesCleartextTraffic = Utility.getAttributeValue(e, "android:usesCleartextTraffic", "true").equalsIgnoreCase("true");
        vmSafeMode = Utility.getAttributeValue(e, "android:vmSafeMode", "false").equalsIgnoreCase("true");

        //        lists

        for (Object activityObject : e.elements(ManifestActivity.TAG)) {
            Element activity = (Element) activityObject;
            ManifestActivity mActivity = new ManifestActivity(activity);
            activities.add(mActivity);
        }

        for (Object activityAliasObject : e.elements(ManifestActivityAlias.TAG)) {
            Element activityAlias = (Element) activityAliasObject;
            ManifestActivityAlias maa = new ManifestActivityAlias(activityAlias);
            for (ManifestActivity mActivity : activities) {
                String activityName = mActivity.getName();
                if (activityName.equalsIgnoreCase(maa.getTargetActivity())) {
                    mActivity.mergeActivityAlias(maa);
                }
            }
        }

        for (Object serviceObject : e.elements(ManifestService.TAG)) {
            Element service = (Element) serviceObject;
            ManifestService mService = new ManifestService(service);
            services.add(mService);
        }

        for (Object providerObject : e.elements(ManifestProvider.TAG)) {
            Element provider = (Element) providerObject;
            ManifestProvider mProvider = new ManifestProvider(provider, parent);
            providers.add(mProvider);
        }

        for (Object receiverObject : e.elements(ManifestReceiver.TAG)) {
            Element receiver = (Element) receiverObject;
            ManifestReceiver mReceiver = new ManifestReceiver(receiver);
            receivers.add(mReceiver);
        }

        // unused
        for (Object libraryObject : e.elements(ManifestLibrary.TAG)) {
            Element library = (Element) libraryObject;
            ManifestLibrary mLibrary = new ManifestLibrary(library);
        }

    }


    public List<ManifestActivity> getActivities() {
        return activities;
    }

    public List<ManifestService> getServices() {
        return services;
    }

    public List<ManifestReceiver> getReceivers() {
        return receivers;
    }

    public List<ManifestProvider> getProviders() {
        return providers;
    }

    public List<ManifestLibrary> getLibraries() {
        return this.libraries;
    }

    @Override
    public String toString() {
        String sb = ("name: " + this.name + "\n") +
                "activities: " + ArrayUtil.serializeArray(activities, ", ") + "\n" +
                "service: " + ArrayUtil.serializeArray(services, ", ") + "\n" +
                "receivers: " + ArrayUtil.serializeArray(services, ", ") + "\n" +
                "providers: " + ArrayUtil.serializeArray(providers, ", ") + "\n" +
                "libraries: " + ArrayUtil.serializeArray(libraries, ", ") + "\n";
        return sb;
    }
}
