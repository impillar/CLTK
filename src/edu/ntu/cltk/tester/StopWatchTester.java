package edu.ntu.cltk.tester;

import java.util.Random;

import edu.ntu.cltk.logging.StopWatch;

public class StopWatchTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int cnt = 0; 
		StopWatch.v().start();
		while (cnt < 5){
			int ran = new Random().nextInt(4);
			try{
				System.out.println("Will wait for " + ran + "s");
				Thread.sleep(ran*1000);
			}catch(Exception e){
				
			}

			StopWatch.v().store();
			cnt++;
		}
		StopWatch.v().finish();
		
		System.out.println(StopWatch.v().toString());
	}

}
