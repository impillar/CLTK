package edu.ntu.cltk.file;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Stack;

import edu.ntu.cltk.data.ArrayUtil;

public class FileRetrieval implements Iterator {

	private int DFS_RETRIEVAL = 0;
	private int BFS_RETRIEVAL = 1;
	
	private String directory = "";
	//File extensions will be changed to lower case for comparison
	private String[] fileExtensions;
	private String fileReg;
	private int count = 0;
	private Stack<INode> files;
	private int option = DFS_RETRIEVAL;
	
	public FileRetrieval(String direct){
		this.directory = direct;
		init();
	}
	//txt|jpg|mp4
	public FileRetrieval(String direct, String fileEx){
		this.directory = direct;
		fileExtensions = fileEx.split("|");
		for (int i = 0; i < fileExtensions.length; i++){
			fileExtensions[i] = fileExtensions[i].toLowerCase();
		}
		init();
	}
	
	public FileRetrieval(String direct, String fileRg, String fileEx){
		this.directory = direct;
		this.fileReg = fileRg;
		fileExtensions = fileEx.split("|");
		for (int i = 0; i < fileExtensions.length; i++){
			fileExtensions[i] = fileExtensions[i].toLowerCase();
		}
		init();
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
		return 0;
	}
	
	@Override
	public boolean hasNext() {
		return !files.empty();
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
			String extension = FileUtil.getFileExtension(name).toLowerCase();
			if (!ArrayUtil.contains(fileExtensions, extension)){
				return false;
			}
		}
		return true;
	}

	@Override
	public Object next() {
		if (option == DFS_RETRIEVAL){
			return dfs();
		}else{
			return bfs();
		}
	}
	
	private Object dfs(){
		INode current = files.pop();
		if (current.type == INode.FILE){
			return current.path;
		}else{
			while(!files.empty() && current.type != INode.FILE){
				File filesUnderFolder = new File(current.path);
				for (int i = filesUnderFolder.listFiles().length - 1 ; i >= 0 ; i--){
					File f = filesUnderFolder.listFiles()[i];
					if (f.isFile() && satisfied(f.getName())){
						try {
							files.add(new INode(INode.FILE, f.getCanonicalPath()));
						} catch (IOException e) {
							//Do nothing after release
							e.printStackTrace();
						}
					}
				}
				for (int i = filesUnderFolder.listFiles().length - 1 ; i >= 0 ; i--){
					File f = filesUnderFolder.listFiles()[i];
					if (f.isDirectory()){
						try {
							files.add(new INode(INode.FOLDER, f.getCanonicalPath()));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				current = files.pop();
			}
			return current.path;
		}
	}
	
	//TODO: Retrieval the files in directoires with BFS
	private Object bfs(){
		return null;
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}
	
	private class INode{
		public static final int FILE = 0;
		public static final int FOLDER = 1;
		public int type;
		public String path;
		
		public INode(int type, String path){
			this.type = type;
			this.path = path;
		}
	}
}
