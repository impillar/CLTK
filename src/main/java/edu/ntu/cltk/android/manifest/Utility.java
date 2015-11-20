package edu.ntu.cltk.android.manifest;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@SuppressWarnings("unused")
public class Utility {
    final static Logger logger = LoggerFactory.getLogger(Utility.class);
    public static final String manifestFileName = "AndroidManifest.xml";

    public static String getSootName(String name) {
        return name.replace('.', '/');
    }

    public static String getQName(String packageName, String name) {
        if (name != null && name.startsWith(".") && packageName != null) {
            return packageName + name;
        }
        return name;
    }

    public static String getAttributeValue(Element element, String name) {
        return getAttributeValue(element, name, null);
    }

    public static boolean equalsOrOneEmpty(String s1, String s2) {
        return s1 == null || s2 == null || s1.equals(s2);
    }

    public static String getAttributeValue(Element element, String name, String defaultVal) {
        if (name.contains(":")) {
            String[] splited = name.split(":");
            assert splited.length == 2;
            String simpleName = splited[1];
            Attribute attribute = element.attribute(simpleName);
            if (attribute == null) {
                logger.warn("{} defaults to {}", name, defaultVal);
                return defaultVal;
            }
            String elementQName = attribute.getQName().getQualifiedName();
            if (elementQName.equals(name)) {
                return attribute.getValue();
            } else {
                logger.error("{} exists, however {} does not", simpleName, name);
                return defaultVal;
            }
        } else {
            return element.attributeValue(name, defaultVal);
        }

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
                if (entryName.equals(manifestFileName)) {
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

    static int tryParseInt(String str, int defaultVal) {
        if (str == null) {
            return defaultVal;
        }
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            throw new InvalidManifestException(e.getMessage());
        }
    }
}
