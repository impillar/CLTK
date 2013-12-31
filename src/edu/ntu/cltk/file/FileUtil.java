package edu.ntu.cltk.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
	 * Append one message to a file
	 * @param filePath
	 * @param content
	 * @return
	 */
	public static boolean appendFile(String filePath, String content){
		return writeFile(filePath, content, "a");
	}
	/**
	 * Write one message to a file, if file exists, overwrite it
	 * @param filePath
	 * @param content
	 * @return
	 */
	public static boolean writeFile(String filePath, String content){
		return writeFile(filePath, content, "w");
	}
	/**
	 * Write one message to a file
	 * @param filePath
	 * @param content
	 * @param option		"a" or "w"
	 * @return
	 */
	public static boolean writeFile(String filePath, String content, String option){
		BufferedWriter bufWriter = null;
		try {
			bufWriter = new BufferedWriter(new FileWriter(filePath));
			if (option.contains("a")){
				bufWriter.append(content);
			}else{
				bufWriter.write(content);
			}
			return true;
		} catch (IOException e) {
			return false;
		}finally{
			if (bufWriter != null){
				try {
					bufWriter.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
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