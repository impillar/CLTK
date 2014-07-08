package edu.ntu.cltk.runtime;

import java.lang.Thread.State;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.security.auth.callback.Callback;

/**
 * The class can help control the execution of one task. <br/>
 * @author pillar
 *
 */
public class ControlledExecution {
	
	private Runnable runnable;
	private long timeout;
	private TimeUnit tu;
	private Thread runThr;
	private ScheduledExecutorService ses;
	private long startFrom;		//The time when the program starts
	private long endAt;			//The time when the program stops
	private long CHECK_FREQUENCY = 100;
	private ScheduledFuture<?> sf = null;
	private volatile Object await = new Object();
	private boolean killed = false;
	private boolean showProgress = false;
	
	private String taskName = null;
	
	private Runnable monitor = new Runnable(){

		@Override
		public void run() {
			long duration = System.nanoTime() - startFrom;
			logger(String.format("\rRunning %30s (Timeout %8ss) - [%02d:%02d:%02d.%03d]",
					taskName==null?"the current task":taskName,
					tu.toSeconds(timeout),
					TimeUnit.HOURS.convert(duration, TimeUnit.NANOSECONDS), 
					TimeUnit.MINUTES.convert(duration, TimeUnit.NANOSECONDS)%60, 
					TimeUnit.SECONDS.convert(duration, TimeUnit.NANOSECONDS)%60, 
					TimeUnit.MILLISECONDS.convert(duration, TimeUnit.NANOSECONDS)%1000));
			
			if (duration >= tu.toNanos(timeout) || runThr.getState() == State.TERMINATED){
				
				if (runThr != null){
					//runThr.interrupt();
					killed = !(runThr.getState() == State.TERMINATED);
					runThr.stop();
					runThr = null;
				}
				if (sf != null)
					sf.cancel(true);

				//If do not shut down the service, the program will enter into a blocked state
				if (ses != null)
					ses.shutdown();
				
				synchronized(await){
					await.notify();
				}
				//logger(String.format("\nThe task costs %d s\n", tu.toSeconds(duration)));
			}
		}
		
	};
	
	/**
	 * If output the progress
	 * @param show
	 */
	public void setShowProgress(boolean show){
		this.showProgress = show;
	}
	
	public void setMonitorFrequency(long millisec){
		CHECK_FREQUENCY = millisec;
	}
	
	public long getMonitorFrequency(){
		return this.CHECK_FREQUENCY;
	}
	
	private void logger(String message){
		if (showProgress)
			System.out.print(message);
	}
	/**
	 * The runnable object, and set the timeout for the task
	 * @param run
	 * @param timeout
	 * @param tu
	 */
	public ControlledExecution(Runnable run, long timeout, TimeUnit tu){
		this(run, timeout, tu, null);
	}
	
	public ControlledExecution(Runnable run, long timeout, TimeUnit tu, String taskName){
		this.runnable = run;
		this.timeout = timeout;
		this.tu = tu;
		ses = Executors.newScheduledThreadPool(2);
		this.taskName = taskName;
	}
	/**
	 * Start the time-consuming work
	 * @return	if the thread is killed, return 1; otherwise return 0;
	 */
	@SuppressWarnings("finally")
	public int start(){
		runThr = new Thread(this.runnable);
		runThr.start();
		startFrom = System.nanoTime();
		sf = ses.scheduleAtFixedRate(monitor, CHECK_FREQUENCY, CHECK_FREQUENCY, TimeUnit.MILLISECONDS);
		//ses.schedule(monitor, CHECK_FREQUENCY, TimeUnit.MILLISECONDS);
		try {
			synchronized(await){
				await.wait();
			}
			endAt = System.nanoTime();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally{
			logger("\n");
			return killed?1:0;
		}
	}
	/**
	 * The execution time of the program in form of Nano time.
	 * @return
	 */
	public long elapsedTime(){
		return endAt - startFrom;
	}

}
