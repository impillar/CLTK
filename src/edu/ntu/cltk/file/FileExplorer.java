package edu.ntu.cltk.file;

import java.io.IOException;
import java.util.Iterator;

public class FileExplorer {

	private String currentDirectory;
	
	public FileExplorer(String _directory) throws NotValidFolderException{
		if (!FileUtil.isFolder(_directory)){
			throw new NotValidFolderException(String.format("%s does not exists or is not a folder", _directory));
		}
		this.currentDirectory = _directory;
	}
	
	public class NotValidFolderException extends Exception{
		
		public NotValidFolderException(String message){
			super(message);
		}
	}

}
