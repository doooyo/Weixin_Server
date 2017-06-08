package com.whayer.wx.common.algorithm;

public class QuickSort {
	
	public static void main(String[] args) {
		int a[] = { 49, 38, 65, 97, 76, 13, 27, 49, 78, 34, 12, 64, 5, 4, 62, 99, 98, 54, 56, 17, 18, 23, 34, 15, 35, 25,
				53, 51 };
		sort(a, 0, a.length - 1);
	}
	
	public static void sort(int arr[], int low, int high) {
		int l = low;  //i  一般指向数组头部
		int h = high; //j  一般指向数组尾部
		int povit = arr[low]; //k(基准值)，一般也是设为数组第一个值

		while (l < h) {
			while (l < h && arr[h] >= povit) { //从后半部分向前扫描
				h--;
				if (l < h) {
					int temp = arr[h];
					arr[h] = arr[l];
					arr[l] = temp;
					l++;
				}
			}
			
			while (l < h && arr[l] <= povit) { //从前半部分向后扫描
				l++;
				if (l < h) {
					int temp = arr[h];
					arr[h] = arr[l];
					arr[l] = temp;
					h--;
				}
			}
		}

		
		System.out.print("l=" + (l + 1) + "h=" + (h + 1) + "povit=" + povit + "\n");
		if (l > low)
			sort(arr, low, l - 1);
		if (h < high)
			sort(arr, l + 1, high);

	}
}
