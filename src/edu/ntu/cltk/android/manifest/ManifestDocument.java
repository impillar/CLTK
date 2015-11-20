package edu.ntu.cltk.android.manifest;

import edu.ntu.cltk.data.ArrayUtil;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * http://developer.android.com/guide/topics/manifest/manifest-element.html
 */
public class ManifestDocument {

    Logger logger = Logger.getLogger(ManifestDocument.class);

    private String filePath;
    private String packageName;
    private String versionCode;
    private String versionName;
    private ManifestUsesSDK sdk;
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
            this.versionCode = root.attributeValue("android:versionCode");
            this.versionName = root.attributeValue("android:versionName");

            Object sdkObj = root.element(ManifestUsesSDK.TAG);
            if (sdkObj != null) {
                Element sdkElement = (Element) sdkObj;
                this.sdk = new ManifestUsesSDK(sdkElement);
            } else {
                this.sdk = new ManifestUsesSDK();
            }

            for (Object obj : root.elements()) {
                Element e = (Element) obj;
                String name = e.getName();
                if (name.equals(ManifestUsesPermission.TAG)) {
                    ManifestUsesPermission usesPermission = new ManifestUsesPermission(e);
                    this.usesPermissions.add(usesPermission);
                } else if (name.equals(ManifestPermission.TAG)) {
                    ManifestPermission mp = new ManifestPermission(e);
                    this.permissions.add(mp);
                } else if (name.equals(ManifestApplication.TAG)) {
                    //  only 1 application per document
                    this.application = new ManifestApplication(e, this);
                } else if (name.equals(ManifestUsesFeature.TAG)) {
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

    public String getVersionCode() {
        return versionCode;
    }

    public String getVersionName() {
        return versionName;
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

    public ManifestUsesSDK getSDK() {
        return sdk;
    }
}
