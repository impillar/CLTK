package edu.ntu.cltk.android.manifest;

import edu.ntu.cltk.data.ArrayUtil;
import org.dom4j.Element;
import org.dom4j.QName;

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

        backupAgent = e.attributeValue(new QName("android:backupAgent"));
        manageSpaceActivity = e.attributeValue(new QName("android:manageSpaceActivity"));
        name = e.attributeValue("android:name");
        permission = e.attributeValue("android:permission");
        process = e.attributeValue("android:process");
        requiredAccountType = e.attributeValue("android:requiredAccountType");
        restrictedAccountType = e.attributeValue("android:restrictedAccountType");
        taskAffinity = e.attributeValue("android:taskAffinity");


        allowBackup = e.attributeValue("android:allowBackup", "true").equalsIgnoreCase("true");
        debuggable = e.attributeValue("android:debuggable", "false").equalsIgnoreCase("true");
        enabled = e.attributeValue("android:enabled", "true").equalsIgnoreCase("true");

        hasCode = e.attributeValue("android:hasCode", "true").equalsIgnoreCase("true");
        ManifestUsesSDK sdk = parent.getSDK();
        if (sdk.minSdkVersion >= 14 || sdk.targetSdkVersion >= 14) {
            hardwareAccelerated = e.attributeValue("android:hardwareAccelerated", "true").equalsIgnoreCase("true");
        } else {
            hardwareAccelerated = e.attributeValue("android:hardwareAccelerated", "false").equalsIgnoreCase("true");
        }
        killAfterRestore = e.attributeValue("android:killAfterRestore", "true").equalsIgnoreCase("true");
        //  TODO: ensure correctness
        largeHeap = e.attributeValue("android:largeHeap", "false").equalsIgnoreCase("true");
        persistent = e.attributeValue("android:persistent", "false").equalsIgnoreCase("true");
        restoreAnyVersion = e.attributeValue("android:restoreAnyVersion", "false").equalsIgnoreCase("true");
        //  TODO ensure correctness
        supportsRtl = e.attributeValue("android:supportsRtl", "false").equalsIgnoreCase("true");
        //  TODO
        testOnly = e.attributeValue("android:testOnly", "false").equalsIgnoreCase("true");
        usesCleartextTraffic = e.attributeValue("android:usesCleartextTraffic", "true").equalsIgnoreCase("true");
        vmSafeMode = e.attributeValue("android:vmSafeMode", "false").equalsIgnoreCase("true");

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
                //                String activityName = mActivity.getName();
                //                if (activityName.equalsIgnoreCase(maa.getTargetActivity())) {
                //                    mActivity.mergeActivityAlias(maa);
                //                }
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
