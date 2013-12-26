package edu.ntu.cltk.file;

import java.io.File;

public class FileUtil {

	/**
	 * Check if the file exists
	 * @param path file name
	 * @return	true or false
	 */
	public static boolean fileExists(String path){
		File file = new File(path);
		return file.exists();
	}
	
	/**
	 * Create the folder if not exists
	 * @param path
	 * @return
	 */
	public static boolean createAllFolder(String folder){
		File file = new File(folder);
		if (!file.exists()){
			return file.mkdirs();
		}
		return true;
	}
	
	/**
	 * Get the [0] file directory, [1] file name
	 * @param fulPath
	 * @return
	 */
	public static String[] fileProfile(String fullPath){
		
		int sep = fullPath.lastIndexOf("/");
		if (sep == -1){
			sep = fullPath.lastIndexOf("\\");	//Try if the windows format
		}
		if (sep == -1){
			return new String[]{"./", fullPath};
		}else if (sep < fullPath.length() - 1){
			return new String[]{fullPath.substring(0, sep), fullPath.substring(sep+1)};
		}else{
			return new String[]{fullPath, null};
		}
	}
}