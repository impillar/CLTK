package edu.ntu.cltk.logging;

import java.io.IOException;

import edu.ntu.cltk.file.FileUtil;

public class Logger {

	private static Logger logger;
	protected String name;
	protected String logFile = null;
	
	public static int INFO = 0;
	public static int WARNING = 1;
	public static int ERROR = 2;
	
	public static int LOGGING_CONSOLE = 1;
	public static int LOGGING_FILE = 2;
	
	protected int logType = 0;
	
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
	
	public void addLogType(int logType){
		this.logType |= logType;
	}
	
	public void delLogType(int logType){
		this.logType = (this.logType | logType) ^ logType;
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
	
	/**
	 * Log the message with a suspend time
	 * @param flag
	 * @param message
	 * @param suspendBefore
	 * @param suspendAfter
	 * @throws IOException
	 */
	private void logMessage(int flag, String message, long suspendBefore, long suspendAfter) throws IOException{
		if (suspendBefore > 0){
			try {
				Thread.sleep(suspendBefore);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if ((logType & LOGGING_CONSOLE) != 0){
			
			System.out.println(message);
			
		}
		if ((logType & LOGGING_FILE) != 0){
			if (!FileUtil.writeFile(logFile, message, "a")){
				throw new IOException("Cannot write to the file " + logFile);
			}
		}
		
		if (suspendAfter > 0){
			try {
				Thread.sleep(suspendAfter);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Log the information message
	 * @param message
	 */
	public void info(String message){
		info(message, 0, 0);
	}
	
	/**
	 * Log the message with a suspend time
	 * @param message
	 * @param suspendBefore		Suspend time before the logging
	 * @param suspendAfter		Suspend time after the logging
	 */
	public void info(String message, long suspendBefore, long suspendAfter){
		try {
			logMessage(Logger.INFO, message, suspendBefore, suspendAfter);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Log the warning message
	 * @param message
	 */
	public void warning(String message){
		warning(message, 0, 0);
	}
	
	/**
	 * Log the warning message with a suspend time
	 * @param message
	 * @param suspendBefore		Suspend time before the logging
	 * @param suspendAfter		Suspend time after the logging
	 */
	public void warning(String message, long suspendBefore, long suspendAfter){
		try {
			logMessage(Logger.WARNING, message, suspendBefore, suspendAfter);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Log the error message
	 * @param message
	 */
	public void error(String message){
		error(message, 0, 0);
	}
	
	/**
	 * Log the error message with a suspend time
	 * @param message
	 * @param suspendBefore		Suspend time before the logging
	 * @param suspendAfter		Suspend time after the logging
	 */
	public void error(String message, long suspendBefore, long suspendAfter){
		try {
			logMessage(Logger.ERROR, message, suspendBefore, suspendAfter);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}