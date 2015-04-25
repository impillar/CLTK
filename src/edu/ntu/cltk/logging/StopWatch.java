package edu.ntu.cltk.logging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import edu.ntu.cltk.exception.CLTKNotKeyInMapException;

public class StopWatch {
	
	private List<long[]> slots = new ArrayList<long[]>();
	private Map<String, Integer> nameOfSlots = new HashMap<String, Integer>();
	public static final int READY = 0;
	public static final int RUNNING = 1;
	public static final int PAUSED = 2;
	public static final int FINISHED = 3;
	private int state = 0;
	
	private static StopWatch sw = null;
	
	public static StopWatch v(){
		if (sw == null){
			sw = new StopWatch();
		}
		return sw;
	}
	
	private StopWatch(){
		
	}
	
	public void start(){
		
		//This method implicitly calls the method to reset the clock
		reset();
		
		state = RUNNING;
		long[] start = new long[]{System.currentTimeMillis(), 0};
		slots.add(start);
	}

	public void finish(){
		//Fix Bug: if the stopwatch is not started, the slots.get(-1) will throw an exception
		if (state != RUNNING && state != PAUSED)	return;
		state = FINISHED;
		slots.get(slots.size()-1)[1] = System.currentTimeMillis();
		
	}
	
	public void pause(){
		//Fix Bug: if the stopwatch is not started, the slots.get(-1) will throw an exception
		if (state != RUNNING)	return;
		state = PAUSED;
		slots.get(slots.size()-1)[1] = System.currentTimeMillis();
	}
	
	public void store(String name){
		
		if (state != RUNNING)	return;
		
		slots.get(slots.size()-1)[1] = System.currentTimeMillis();
		
		if (name != null){
			nameOfSlots.put(name, slots.size()-1);
		}
		
		long[] start = new long[]{System.currentTimeMillis(), 0};
		slots.add(start);
		
		
	}
	
	public void store(){
		store(null);
	}
	
	public void resume(){
		state = RUNNING;
		long[] start = new long[]{System.currentTimeMillis(), 0};
		slots.add(start);
	}
	
	public void reset(){
		this.slots = new ArrayList<long[]>();
		this.state = READY;
	}
	
	public long runTime(){
		long sum = 0;
		for (int i = 0 ; i < slots.size(); i++){
			if (slots.get(i)[1]!=0){
				sum += slots.get(i)[1] - slots.get(i)[0];
			}
		}
		return sum;
	}
	
	/**
	 * Output the running time with TimeUnit.
	 * Notice: we use Math.round to get the approximate value of running time
	 * @param tu
	 * @return
	 */
	public String runTime(TimeUnit tu){
		long sum = 0;
		for (int i = 0 ; i < slots.size(); i++){
			if (slots.get(i)[1]!=0){
				sum += slots.get(i)[1] - slots.get(i)[0];
			}
		}
		if (tu.equals(TimeUnit.MILLISECONDS)){
			return sum+"ms";
		}else if (tu.equals(TimeUnit.MICROSECONDS)){
			return sum*1000+"m(icro)s";
		}else if (tu.equals(TimeUnit.NANOSECONDS)){
			return sum*1000000+"ms";
		}else if (tu.equals(TimeUnit.SECONDS)){
			return Math.round(sum*1.0/1000)+"s";
		}else if (tu.equals(TimeUnit.MINUTES)){
			return Math.round(sum*1.0/1000/60)+"m";
		}else if (tu.equals(TimeUnit.HOURS)){
			return Math.round(sum*1.0/1000/60/60)+"m";
		}else if (tu.equals(TimeUnit.DAYS)){
			return Math.round(sum*1.0/1000/60/24)+"m";
		}else{
			return "NULL";
		}
	}
	
	public long getSlot(int s){
		if (s >= slots.size()){
			throw new ArrayIndexOutOfBoundsException("The value of s is out of the index of slots (" + slots.size() +")");
		}
		return slots.get(s)[1] - slots.get(s)[0];
	}
	
	public long getSlot(String name) throws CLTKNotKeyInMapException{
		if (!nameOfSlots.containsKey(name)){
			throw new CLTKNotKeyInMapException("The watch does not have a record for " + name);
		}
		
		int s = nameOfSlots.get(name);
		return slots.get(s)[1] - slots.get(s)[0];
	}

	@Override
	public String toString(){
		return toString(1);
	}
	
	public String toString(int num){
		StringBuilder sb = new StringBuilder();
		String str = "ms";
		if (num == 1000){
			str = "s";
		}
			
		for (int i = 0 ; i < slots.size() - 1; i++){
			sb.append(String.format("%d - %d: %.2f %s\n", slots.get(i)[0], slots.get(i)[1], (slots.get(i)[1] - slots.get(i)[0])*1.0/num, str));
		}
		
		if (state == RUNNING){
			sb.append(String.format("%d - ?\n", slots.get(slots.size()-1)[0]));
		}
		return sb.toString();
	}
}
