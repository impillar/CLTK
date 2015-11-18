package edu.ntu.cltk.android.manifest;

import edu.ntu.cltk.data.ArrayUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


/**
 * It's a diligent class, which will parse the manifest file immediately after knowing its path.
 * Usage:
 * 1. Create a new object of ManifestManager
 * 2. Get the elements in the Mannifest file
 * - Permission
 * - Activity
 * - ...
 * Data Structure:
 * <p/>
 * ManifestManager
 * - manifestFile:  the location of manifest file
 * - application: Manifest application
 * -- List of permissions
 * -- List of activities
 * -- List of services
 * -- ...
 *
 * @author pillar
 */
@SuppressWarnings("unused")
public class ManifestManager {

    private String manifestFile;
    private ManifestApplication application;
    private List<ManifestPermission> permissions = new ArrayList<ManifestPermission>();
    private List<ManifestUsesPermission> usesPermissions = new ArrayList<ManifestUsesPermission>();
    private List<ManifestUsesFeature> usesFeatures = new ArrayList<ManifestUsesFeature>();
    private String packageName;
    private String versionCode;
    private String versionName;

    public static void main(String[] args) {
        if (args.length == 0) {
            throw new RuntimeException("Please run with AndroidManifest.xml as the 1st arg");

        }
        String fpath = args[0];
        try {
            ManifestManager mgr = new ManifestManager(fpath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(String.format("File %s does not exist\n", fpath));

        }
    }

    public ManifestManager(final String filePath) throws FileNotFoundException {
        this(new FileInputStream(filePath));
        this.manifestFile = filePath;
    }

    public ManifestManager(final InputStream is) {
        this.application = new ManifestApplication();
        parse(is);
    }

    public static InputStream getInputStreamFromApk(String apkFile) {

        File apkF = new File(apkFile);

        if (!apkF.exists()) {
            throw new RuntimeException(String.format("apk File %s does not exist\n", apkFile));
        }

        // get AndroidManifest
        InputStream manifestIS = null;
        ZipFile archive = null;
        try {
            archive = new ZipFile(apkF);
            for (Enumeration entries = archive.entries(); entries.hasMoreElements(); ) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                String entryName = entry.getName();
                // We are dealing with the Android manifest
                if (entryName.equals("AndroidManifest.xml")) {
                    manifestIS = archive.getInputStream(entry);
                    break;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error when looking for manifest in apk: " + e);
        } finally {
            if (archive != null)
                try {
                    archive.close();
                } catch (IOException e) {
                    System.err.println("Error when looking for manifest in apk: " + e);
                    System.exit(1);
                }
        }

        return manifestIS;
    }

    /**
     * @return manifest file name if exists; otherwise null
     */
    public String getManifestFile() {
        return manifestFile;
    }


    public ManifestApplication getApplication() {
        return application;
    }

    public void setApplication(ManifestApplication application) {
        this.application = application;
    }

    public List<ManifestPermission> getPermissions() {
        return permissions;
    }


    public List<ManifestUsesPermission> getUsesPermissions() {
        return usesPermissions;
    }


    public List<ManifestUsesFeature> getUsesFeatures() {
        return usesFeatures;
    }


    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    private void parse(InputStream is) {
        SAXReader reader = new SAXReader();
        try {
            Document document = reader.read(is);
            Element element = document.getRootElement();
            this.packageName = element.attributeValue("package");
            this.versionCode = element.attributeValue("versionCode");
            this.versionName = element.attributeValue("versionName");

            for (Iterator iter = element.elementIterator(); iter.hasNext(); ) {
                Element e = (Element) iter.next();
                if (e.getName().equals(ManifestUsesPermission.TAG)) {
                    this.usesPermissions.add(new ManifestUsesPermission(e.attributeValue("name")));
                } else if (e.getName().equals(ManifestPermission.TAG)) {
                    this.permissions.add(new ManifestPermission(e.attributeValue("name")));
                } else if (e.getName().equals(ManifestApplication.TAG)) {

                    String appName = Utility.getQName(this.packageName, e.attributeValue("name"));
                    application.setName(appName);

                    for (Iterator appIter = e.elementIterator(ManifestActivity.TAG); appIter.hasNext(); ) {
                        Element act = (Element) appIter.next();
                        String actName = Utility.getQName(this.packageName, act.attributeValue("name"));
                        ManifestActivity mActivity = new ManifestActivity(actName);

                        for (Iterator inIter = act.elementIterator(ManifestIntentFilter.TAG); inIter.hasNext(); ) {
                            Element filter = (Element) inIter.next();
                            String action = filter.element("action") == null ? null : filter.element("action").attributeValue("name");
                            String category = filter.elementText("category") == null ? null : filter.element("category").attributeValue("name");
                            String data = filter.element("data") == null ? null : filter.element("data").attributeValue("name");

                            mActivity.addIntentFilter(new ManifestIntentFilter(action, category, data));

                        }

                        application.addActivity(mActivity);
                    }
                    for (Iterator appIter = e.elementIterator(ManifestActivityAlias.TAG); appIter.hasNext(); ) {
                        Element act = (Element) appIter.next();
//                        FIXME may be wrong
                        String actName = Utility.getQName(this.packageName, act.attributeValue("name"));
                        ManifestActivityAlias mActivity = new ManifestActivityAlias(actName);

                        for (Iterator inIter = act.elementIterator(ManifestIntentFilter.TAG); inIter.hasNext(); ) {
                            Element filter = (Element) inIter.next();
                            ManifestIntentFilter mif = new ManifestIntentFilter(filter);
                            mActivity.addIntentFilter(mif);
                        }

                        application.addActivity(mActivity);
                    }
                    for (Iterator appIter = e.elementIterator(ManifestService.TAG); appIter.hasNext(); ) {
                        Element service = (Element) appIter.next();
                        String serviceName = Utility.getQName(this.packageName, service.attributeValue("name"));
                        ManifestService mService = new ManifestService(serviceName);

                        for (Iterator inIter = service.elementIterator(ManifestIntentFilter.TAG); inIter.hasNext(); ) {
                            Element ifElement = (Element) inIter.next();
                            ManifestIntentFilter intentFilter = new ManifestIntentFilter(ifElement);
                            mService.addIntentFilter(intentFilter);
                        }

                        application.addService(mService);
                    }
                    for (Iterator appIter = e.elementIterator(ManifestProvider.TAG); appIter.hasNext(); ) {

                        Element provider = (Element) appIter.next();
                        String providerName = Utility.getQName(this.packageName, provider.attributeValue("name"));
                        ManifestProvider mProvider = new ManifestProvider(providerName);

                        String grantUriPermission = provider.elementText("grant-uri-permission");
                        String metaData = provider.elementText("meta-data");
                        String pathPermission = provider.elementText("path-permission");

                        mProvider.setGrantUriPermission(grantUriPermission);
                        mProvider.setMetaData(metaData);
                        mProvider.setPathPermission(pathPermission);

                        application.addProvider(mProvider);

                    }
                    for (Iterator appIter = e.elementIterator(ManifestReceiver.TAG); appIter.hasNext(); ) {

                        Element receiver = (Element) appIter.next();
                        String receiverName = Utility.getQName(this.packageName, receiver.attributeValue("name"));
                        ManifestReceiver mReceiver = new ManifestReceiver(receiverName);

                        for (Iterator inIter = receiver.elementIterator(ManifestIntentFilter.TAG); inIter.hasNext(); ) {
                            Element filter = (Element) inIter.next();
                            ManifestIntentFilter intentFilter = new ManifestIntentFilter(filter);
                            mReceiver.addIntentFilter(intentFilter);
                        }

                        application.addReceiver(mReceiver);
                    }

                    for (Iterator fetIter = e.elementIterator(ManifestLibrary.TAG); fetIter.hasNext(); ) {
                        Element feature = (Element) fetIter.next();
                        application.addLibrary(new ManifestLibrary(feature.attributeValue("name")));
                    }
                } else if (e.getName().equals(ManifestUsesFeature.TAG)) {
                    this.usesFeatures.add(new ManifestUsesFeature(e.attributeValue("name")));
                }
            }
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        String s = "package: " + this.packageName + "\n" +
                "version: " + this.versionCode + "\n" +
                "uses-permission: " + ArrayUtil.serializeArray(usesPermissions, ", ") + "\n" +
                "permission: " + ArrayUtil.serializeArray(permissions, ", ") + "\n" +
                "uses-feature: " + ArrayUtil.serializeArray(usesFeatures, ", ") + "\n" +
                "application: " + application.toString() + "\n";
        return s;
    }

}
