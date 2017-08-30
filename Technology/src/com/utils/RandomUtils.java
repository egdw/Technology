package com.utils;

import java.util.Random;

public class RandomUtils {
	public static int[] nums = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
	public static Random random = new Random();
	
	/**
	 * 
	 * @param times
	 *            得到几位的随机数
	 * @param times2 得到几位的英文随机数
	 * @return
	 */
	public static String getRandomNum(int times) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < times; i++) {
			sb.append(nums[random.nextInt(10)]);
		}
		return sb.toString();
	}
}
