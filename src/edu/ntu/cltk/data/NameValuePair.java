package edu.ntu.cltk.data;

public class NameValuePair<K, V> {

	private K key;
	private V value;
	
	public NameValuePair(K key, V value){
		this.key = key;
		this.value = value;
	}
	
	public K getKey(){
		return key;
	}
	
	public void setKey(K key){
		this.key = key;
	}
	
	public V getValue(){
		return value;
	}
	
	public void setValue(V value){
		this.value = value;
	}
	
	public boolean keyEqualsTo(byte val){
		return Byte.parseByte(key.toString()) == val;
	}
	
	public boolean keyEqualsTo(int val){
		return Integer.parseInt(key.toString()) == val;
	}
	
	public boolean keyEqualsTo(short val){
		return Short.parseShort(key.toString()) == val;
	}
	
	public boolean keyEqualsTo(long val){
		return Long.parseLong(key.toString()) == val;
	}
	
	public boolean keyEqualsTo(float val){
		return Float.parseFloat(key.toString()) == val;
	}
	
	public boolean keyEqualsTo(double val){
		return Double.parseDouble(key.toString()) == val;
	}
	
	public boolean keyEqualsTo(boolean val){
		return Boolean.parseBoolean(key.toString()) == val;
	}
	
	public boolean keyEqualsTo(char val){
		return Integer.parseInt(key.toString()) == val;
	}
	
	public boolean valueEqualsTo(int val){
		return Integer.parseInt(value.toString()) == val;
	}
	
	public boolean valueEqualsTo(short val){
		return Short.parseShort(value.toString()) == val;
	}
	
	public boolean valueEqualsTo(long val){
		return Long.parseLong(value.toString()) == val;
	}
	
	public boolean valueEqualsTo(float val){
		return Float.parseFloat(value.toString()) == val;
	}
	
	public boolean valueEqualsTo(double val){
		return Double.parseDouble(value.toString()) == val;
	}
	
	public boolean valueEqualsTo(boolean val){
		return Boolean.parseBoolean(value.toString()) == val;
	}
	
	public boolean valueEqualsTo(char val){
		return Integer.parseInt(value.toString()) == val;
	}
}