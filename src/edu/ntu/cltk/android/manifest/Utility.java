package edu.ntu.cltk.android.manifest;

@SuppressWarnings("unused")
public class Utility {
    public static String getSootName(String name) {
        return name.replace('.', '/');
    }

    public static String getQName(String packageName, String name) {
        if (name != null && name.startsWith(".") && packageName != null) {
            return packageName + name;
        }
        return name;
    }
}
