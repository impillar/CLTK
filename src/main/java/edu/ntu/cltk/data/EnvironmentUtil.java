package edu.ntu.cltk.data;

public class EnvironmentUtil {
    //From http://docs.oracle.com/javase/tutorial/essential/environment/sysprop.html
    //"file.separator"	Character that separates components of a file path. This is "/" on UNIX and "\" on Windows.
    //"java.class.path"	Path used to find directories and JAR archives containing class files. Elements of the class path are separated by a platform-specific character specified in the path.separator property.
    //"java.home"	Installation directory for Java Runtime Environment (JRE)
    //"java.vendor"	JRE vendor name
    //"java.vendor.url"	JRE vendor URL
    //"java.version"	JRE version number
    //"line.separator"	Sequence used by operating system to separate lines in text files
    //"os.arch"	Operating system architecture
    //"os.name"	Operating system name
    //"os.version"	Operating system version
    //"path.separator"	Path separator character used in java.class.path
    //"user.dir"	User working directory
    //"user.home"	User home directory
    //"user.name"	User account name

    public static String getFileSeparator() {
        return System.getProperty("file.separator");
    }

    public static String getJavaPath() {
        return System.getProperty("java.class.path");
    }

    public static String getJavaHome() {
        return System.getProperty("java.home");
    }

    public static String getJavaVendor() {
        return System.getProperty("java.vendor");
    }

    public static String getJavaVendorUrl() {
        return System.getProperty("java.vendor.url");
    }

    public static String getJavaVersion() {
        return System.getProperty("java.version");
    }

    public static String getLineSeperator() {
        return System.getProperty("line.separator");
    }

    public static String getOSArchitecture() {
        return System.getProperty("os.arch");
    }

    public static String getOSName() {
        return System.getProperty("os.name");
    }

    public static String getOSVersion() {
        return System.getProperty("os.version");
    }

    public static String getPathSeparator() {
        return System.getProperty("path.separator");
    }

    public static String getUserHome() {
        return System.getProperty("user.home");
    }

    public static String getUserDir() {
        return System.getProperty("user.dir");
    }

    public static String getUserName() {
        return System.getProperty("user.name");
    }
}
