package edu.ntu.cltk.data;

import java.util.LinkedList;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {

	//http://www.json.org/
	public static final String JSON_PATTERN_ARRAY = "\\[(.*)\\]";
    public static final String JSON_PATTERN_OBJECT = "\\{(.*)\\}";
    public static final String JSON_PATTERN_STRING = "\"[a-zA-Z0-9._\"/]*\"";
    public static final String JSON_PATTERN_NUMBER = "-?(0|[1-9][0-9]*)(.[0-9]*)?([e|E][+|-]?[0-9]*)?";
    //public static final 

	
	/**
	 * Extract the JSON content from a paragraph of text
	 * @param text
	 * @return
	 */
	public static String extractJSONfromText(String text){
		Pattern arrayPattern = Pattern.compile(RegexUtil.JSON_PATTERN_ARRAY);
		Pattern objectPattern = Pattern.compile(RegexUtil.JSON_PATTERN_OBJECT);
		Pattern stringPattern = Pattern.compile(RegexUtil.JSON_PATTERN_STRING);
		Pattern numberPattern = Pattern.compile(RegexUtil.JSON_PATTERN_NUMBER);
		Matcher matcher = arrayPattern.matcher(text);
		return null;
	}
	
	public static void main(String[] args){
		String[] cases = {"-1.3", 
						"1.3",
						"-0.32e+12",
						"-90.32E-01",
						"-90.3.2E-01",
						"0132",
						"we+-"};
		for (String c : cases){
			if (Pattern.matches(RegexUtil.JSON_PATTERN_NUMBER, c)){
				System.out.println(c + " : true");
			}else{
				System.out.println(c + " : false");
			}
		}
	}
}
