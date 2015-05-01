package edu.ntu.cltk.data;

import java.util.List;

public class StringUtil {

	
	public static String EMPTY = "";
	
	/**
	 * Check if the string is null of emptry string (the length is zero)
	 * @param s
	 * @return
	 */
	public static boolean isEmpty(String s){
		return s == null || s.length() == 0;
	}
	
	/**
	 * Concatenate strings with a seperator between each two strings.
	 * 
	 * For Example:
	 * seperator = ","
	 * items = {"hello", "world", "Good"}
	 * 
	 * The method will return "hello,world,Good"
	 * @param seperator
	 * @param items
	 * @return
	 */
	public static String jointString(String seperator, List<String> items){
		return jointString(seperator, items.toArray(new String[items.size()]));
	}
	
	public static String jointString(String seperator, String... items){
		StringBuilder sb = new StringBuilder("");
		for (int i = 0 ; i < items.length ; i ++){
			if (!isEmpty(items[i])){
				sb.append(items[i]+seperator);
			}
		}
		if (sb.length() > 0)
			return sb.subSequence(0, sb.length() - 1).toString();
		return sb.toString();
	}
	
	/**
	 * Return the
	 * @param str
	 * @param len
	 * @param suffix
	 * @return
	 */
	public static String truncate(String str, int len, String suffix){
		if (str == null || suffix == null || len < -1)	return null;
		int sLen = suffix.length();
		if (str.length() <= len){
			return str;
		}
		if (sLen >= len)	return suffix;
		return str.substring(0, len-sLen) + suffix;
	}
}