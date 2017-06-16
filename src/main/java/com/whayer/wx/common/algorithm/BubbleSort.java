package com.whayer.wx.common.algorithm;

/**
 * 鸡尾酒双向冒泡
 * 简单的冒泡排序其实就是一个N-1趟扫描,每趟N-1次对比交换，即两个for循环
 * @author duyu
 *
 */
public class BubbleSort {
	
	static int swapCount = 0;
	
	public static void main(String[] args) {
		int[] array = { 10, -3, 5, 34, -34, 5, 0, 9 };
		sort(array);
		
		//打印排序后的结果
		for (int el : array) {
			System.out.print(el + " ");
		}
		
		System.out.println("交换的次数：" + swapCount);
	}

	static void sort(int[] array) {
		int top = array.length - 1;
		int bottom = 0;
		boolean flag = true;
		int i, j;
		while (flag) {
			flag = false;
			// 从小到大，升序
			for (i = bottom; i < top; i++) {
				if (array[i] > array[i + 1]) {
					swap(array, i, i + 1);
					flag = true;
				}
			}
			top--;
			// 从大到小，降序
			for (j = top; j > bottom; j--) {
				if (array[j] < array[j - 1]) {
					swap(array, j, j - 1);
					flag = true;
				}
			}
			bottom++;
		}
	}

	private static void swap(int[] array, int i, int j) {
		
		int tmp = array[i];
		array[i] = array[j];
		array[j] = tmp;
		
		//记录交换的次数
		swapCount ++;
	}
}
