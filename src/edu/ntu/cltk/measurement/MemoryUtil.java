package edu.ntu.cltk.measurement;

import java.lang.instrument.Instrumentation;

public class MemoryUtil {

	public static long B  = 1024;
	public static long KB = 1024;
	public static long MB = 1024 * 1024;
	public static long GB = 1024 * 1024 * 1024;
	public static long TB = 1024 * 1024 * 1024 * 1024;
	public static long PB = 1024 * 1024 * 1024 * 1024 * 1024;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	private static volatile Instrumentation instrumentation;

    public static void premain(String args, Instrumentation inst) {
        instrumentation = inst;
    }

    public static long getObjectSize(Object o) {
        return instrumentation.getObjectSize(o);
    }

    private static MemoryUtil mu = null;
    private Runtime runtime = null;
    
    private MemoryUtil(){
    	runtime = Runtime.getRuntime();
    }
    
    public synchronized MemoryUtil v(){
    	if (mu == null){
    		mu = new MemoryUtil();
    	}
    	return mu;
    }
    
    public long totalMemory(){
    	return runtime.totalMemory();
    }
    
    public long freeMemory(){
    	return runtime.freeMemory();
    }
    
    public long maxMemory(){
    	return runtime.maxMemory();
    }
    
    
}
