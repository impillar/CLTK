package edu.ntu.cltk.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ArrayUtil {

	/**
	 * Serialise the one-dimension array with a separator <br />
	 * For example: <br/>
	 * List:	 a, b, c<br/>
	 * Separator: ","<br/>
	 * leftDelimiter: null<br/> 
	 * rightDelimiter: null<br/>
	 * The result will be a,b,c
	 * @param <T>
	 * @param data
	 * @param separator
	 * @param leftDelimiter
	 * @param rightDelimiter
	 * @return
	 */
	public static <T> String serializeArray(T[] data, String separator, String leftDelimiter, String rightDelimiter){
		if (data == null || data.length == 0)	return "";
		StringBuilder sb = new StringBuilder();
		for (int i = 0 ; i < data.length; i++){
			if (i > 0)	sb.append(separator);
			if (leftDelimiter!=null)	sb.append(leftDelimiter);
			sb.append(data[i].toString());
			if (rightDelimiter!=null)	sb.append(rightDelimiter);
		}
		return sb.toString();
	}
	
	public static <T> String serializeCollection(Collection<T> data, String separator, String leftDelimiter, String rightDelimiter){
		if (data == null || data.size() == 0)	return "";
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (T d : data){
			if (!first)	sb.append(separator);
			if (leftDelimiter!=null)	sb.append(leftDelimiter);
			sb.append(d);
			if (rightDelimiter!=null)	sb.append(rightDelimiter);
			first = false;
		}
		return sb.toString();
	}
	
	public static <T> String serializeCollection(Collection<T> data, String separator){
		return serializeCollection(data, separator, null, null);
	}
	
	public static String serializeArray(List<?> data, String separator){
		return serializeArray((data==null?null:data.toArray()), separator, null, null);
	}
	
	public static <T> String serializeArray(T[] data, String separator){
		return serializeArray(data, separator, null, null);
	}
	
	public static <T> String serializeArray(List<T> data, String separator, String leftDelimiter, String rightDelimiter){
		return serializeArray((data==null?null:data.toArray()), separator, leftDelimiter, rightDelimiter);
	}
	/**
	 * Make a new array for one string and a string array <br />
	 * For example:	<br />
	 * String str = "abc"; <br/>
	 * String[] item = {"c","d","e"}; <br/>
	 * Then the result will be an array {"abc","c","d","e"}
	 * @param str
	 * @param item
	 * @return
	 */
	public static String[] makeArray(final String str, final String [] item){
		List<String> res = new ArrayList<String>(item.length + 1){{
			add(str);
			for (int i = 0; i < item.length; i++){
				add(item[i]);
			}
		}};
		
		return res.toArray(new String[res.size()]);
		
	}
	
	public static String[] makeArray(final String[] item1, final String[] item2){
		List<String> res = new ArrayList<String>(item1.length + item2.length){{
			for (int i = 0 ; i < item1.length ; i++){
				add(item1[i]);
			}
			for (int i = 0; i < item2.length; i++){
				add(item2[i]);
			}
		}};
		
		return res.toArray(new String[res.size()]);
	}
	
	public static Byte[] makeArray(final byte str, final byte [] item){
		List<Byte> res = new ArrayList<Byte>(item.length + 1){{
			add(str);
			for (int i = 0; i < item.length; i++){
				add(item[i]);
			}
		}};
		
		return res.toArray(new Byte[res.size()]);
		
	}
	
	public static Byte[] makeArray(final byte[] item1, final byte[] item2){
		List<Byte> res = new ArrayList<Byte>(item1.length + item2.length){{
			for (int i = 0 ; i < item1.length ; i++){
				add(item1[i]);
			}
			for (int i = 0; i < item2.length; i++){
				add(item2[i]);
			}
		}};
		
		return res.toArray(new Byte[res.size()]);
	}
	
	public static Short[] makeArray(final short str, final short [] item){
		List<Short> res = new ArrayList<Short>(item.length + 1){{
			add(str);
			for (int i = 0; i < item.length; i++){
				add(item[i]);
			}
		}};
		
		return res.toArray(new Short[res.size()]);
		
	}
	
	public static Short[] makeArray(final short[] item1, final short[] item2){
		List<Short> res = new ArrayList<Short>(item1.length + item2.length){{
			for (int i = 0 ; i < item1.length ; i++){
				add(item1[i]);
			}
			for (int i = 0; i < item2.length; i++){
				add(item2[i]);
			}
		}};
		
		return res.toArray(new Short[res.size()]);
	}
	
	public static Integer[] makeArray(final int str, final int [] item){
		List<Integer> res = new ArrayList<Integer>(item.length + 1){{
			add(str);
			for (int i = 0; i < item.length; i++){
				add(item[i]);
			}
		}};
		
		return res.toArray(new Integer[res.size()]);
		
	}
	
	public static Integer[] makeArray(final int[] item1, final int[] item2){
		List<Integer> res = new ArrayList<Integer>(item1.length + item2.length){{
			for (int i = 0 ; i < item1.length ; i++){
				add(item1[i]);
			}
			for (int i = 0; i < item2.length; i++){
				add(item2[i]);
			}
		}};
		
		return res.toArray(new Integer[res.size()]);
	}
	
	public static Long[] makeArray(final long str, final long [] item){
		List<Long> res = new ArrayList<Long>(item.length + 1){{
			add(str);
			for (int i = 0; i < item.length; i++){
				add(item[i]);
			}
		}};
		
		return res.toArray(new Long[res.size()]);
		
	}
	
	public static Long[] makeArray(final long[] item1, final long[] item2){
		List<Long> res = new ArrayList<Long>(item1.length + item2.length){{
			for (int i = 0 ; i < item1.length ; i++){
				add(item1[i]);
			}
			for (int i = 0; i < item2.length; i++){
				add(item2[i]);
			}
		}};
		
		return res.toArray(new Long[res.size()]);
	}
	
	public static Float[] makeArray(final float str, final float [] item){
		List<Float> res = new ArrayList<Float>(item.length + 1){{
			add(str);
			for (int i = 0; i < item.length; i++){
				add(item[i]);
			}
		}};
		
		return res.toArray(new Float[res.size()]);
		
	}
	
	public static Float[] makeArray(final float[] item1, final float[] item2){
		List<Float> res = new ArrayList<Float>(item1.length + item2.length){{
			for (int i = 0 ; i < item1.length ; i++){
				add(item1[i]);
			}
			for (int i = 0; i < item2.length; i++){
				add(item2[i]);
			}
		}};
		
		return res.toArray(new Float[res.size()]);
	}
	
	public static Double[] makeArray(final double str, final double [] item){
		List<Double> res = new ArrayList<Double>(item.length + 1){{
			add(str);
			for (int i = 0; i < item.length; i++){
				add(item[i]);
			}
		}};
		
		return res.toArray(new Double[res.size()]);
		
	}
	
	public static Double[] makeArray(final double[] item1, final double[] item2){
		List<Double> res = new ArrayList<Double>(item1.length + item2.length){{
			for (int i = 0 ; i < item1.length ; i++){
				add(item1[i]);
			}
			for (int i = 0; i < item2.length; i++){
				add(item2[i]);
			}
		}};
		
		return res.toArray(new Double[res.size()]);
	}
	
	public static Character[] makeArray(final char str, final char [] item){
		List<Character> res = new ArrayList<Character>(item.length + 1){{
			add(str);
			for (int i = 0; i < item.length; i++){
				add(item[i]);
			}
		}};
		
		return res.toArray(new Character[res.size()]);
		
	}
	
	public static Character[] makeArray(final char[] item1, final char[] item2){
		List<Character> res = new ArrayList<Character>(item1.length + item2.length){{
			for (int i = 0 ; i < item1.length ; i++){
				add(item1[i]);
			}
			for (int i = 0; i < item2.length; i++){
				add(item2[i]);
			}
		}};
		
		return res.toArray(new Character[res.size()]);
	}
	
	public static Boolean[] makeArray(final boolean str, final boolean [] item){
		List<Boolean> res = new ArrayList<Boolean>(item.length + 1){{
			add(str);
			for (int i = 0; i < item.length; i++){
				add(item[i]);
			}
		}};
		
		return res.toArray(new Boolean[res.size()]);
		
	}
	
	public static Boolean[] makeArray(final boolean[] item1, final boolean[] item2){
		List<Boolean> res = new ArrayList<Boolean>(item1.length + item2.length){{
			for (int i = 0 ; i < item1.length ; i++){
				add(item1[i]);
			}
			for (int i = 0; i < item2.length; i++){
				add(item2[i]);
			}
		}};
		
		return res.toArray(new Boolean[res.size()]);
	}
	
	/**
	 * Determine if dst is included in src
	 * @param <T>
	 * @param src
	 * @param dst
	 * @return
	 */
	public static <T> boolean contains(final T[] src, final T[] dst){
		if (src == null || dst == null)	return false;
		for (int i = 0 ; i < dst.length; i++){
			int j = 0;
			for (; j < src.length; j++){
				if (src[j].equals(dst[i])){
					break;
				}
			}
			if (j >= src.length)	return false;
		}
		return true;
	}
	
	/**
	 * Determine if dst is included in src
	 * @param <T>
	 * @param src
	 * @param dst
	 * @return
	 */
	public static <T> boolean contains(final T[] src, final T dst){
		if (dst == null || src == null)	return false;
		for (int i = 0 ; i < src.length; i++){
			if (src[i].equals(dst)){
				return true;
			}
		}
		return false;
	}
	
	public static boolean contains(final List<?> src, final List<?> dst){
		return contains(src.toArray(), dst.toArray());
	}
	
	public static <T> boolean contains(final List<T> src, final T dst){
		return contains(src.toArray(), dst);
	}
	
	/**
	 * Convert the array from the object to the primitive type
	 * @param nums
	 * @return
	 */
	public static double[] toPrimitive(Double[] nums){
		double[] converted = new double[nums.length];
		for (int i = 0 ; i < nums.length; i++){
			// Java 1.4
			converted[i] = nums[i].doubleValue();
			// Java 1.5
			// converted[i] = nums[i];
		}
		return converted;
	}
	
	public static int[] toPrimitive(Integer[] nums){
		int[] converted = new int[nums.length];
		for (int i = 0 ; i < nums.length; i++){
			// Java 1.4
			converted[i] = nums[i].intValue();
			// Java 1.5
			// converted[i] = nums[i];
		}
		return converted;
	}
	
	public static long[] toPrimitive(Long[] nums){
		long[] converted = new long[nums.length];
		for (int i = 0 ; i < nums.length; i++){
			// Java 1.4
			converted[i] = nums[i].longValue();
			// Java 1.5
			// converted[i] = nums[i];
		}
		return converted;
	}
	
	public static float[] toPrimitive(Float[] nums){
		float[] converted = new float[nums.length];
		for (int i = 0 ; i < nums.length; i++){
			// Java 1.4
			converted[i] = nums[i].floatValue();
			// Java 1.5
			// converted[i] = nums[i];
		}
		return converted;
	}
	
	public static short[] toPrimitive(Short[] nums){
		short[] converted = new short[nums.length];
		for (int i = 0 ; i < nums.length; i++){
			// Java 1.4
			converted[i] = nums[i].shortValue();
			// Java 1.5
			// converted[i] = nums[i];
		}
		return converted;
	}
	
	/**
	 * Determine if two arrays contain the same elements
	 * @param arr1
	 * @param arr2
	 * @return
	 */
	public static <T> boolean overlap(T[] arr1, T[] arr2){
		for (T a1 : arr1){
			for (T a2 : arr2){
				if (a1.equals(a2))
					return true;
			}
		}
		return false;
	}
	
	/**
	 * Determine if two lists contain the same elements
	 * @param col1
	 * @param col2
	 * @return
	 */
	public static <T> boolean overlap(Collection<T> col1, Collection<T> col2){
		for (T c1 : col1){
			for (T c2 : col2){
				if (c1.equals(c2)){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Compare two collections with same type. 
	 * data1 > data2, 	return 1;
	 * data1 < data2, 	return -1;
	 * data1 == data2, 	return 0;
	 * else				return 2;
	 * The result will not be influenced by duplicated elements.
	 * @param data1
	 * @param data2
	 * @return
	 */
	public static int COLLECTION_LARGER = 1;
	public static int COLLECTION_SMALLER = -1;
	public static int COLLECTION_EQUAL = 0;
	public static int COLLECTION_NOREL = 2;
	public static <T> int compareCollection(Collection<T> data1, Collection<T> data2){
		boolean largerOrEqual = true;
		for (T d2 : data2){
			boolean found = false;
			for (T d1 : data1){
				if (d1.equals(d2)){
					found = true;
					break;
				}
			}
			if (found == false)	largerOrEqual = false;
		}
		boolean smallerOrEqual = true;
		for (T d1 : data1){
			boolean found = false;
			for (T d2 : data2){
				if (d1.equals(d2)){
					found = true;
					break;
				}
			}
			if (found == false)	smallerOrEqual = false;
		}
		if (smallerOrEqual && largerOrEqual){
			return COLLECTION_EQUAL;
		}else if (largerOrEqual){
			return COLLECTION_LARGER;
		}else if (smallerOrEqual){
			return COLLECTION_SMALLER;
		}else
			return COLLECTION_NOREL;
	}
}
