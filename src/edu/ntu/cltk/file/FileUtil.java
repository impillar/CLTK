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
	
}