package edu.ntu.cltk.logging;

import java.io.IOException;

import edu.ntu.cltk.file.FileUtil;

public class Logger {

	private static Logger logger;
	private String name;
	private String logFile = null;
	
	public static int INFO = 0;
	public static int WARNING = 1;
	public static int ERROR = 2;
	
	public static int LOGGING_CONSOLE = 1;
	public static int LOGGING_FILE = 2;
	
	private int logType = 0;
	
	private Logger(String name, int logType){
		this.name = name;
		this.logType = logType;
	}
	
	public void setLogFile(String filePath){
		this.logFile = filePath;
	}
	
	public void setLogType(int logType){
		this.logType = logType;
	}
	
	public static Logger getLogger(String name, int logType){
		synchronized(Logger.class){
			if (logger == null){
				logger = new Logger(name, logType);
			}
			return logger;
		}
	}
	
	public static Logger getLogger(int logType){
		return getLogger(Logger.class.getName(), logType);
	}
	
	private void logMessage(int flag, String message) throws IOException{
		if ((logType & LOGGING_CONSOLE) != 0){
			System.out.println(message);
		}
		if ((logType & LOGGING_FILE) != 0){
			if (!FileUtil.writeFile(logFile, message, "a")){
				throw new IOException("Cannot write to the file " + logFile);
			}
		}
	}
	
	public void info(String message){
		try {
			logMessage(Logger.INFO, message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void warning(String message){
		try {
			logMessage(Logger.WARNING, message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void error(String message){
		try {
			logMessage(Logger.ERROR, message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}