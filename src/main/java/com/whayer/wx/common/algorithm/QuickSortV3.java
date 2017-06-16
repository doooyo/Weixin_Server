package com.whayer.wx.common.algorithm;

@Deprecated
public class QuickSortV3 {
	public static void main(String[] args) {
		//注意java的范型不能用于简单类型
		Integer a[] =  { 49, 38, 65, 97, 76, 13, 27};
		_quickSortV3(a, 0, a.length - 1);
		// 打印快排后的数组
		for (int i = 0; i < a.length; i++)
			System.out.println(a[i]);
	}

	private static <T extends Comparable<? super T>> void _quickSortV3(T[] targetArr, int start, int end) {
		int i = start, j = end;
		T key = targetArr[start];

		while (i < j) {
			/* 按j--方向遍历目标数组，直到比key小的值为止 */
			while (j > i && targetArr[j].compareTo(key) >= 0) {
				j--;
			}
			if (i < j) {
				/* targetArr[i]已经保存在key中，可将后面的数填入 */
				targetArr[i] = targetArr[j];
				i++;
			}
			/* 按i++方向遍历目标数组，直到比key大的值为止 */
			while (i < j && targetArr[i].compareTo(key) <= 0)
			/*
			 * 此处一定要小于等于零，假设数组之内有一亿个1，0交替出现的话，而key的值又恰巧是1的话，
			 * 那么这个小于等于的作用就会使下面的if语句少执行一亿次。
			 */
			{
				i++;
			}
			if (i < j) {
				/* targetArr[j]已保存在targetArr[i]中，可将前面的值填入 */
				targetArr[j] = targetArr[i];
				j--;
			}
		}
		/* 此时i==j */
		targetArr[i] = key;

		/* 递归调用，把key前面的完成排序 */
		_quickSortV3(targetArr, start, i - 1);

		/* 递归调用，把key后面的完成排序 */
		_quickSortV3(targetArr, j + 1, end);

	}
}
