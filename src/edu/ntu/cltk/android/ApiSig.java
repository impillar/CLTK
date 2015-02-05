package edu.ntu.cltk.android;

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

}
