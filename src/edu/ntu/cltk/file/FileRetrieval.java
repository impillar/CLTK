package edu.ntu.cltk.file;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Stack;

import edu.ntu.cltk.data.ArrayUtil;

public class FileRetrieval implements Iterator {

	public static int DFS_RETRIEVAL = 0;
	public static int BFS_RETRIEVAL = 1;

	public static int SORT_BY_TYPE_ASC = 1;
	public static int SORT_BY_TYPE_DEC = 2;
	public static int SORT_BY_NAME_ASC = 4;
	public static int SORT_BY_NAME_DEC = 8;
	public static int SORT_BY_LAST_MODIFIED_ASC = 16;
	public static int SORT_BY_LAST_MODIFIED_DEC = 32;
	public static int SORT_BY_SIZE_ASC = 64;
	public static int SORT_BY_SIZE_DEC = 128;
	
	private String directory = "";
	//File extensions will be changed to lower case for comparison
	private String[] fileExtensions;
	private String fileReg;
	private int count = 0;
	private Stack<INode> files;
	private int option = DFS_RETRIEVAL;
	private int sortBy = SORT_BY_NAME_ASC;
	
	public FileRetrieval(String direct){
		this.directory = direct;
		init();
	}
	//txt|jpg|mp4
	public FileRetrieval(String direct, String fileEx){
		this.directory = direct;
		fileExtensions = fileEx.split("\\|");
		for (int i = 0; i < fileExtensions.length; i++){
			fileExtensions[i] = fileExtensions[i].toLowerCase();
		}
		init();
	}
	
	public FileRetrieval(String direct, String fileRg, String fileEx){
		this.directory = direct;
		this.fileReg = fileRg;
		fileExtensions = fileEx.split("\\|");
		for (int i = 0; i < fileExtensions.length; i++){
			fileExtensions[i] = fileExtensions[i].toLowerCase();
		}
		init();
	}
	
	public void setSortBy(int sort){
		this.sortBy = sort;
	}
	
	public void setRetrievalOption(int option){
		this.option = option;
	}
	
	private void init(){
		files = new Stack<INode>();
		if (directory != null){
			files.add(new INode(INode.FOLDER, directory));
		}
	}
	
	public int count(){
		int count = 0;
		Stack<String> directs = new Stack<String>();
		directs.add(this.directory);
		
		while (!directs.empty()){
			String current = directs.pop();
			
			File filesUnderFolder = new File(current);
			for (int i = filesUnderFolder.listFiles().length - 1 ; i >= 0 ; i--){
				File f = filesUnderFolder.listFiles()[i];
				if (f.isFile() && satisfied(f.getName())){
					count++;
				}
			}
			for (int i = filesUnderFolder.listFiles().length - 1 ; i >= 0 ; i--){
				File f = filesUnderFolder.listFiles()[i];
				if (f.isDirectory()){
					try {
						directs.add(f.getCanonicalPath());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		directs = null;
		return count;
	}
	
	@Override
	public boolean hasNext() {
		if (option == DFS_RETRIEVAL){
			dfs();
		}else{
			bfs();
		}
		if (files.empty())	return false;
		return true;
	}

	/**
	 * Determine if the current file is satisfied with the condition: file extension or regex expression
	 * @return
	 */
	private boolean satisfied(String name){
		if (fileReg != null){
			//TODO: Need for implementation
		}
		if (fileExtensions != null && fileExtensions.length != 0){
			String extension = FileUtil.getFileExtension(name);
			if (extension!=null){
				extension = extension.toLowerCase();
			}
			if (!ArrayUtil.contains(fileExtensions, extension)){
				return false;
			}
		}
		return true;
	}

	@Override
	public Object next() {
		if (!files.empty() && files.peek().type == INode.FOLDER)	hasNext();
		if (files.empty()){
			return null;
		}
		return files.pop();
	}
	
	private File[] getUnderFiles(File file){
		File[] files = null;
		if (file.isDirectory()){
			files = file.listFiles();
			Arrays.sort(files, new Comparator<File>(){

				@Override
				public int compare(File f1, File f2) {
					if ((sortBy & SORT_BY_TYPE_ASC) == SORT_BY_TYPE_ASC || (sortBy & SORT_BY_TYPE_DEC) == SORT_BY_TYPE_DEC){
						if (f1.isDirectory() && f2.isFile()){
							if ((sortBy & SORT_BY_TYPE_ASC) == SORT_BY_TYPE_ASC)	return -1;
							else	return 1;
						}
						if (f1.isFile() && f2.isDirectory()){
							if ((sortBy & SORT_BY_TYPE_ASC) == SORT_BY_TYPE_ASC)	return 1;
							else	return -1;
						}
					}
					if ((sortBy & SORT_BY_NAME_ASC) == SORT_BY_NAME_ASC || (sortBy & SORT_BY_NAME_DEC) == SORT_BY_NAME_DEC){
						int c = f1.getName().compareTo(f2.getName());
						int d = ((sortBy & SORT_BY_NAME_ASC) == SORT_BY_NAME_ASC)?1:-1;
						if (c != 0)	return c*d;
					}
					if ((sortBy & SORT_BY_LAST_MODIFIED_ASC) == SORT_BY_LAST_MODIFIED_ASC || (sortBy & SORT_BY_LAST_MODIFIED_DEC) == SORT_BY_LAST_MODIFIED_DEC){
						int d = ((sortBy & SORT_BY_LAST_MODIFIED_ASC) == SORT_BY_LAST_MODIFIED_ASC)?-1:1;
						if (f1.lastModified() > f2.lastModified())	return -1*d;
						else if (f1.lastModified() < f2.lastModified())	return 1*d;
					}
					if ((sortBy & SORT_BY_SIZE_ASC) == SORT_BY_SIZE_ASC || (sortBy & SORT_BY_SIZE_DEC) == SORT_BY_SIZE_DEC){
						int d = ((sortBy & SORT_BY_SIZE_ASC) == SORT_BY_SIZE_ASC)?1:-1;
						if (f1.getTotalSpace() > f2.getTotalSpace())	return 1*d;
						else if (f1.getTotalSpace() < f2.getTotalSpace())	return -1*d;
					}
					return 0;
				}
				
			});
		}
		return files;
	}
	
	/*Directories:
	 *	A --- a1.txt
	 *     |- a2.txt
	 *     |- a3.txt
	 *  B --- b1.txt
	 *     |- b2.txt
	 *  C
	 *  d.txt
	 *  e.txt
	 *  
	 *Output:
	 *  a1.txt, a2.txt, a3.txt, b1.txt, b2.txt, d.txt, e.txt
	 */
	private void dfs(){
		while (!files.empty() && files.peek().type == INode.FOLDER){
			INode current = files.pop();
			
			File[] filesUnderFolder = getUnderFiles(new File(current.path));
			if (filesUnderFolder != null){
				
				for (int i = filesUnderFolder.length - 1 ; i >= 0; i--){
					File f = filesUnderFolder[i];
					if (f.isFile() && satisfied(f.getName())){
						try {
							files.add(new INode(INode.FILE, f.getCanonicalPath()));
						} catch (IOException e) {
							//Do nothing after release
							e.printStackTrace();
						}
					}
				}
				
				
				for (int i = filesUnderFolder.length - 1; i >= 0; i--){
					File f = filesUnderFolder[i];
					if (f.isDirectory()){
						try {
							files.add(new INode(INode.FOLDER, f.getCanonicalPath()));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
			
		}
	}
	
	/*Directories:
	 *	A --- a1.txt
	 *     |- a2.txt
	 *     |- a3.txt
	 *  B --- b1.txt
	 *     |- b2.txt
	 *  C
	 *  d.txt
	 *  e.txt
	 *  
	 *Output:
	 *  d.txt, e.txt, a1.txt, a2.txt, a3.txt, b1.txt, b2.txt
	 */
	private void bfs(){
		while (!files.empty() && files.peek().type == INode.FOLDER){
			INode current = files.pop();
			
			File[] filesUnderFolder = getUnderFiles(new File(current.path));
			if (filesUnderFolder != null){
			
				for (int i = filesUnderFolder.length - 1; i >= 0; i--){
					File f = filesUnderFolder[i];
					if (f.isDirectory()){
						try {
							files.add(new INode(INode.FOLDER, f.getCanonicalPath()));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				
				for (int i = filesUnderFolder.length - 1 ; i >= 0; i--){
					File f = filesUnderFolder[i];
					if (f.isFile() && satisfied(f.getName())){
						try {
							files.add(new INode(INode.FILE, f.getCanonicalPath()));
						} catch (IOException e) {
							//Do nothing after release
							e.printStackTrace();
						}
					}
				}
				
			}
		}
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}
	
	public class INode{
		public static final int FILE = 0;
		public static final int FOLDER = 1;
		public int type;
		public String path;
		
		public INode(int type, String path){
			this.type = type;
			this.path = path;
		}
		
		@Override
		public String toString(){
			return path;
		}
	}
}
