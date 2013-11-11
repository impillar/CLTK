package edu.ntu.cltk.date;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.ntu.cltk.data.StringUtil;

public class DateFormater {

	public static DateFormat df;
	public static String defaultDateFormat = "yyyy/MM/dd HH:mm:ss";
	
	/**
	 * Timestamp to String
	 * @param millisecond
	 * @return
	 */
	public static String formatString(long millisecond){
		return formatString(millisecond, defaultDateFormat);
	}
	
	public static String formatString(long millisecond, String form){
		Timestamp ts = new Timestamp(millisecond);
		df = new SimpleDateFormat(form);
		try{
			return df.format(ts);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return StringUtil.EMPTY;
	}
	
	/**
	 * Timestamp to Date
	 * @param millisecond
	 * @return
	 */
	public static Date formatDate(long millisecond){
		Timestamp ts = new Timestamp(millisecond);
		try{
			Date dt = new Date();
			dt = ts;
			return dt;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Date to String
	 * @param date
	 * @return
	 */
	public static String formatString(Date date){
		return formatString(date,defaultDateFormat);
	}
	
	public static String formatString(Date date, String form){
		df = new SimpleDateFormat(form);
		try{
			return df.format(date);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return StringUtil.EMPTY;
	}
	
	/**
	 * String to Date
	 * @param str
	 * @return
	 */
	public static Date formatDate(String str){
		return formatDate(str, defaultDateFormat);
	}
	
	public static Date formatDate(String str, String form){
		df = new SimpleDateFormat(form);
		try{
			return df.parse(str);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	/**
	 * String to timestamp
	 * @param str
	 * @return
	 */
	public static Timestamp formatTimestamp(String str){
		try{
			return Timestamp.valueOf(str);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	/**
	 * String to long
	 * @param str
	 * @return
	 */
	public static long formatLong(String str){
		try{
			Timestamp ts = formatTimestamp(str);
			if (ts != null)		return ts.getTime();
			return -1;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return -1;
	}
}