package edu.ntu.cltk.runtime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RuntimeUtil {

	/**
	 * Execute one file
	 * @param executable
	 * @param args
	 */
	public static void executeProcess(String ... cmdArray){
		executeProcess(false, cmdArray);
	}
	
	/**
	 * Execute a process with message returned
	 * @param args
	 * @return
	 */
	public static String executeProcessWithResponse(String...args){
		BufferedReader reader = null;
		Process pro = null;
		StringBuilder response = new StringBuilder();
		try {
			pro = Runtime.getRuntime().exec(args);
			pro.waitFor();
			reader = new BufferedReader(new InputStreamReader(pro.getInputStream()));
			String line = "";
			while ((line = reader.readLine())!= null) {
				response.append(line+"\n");
			}
			return response.toString();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if (reader != null){
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (pro != null){
				try {
					pro.getInputStream().close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				pro.destroy();
			}
		}
		return null;
	}
	
	/**
	 * Execute a process with printing out the message determined by output
	 * @param output
	 * @param args
	 */
	public static void executeProcess(boolean output, String... args){
		BufferedReader reader = null;
		Process pro = null;
		try {
			pro = Runtime.getRuntime().exec(args);
			pro.waitFor();
			if (output){
				reader = 
				         new BufferedReader(new InputStreamReader(pro.getInputStream()));
				 
				String line = "";			
				while ((line = reader.readLine())!= null) {
					System.out.println(line);
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if (reader != null){
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (pro != null){
				try {
					pro.getInputStream().close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				pro.destroy();
			}
		}
	}

}
