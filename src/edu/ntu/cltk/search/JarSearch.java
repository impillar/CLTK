package edu.ntu.cltk.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Note:
 * <p>
 * The canonical class name here is like org/xxx/yyy/zzz
 *
 * @author gzmeng
 */
public class JarSearch {

    /**
     * Get the canonical name of a class
     *
     * @param className
     * @return
     */
    private static String getCanonicalClassName(String className) {
        //First check if the suffix is a file extension or the class name is "class"
        if (className.endsWith(".class")) {
            if (className.contains("/")) className = className.substring(0, className.length() - 6);
        }
        return className.replaceAll("\\.", "/");
    }

    /**
     * Check if one class is contained in one jar file
     *
     * @param className
     * @param jarFile
     * @return
     */
    public static boolean isClassContainedByJar(String className, String jarFile) {

        className = getCanonicalClassName(className) + ".class";
        try {
            JarFile jar = new JarFile(jarFile);
            Enumeration<JarEntry> enumeration = jar.entries();
            while (enumeration.hasMoreElements()) {
                JarEntry je = enumeration.nextElement();
                /**
                 * One jar entry looks like org/xxx/yyy/zzz.class
                 */
                if (!je.isDirectory() && className.equals(je.getName())) {
                    return true;
                }
            }
            return false;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Return a list of jar files which contain the certain class
     *
     * @param className
     * @param jarFiles
     * @return
     */
    public static String[] classContainedByJar(String className, String... jarFiles) {
        List<String> targets = new ArrayList<String>();
        for (String jarFile : jarFiles) {
            if (isClassContainedByJar(className, jarFile)) {
                targets.add(jarFile);
            }
        }
        return targets.toArray(new String[targets.size()]);
    }
}
