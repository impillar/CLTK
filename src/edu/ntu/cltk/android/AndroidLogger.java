package edu.ntu.cltk.android;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import android.util.Log;
import edu.ntu.cltk.file.FileUtil;

public class AndroidLogger {

	public static int WRITE_FILE = 1;
	public static int WRITE_CONSOLE = 2;
	private static AndroidLogger instance;
	private static Object lock = new Object();
	private String filePath = null;
	private int logType;
	private String tag;

	private AndroidLogger(String filePath, String tag, int logType) {
		this.filePath = filePath;
		this.tag = tag;
		this.logType = logType;
	}

	private AndroidLogger(String filePath, int logType) {
		this.filePath = filePath;
		this.logType = logType;
		this.tag = AndroidLogger.class.getName();
	}

	private AndroidLogger(int logType) {
		this.tag = AndroidLogger.class.getName();
		this.filePath = null;
		this.logType = logType;
	}

	public static AndroidLogger getLogger(int logType) {
		if (instance == null) {
			synchronized (lock) {
				instance = new AndroidLogger(logType);
			}
		}
		return instance;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public void log(String message) {
		if ((logType | WRITE_FILE) != 0) {
			String[] args = FileUtil.fileProfile(filePath);
			if (args[1] != null) {
				FileUtil.createAllFolder(args[0]);
				boolean append = FileUtil.fileExists(filePath);
				BufferedWriter bWriter = null;
				try {
					bWriter = new BufferedWriter(new FileWriter(filePath, append));
					bWriter.write(message);
					bWriter.flush();
				} catch (IOException e) {
					Log.e(tag, e.getMessage());
				}finally{
					if (bWriter != null){
						try {
							bWriter.close();
						} catch (IOException e) {
							Log.e(tag, e.getMessage());
						}
					}
				}
			}
		} else if ((logType | WRITE_CONSOLE) != 0) {
			Log.v(tag, message);
		}
	}

}
