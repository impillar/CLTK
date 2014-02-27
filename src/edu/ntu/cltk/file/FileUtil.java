package edu.ntu.cltk.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

	public static final String FILE_OVERWRITE = "w";
	public static final String FILE_APPEND = "a";
	
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
			
			if (option.contains("a")){
				bufWriter = new BufferedWriter(new FileWriter(filePath, true));
			}else{
				bufWriter = new BufferedWriter(new FileWriter(filePath, true));			
			}
			bufWriter.write(content+"\n");
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
	
	public static final int FILE_COMBINE_SUFFIX = 0x0001;
	public static final int FILE_COMBINE_PREFIX = 0x0002;
	/**
	 * Combine the file name with a prefix or suffix
	 * @param fileName
	 * @param addition
	 * @param option
	 * @return
	 */
	public static String combineName(String fileName, String addition, int option){
		if (fileName == null)	return null;
		FileUtil.FileNode fileNode = new FileNode(fileName);
		if (option == FILE_COMBINE_SUFFIX){
			fileNode.fileName = fileNode.fileName + addition;
		}else if (option == FILE_COMBINE_PREFIX){
			fileNode.fileName = addition + fileNode.fileName;
		}
		return fileNode.toString();
	}
	
	public static List<String> readFileLineByLine(String fileName){
		
		List<String> res = new ArrayList<String>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(fileName));
			String line = null;
			while (null != (line = br.readLine())){
				res.add(line);
			}
		} catch (FileNotFoundException e) {
			return null;
		} catch (IOException e) {
			return null;
		} finally{
			if (br == null){
				try {
					br.close();
				} catch (IOException e) {
					//e.printStackTrace();
				}
			}
		}
		return res;
	}
	
	/**
	 * The class to present the info for a specific file
	 * For example, /home/pillar/test/class.txt will create an instance of FileNode which has
	 * 		Directory : /home/pillar/test
	 * 		FileName:	class
	 * 		Extension:	txt
	 * Where the extension only means the suffix of the file, not the real file type sometimes.
	 * If one file does not have directory or extension, like file test, then will create an instance
	 * 		Directory: 
	 * 		FileName:	test
	 * 		Extension:	
	 * @author pillar
	 *
	 */
	public static class FileNode{
		
		// The directory of the file
		String directory = null;
		// The name of the file
		String fileName = null;
		// The extension for the file
		String fileExtension = null;
		
		int osType = UNIX_TYPE;
		
		public static final int WINDOW_TYPE = 0x0001;
		public static final int UNIX_TYPE = 0x0002;
		
		public FileNode(String absoluteName){
			parse(absoluteName);
		}
		
		public FileNode(String directory, String fileName, String fileExtension, int osType){
			this.directory = directory;
			this.fileName = fileName;
			this.fileExtension = fileExtension;
			this.osType = osType;
		}
		
		private void parse(final String absoluteName){
			int i;
			for (i = absoluteName.length() - 1; i >= 0 ; i--){
				if (absoluteName.charAt(i)=='/' || absoluteName.charAt(i) == '\\'){
					break;
				}
			}
			String wholeFileName = new String(absoluteName);
			if (i > 0){
				// There is a directory for the file
				this.directory = absoluteName.substring(0, i);
				if (i + 1 < absoluteName.length())
					wholeFileName = absoluteName.substring(i+1);
			}
			
			for (i = wholeFileName.length() - 1; i >= 0; i--){
				if (wholeFileName.charAt(i) == '.'){
					break;
				}
			}
			if (i < 0 || i == wholeFileName.length() - 1){
				this.fileName = wholeFileName;
			}else{
				// There is an extension for the file
				this.fileName = wholeFileName.substring(0, i);
				this.fileExtension = wholeFileName.substring(i+1);
			}
		}
		
		@Override
		public String toString(){
			StringBuilder sb = new StringBuilder();
			String combine = "/";
			if (osType == WINDOW_TYPE){
				combine = "\\";
			}
			if (directory != null){
				sb.append(directory).append(combine);
			}
			if (fileName != null){
				sb.append(fileName);
			}
			if (fileExtension != null){
				sb.append(".").append(fileExtension);
			}
			return sb.toString();
		}
	}
	/**
	 * Delete a file
	 * @param fileName
	 * @return true/false
	 */
	public static boolean deleteFile(String fileName){
		File file = new File(fileName);
		return file.delete();
	}
	/**
	 * Clear the content of the file, the file still exists
	 * @param fileName
	 * @return
	 */
	public static boolean clearFile(String fileName){
		return writeFile(fileName, "", "w");
	}
}