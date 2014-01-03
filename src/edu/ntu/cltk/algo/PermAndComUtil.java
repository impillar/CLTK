package edu.ntu.cltk.algo;

import java.util.ArrayList;
import java.util.List;

/**
 * This class aggregates the common algorithms in Permutation and Combination
 * @author pillar
 *
 */
public class PermAndComUtil {

	/**
	 * Find the max number of the sequence occurring in the data <br/>
	 * For example, the data contains the following strings <br/>
	 * 		a, a, b, c, a, c, b, d, e, f, e, a, b, c, e, c, b, a <br/>
	 * To find the number of sequence a,b,c occurring in the above lists <br/>
	 * The answer is 2, because <br/>
	 * 		a, <u>a</u>, <u>b</u>, <u>c</u>, <u>a</u>, c, <u>b</u>, d, e, f, e, a, b, <u>c</u>, e, c, b, a <br/> 
	 * @param data
	 * @param seq
	 * @return
	 */
	public static int getMaxSequence(List<?> data, List<?> seq){
		int[] max = new int[data.size()];
		int i,j,k,cnt=0;
		for (i = 0 ; i < data.size(); i++){
			for (j = 0 ; j < seq.size(); j++){
				if (data.get(i).equals(seq.get(j))){
					break;
				}
			}
			if (j >= seq.size())	continue;
			if (j == 0)		max[i] = 1;
			else{
				for (k = i-1; k >=0 && max[k] == 0;k--);
				if (k >= 0 && max[k] == j){
					max[i] = max[k] + 1; 
				}
			}
			if (max[i] == seq.size())	
				cnt++;
		}
		return cnt;
	}
	
	/**
	 * Count the number of target occurring in the data
	 * @param data
	 * @param target
	 * @return
	 */
	public static int itemCount(List<?> data, Object target){
		int cnt = 0;
		for (Object d : data){
			if (d.equals(target))
				cnt++;
		}
		return cnt;
	}
	
	/**
	 * Return the permutation in a specified number of the data
	 * @param data
	 * @param num
	 * @return
	 */
	public static List<?> permutation(List<?> data, int num){
		if (MathUtil.factorialExceedIntegerMax(data.size(), num)){
			return null;
		}
		List perms = new ArrayList();
		
		return perms;
	}
}
