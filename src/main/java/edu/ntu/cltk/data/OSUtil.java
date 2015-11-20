package edu.ntu.cltk.data;

public class OSUtil {

    public static int UNKNOWN = 0;

    public static int WINDOWS = 1;

    public static int MAC = 2;

    public static int UNIX = 3;

    public static int SOLARIS = 4;
    public static String OS = System.getProperty("os.name").toLowerCase();
    private static int osType = 0;

    static {
        if (OS.indexOf("win") != -1) {
            osType = WINDOWS;
        } else if (OS.indexOf("mac") != -1 || OS.indexOf("darwin") != -1) {
            osType = MAC;
        } else if (OS.indexOf("nix") != -1 || OS.indexOf("nux") != -1
                || OS.indexOf("aix") != -1) {
            osType = UNIX;
        } else if (OS.indexOf("sunos") != -1) {
            osType = SOLARIS;
        }
    }

    public static boolean isWindows() {
        return osType == WINDOWS;
    }

    public static boolean isUnix() {
        return osType == UNIX;
    }

    public static boolean isMac() {
        return osType == MAC;
    }

    public static boolean isSolaris() {
        return osType == SOLARIS;
    }

    public static boolean isUnknown() {
        return osType == UNKNOWN;
    }
}
