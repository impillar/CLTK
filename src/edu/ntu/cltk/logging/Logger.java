package edu.ntu.cltk.logging;

public class Logger {

	private static Logger logger;
	private String name;
	
	public static int INFO = 0;
	public static int WARNING = 1;
	public static int ERROR = 2;
	
	private Logger(String name){
		this.name = name;
	}
	
	public static Logger getLogger(String name){
		synchronized(Logger.class){
			if (logger == null){
				logger = new Logger(name);
			}
			return logger;
		}
	}
	
	public static Logger getLogger(){
		return getLogger(Logger.class.getName());
	}
	
	private void logMessage(int flag, String message){
		System.out.println(message);
	}
	
	public void info(String message){
		logMessage(Logger.INFO, message);
	}
	
	public void warning(String message){
		logMessage(Logger.WARNING, message);
	}
	
	public void error(String message){
		logMessage(Logger.ERROR, message);
	}
	
}