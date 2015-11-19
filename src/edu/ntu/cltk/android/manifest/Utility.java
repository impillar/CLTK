package edu.ntu.cltk.android.manifest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@SuppressWarnings("unused")
public class Utility {
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
}
