package edu.ntu.cltk.android;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import edu.ntu.cltk.file.FileUtil;

public class AndroidApiRecognizer {

	private static AndroidApiRecognizer aar;
	
	public static String API_FILE = "data/android-4.1.1.pscout";
	
	public static final String PATTERN_FOR_PERMISSION = "Permission:.*";
	public static final String PATTERN_FOR_CALLERS = "[0-9]+\\sCallers:";
	public static final String PATTERN_FOR_API = "<.*>\\s\\([0-9]*\\)";
	
	private HashMap<String, List<ApiSig>> permMap = new HashMap<String, List<ApiSig>>();
	private HashMap<ApiSig, List<String>> apiMap = new HashMap<ApiSig, List<String>>();
	
	private AndroidApiRecognizer(){
		parse();
	}
	
	public void parse(){
		if (!FileUtil.isFile(API_FILE)){
			System.out.println(String.format("%s is not a valid file", API_FILE));
			return;
		}
		
		List<String> lines = FileUtil.readFileLineByLine(API_FILE);
		
		for (int i = 0 ; i < lines.size(); ){
			String line = lines.get(i);
			if (Pattern.matches(PATTERN_FOR_PERMISSION, line)){
				String perm = line.replace("Permission:", "").trim();
				List<ApiSig> callers = new ArrayList<ApiSig>();
				i++;
				if (i < lines.size()){
					line = lines.get(i);
					if (Pattern.matches(PATTERN_FOR_CALLERS, line)){
						if (i < lines.size() - 1){
							i++;
							line = lines.get(i);
						}
					}
					while (i < lines.size() && Pattern.matches(PATTERN_FOR_API, line)){
						int rightSep = line.lastIndexOf('>');
						ApiSig as = null;
						if (rightSep != -1){
							as = construct(line.substring(1, rightSep));
						}
						int number = 0;
						try{
							number = Integer.parseInt(line.substring(rightSep+1).replace('(', ' ').replace(')', ' ').trim());
						}catch(NumberFormatException ex){
							number = 0;
						}
						as.setCalledNumber(number);
						callers.add(as);
						if (apiMap.containsKey(as)){
							apiMap.get(as).add(perm);
						}else{
							ArrayList<String> perms = new ArrayList<String>();
							perms.add(perm);
							apiMap.put(as, perms);
						}
						i++;
						if (i < lines.size())	line = lines.get(i);
					}
				}
				
				permMap.put(perm, callers);
				
			}else{
				System.err.println("The first line is not in the format of Permission " + line);
				break;
			}
		}
	}
	
	public boolean find(ApiSig as){
		return apiMap.containsKey(as);
	}
	
	public List<String> getPermission(ApiSig as){
		if (find(as)){
			return apiMap.get(as);
		}
		return null;
	}
	
	public HashMap<ApiSig, List<String>> getApiMap() {
		return apiMap;
	}
	
	/**
	 * Construct an apisig from a string
	 * @param line	it is in the format of com.android.server.am.ActivityManagerService: void killAllBackgroundProcesses()
	 * @return
	 */
	public static ApiSig construct(String line){
		int colon = line.indexOf(':');
		if (colon == -1)	return null;
		String className = line.substring(0, colon).trim();
		line = line.substring(colon+1).trim();
		String returnType = null;
		String methodName = null;
		List<String> pars = new ArrayList<String>();
		int i = 0;
		while (i < line.length() && line.charAt(i) != ' '){
			i++;
		}
		if (i < line.length()){
			returnType = line.substring(0, i);
			String tmp = line.substring(i+1).trim();

			methodName = tmp.substring(0, tmp.indexOf('('));
			String parStr = tmp.substring(tmp.indexOf('(')+1, tmp.indexOf(')')).trim();
			if (!parStr.isEmpty())
				pars = Arrays.asList(parStr.split(","));
			
			ApiSig as = ApiSig.makeApi(className, methodName, returnType, pars);
			return as;
		}
		return null;
	}
	
	public static synchronized AndroidApiRecognizer v(){
		if (aar == null){
			aar = new AndroidApiRecognizer();
		}
		
		return aar;
	}
	
	public boolean isAndroidApi(ApiSig as){
		return find(as);
	}
}
