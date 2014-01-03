package edu.ntu.cltk.algo;

import java.math.BigInteger;

public class MathUtil {

	/**
	 * Calculate the factorial
	 * @param num
	 * @return
	 */
	public static BigInteger factorial(int num){
		return factorial(num, num);
	}
	
	public static BigInteger factorial(int num, int cnt){
		BigInteger res = new BigInteger("1");
		for (int i = 0; i < cnt; i++){
			res.multiply(new BigInteger(num-i+""));
		}
		return res;
	}
	/**
	 * Determine if the factorial exceeds the maximal value of Integer
	 * @param num
	 * @param cnt
	 * @return
	 */
	public static boolean factorialExceedIntegerMax(int num, int cnt){
		BigInteger com = factorial(num, cnt);
		if (com.compareTo(new BigInteger(Integer.MAX_VALUE + "")) == 1){
			return true;
		}
		return false;
	}
}
