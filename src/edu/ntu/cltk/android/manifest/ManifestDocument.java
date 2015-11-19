package edu.ntu.cltk.android.manifest;

import edu.ntu.cltk.data.ArrayUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * It's a diligent class, which will parse the manifest file immediately after knowing its path.
 * <p/>
 * Usage:
 * - Create a new object of ManifestDocument
 * - Get the elements in the Mannifest file
 * - Permission
 * - Activity
 * - ...
 * <p/>
 * Data Structure:
 * - filePath:  the location of manifest file
 * - application: Manifest application
 * -- List of permissions
 * -- List of activities
 * -- List of services
 * -- ...
 */
@SuppressWarnings("unused")
public class ManifestDocument {

    private String filePath;
    private String packageName;
    private String versionCode;
    private String versionName;
    private ManifestApplication application;
    private List<ManifestPermission> permissions = new ArrayList<ManifestPermission>();
    private List<ManifestUsesPermission> usesPermissions = new ArrayList<ManifestUsesPermission>();
    private List<ManifestUsesFeature> usesFeatures = new ArrayList<ManifestUsesFeature>();


    public ManifestDocument(final String fpath) throws FileNotFoundException {
        this(new FileInputStream(fpath));
        this.filePath = fpath;
    }

    public ManifestDocument(final InputStream is) {
        SAXReader reader = new SAXReader();
        try {
            Document document = reader.read(is);
            Element root = document.getRootElement();
            this.packageName = root.attributeValue("package");
            this.versionCode = root.attributeValue("versionCode");
            this.versionName = root.attributeValue("versionName");

            for (Iterator iter = root.elementIterator(); iter.hasNext(); ) {
                Element e = (Element) iter.next();
                if (e.getName().equals(ManifestUsesPermission.TAG)) {
                    ManifestUsesPermission usesPermission = new ManifestUsesPermission(e);
                    this.usesPermissions.add(usesPermission);
                } else if (e.getName().equals(ManifestPermission.TAG)) {
                    ManifestPermission mp = new ManifestPermission(e);
                    this.permissions.add(mp);
                } else if (e.getName().equals(ManifestApplication.TAG)) {
//                    only 1 application per document
                    this.application = new ManifestApplication(e, this.packageName);
                } else if (e.getName().equals(ManifestUsesFeature.TAG)) {
                    ManifestUsesFeature muf = new ManifestUsesFeature(e);
                    this.usesFeatures.add(muf);
                }
            }
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return manifest file name if exists; otherwise null
     */
    public String getfilePath() {
        return filePath;
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
