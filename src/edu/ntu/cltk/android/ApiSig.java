package edu.ntu.cltk.android;

import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.List;
public class ApiSig implements Comparable<ApiSig> {

	private String className;
	private String methodName;
	private String returnType;
	private List<String> parameters;
	private int calledNumber = 0;
	private volatile int hashCode = 0;

	private ApiSig(String className, String methodName, String returnType,
			List<String> parameters, int number) {
		this.className = className;
		this.methodName = methodName;
		this.returnType = returnType;
		this.parameters = parameters;
		this.calledNumber = number;
	}

	private ApiSig(String className, String methodName) {
		this.className = className;
		this.methodName = methodName;
		
	}
	
	public String getClassName(){
		return this.className;
	}
	
	public String getMethodName(){
		return this.methodName;
	}
	
	public String getReturnType(){
		return this.returnType;
	}
	
	public List<String> getParamters(){
		return this.parameters;
	}

	public static ApiSig makeApi(String className, String methodName,
			String returnType, List<String> parameters) {
		return new ApiSig(className, methodName, returnType, parameters, 0);
	}

	public static ApiSig makeApi(String className, String methodName,
			String returnType, List<String> parameters, int number) {
		return new ApiSig(className, methodName, returnType, parameters, number);
	}
	
	public static ApiSig makeApi(String className, String methodName) {
		return new ApiSig(className, methodName);
	}
	
	public void setCalledNumber(int number){
		this.calledNumber = number;
	}	

	public String getName() {
		return this.className.replace(".", "/")+
				
				"."+this.methodName;
	}

	@Override
	public int hashCode() {
		if (hashCode == 0) {
			int result = 17;
			final int prime = 31;
			result = result * prime + className == null ? 0 : className
					.hashCode();
			result = result * prime + methodName == null ? 0 : methodName
					.hashCode();
			this.hashCode = result;
		}
		return this.hashCode;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (!o.getClass().equals(getClass()))
			return false;
		ApiSig as = (ApiSig) o;
		
		return className.equals(as.className)
				&& methodName.equals(as.methodName)
				&& returnType.equals(as.returnType)
				&& parameters.equals(as.parameters);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%s: %s %s(", className, returnType, methodName));
		if (parameters != null && parameters.size() != 0){
			for (int i = 0 ; i < parameters.size(); i++){
				if (i != 0)	sb.append(", ");
				sb.append(parameters.get(i));
			}
		}
		sb.append(")");
		return sb.toString();
	}

	@Override
	public int compareTo(ApiSig o) {
		return toString().compareTo(o.toString());
	}
	
	/**
	 * The signature should follows:
	 * &lt;ClassName: ReturnType MethodName(Parameters)&gt;
	 * @param apiSig
	 * @return
	 * @throws Exception 
	 */
	public static ApiSig makeApi(String apiSig) throws Exception{
		
		String[] met = apiSig.substring(1, apiSig.length()).replaceAll(":", "").split("\\s");
		
		if (met.length != 3 
				|| met[2].indexOf("(") == -1 
				|| met[2].charAt(met[2].length()-1)!= '>' 
				|| met[2].charAt(met[2].length()-2) != ')' 
				|| met[2].indexOf("(")+1 > met[2].length()-2){
			throw new Exception("The signature is not following the format: <ClassName: ReturnType MethodName(Parameters)>");
		}
		
		String className = met[0];
		String methodName = met[2].substring(0, met[2].indexOf("("));
		
		String parStr = met[2].substring(met[2].indexOf("(")+1, met[2].length()-2);
		String[] parArr = parStr.split(",");
		List<String> parList = new ArrayList<String>();
		for (String par : parArr){
			if (par != null && par.length() != 0){
				parList.add(par);
			}
		}
		
		return makeApi(className, methodName, met[1], parList);
	}

}
