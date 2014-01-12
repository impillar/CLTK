package edu.ntu.cltk.data;

import java.util.ArrayList;
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
	
	public static boolean contains(final List<?> src, final List<?> dst){
		return contains(src.toArray(), dst.toArray());
	}
}
