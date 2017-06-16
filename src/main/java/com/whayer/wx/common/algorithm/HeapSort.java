package com.whayer.wx.common.algorithm;

import java.util.Arrays;

public class HeapSort {
	public static void main(String[] args) {
		int[] arr = { 1, 3, 2, 45, 65, 33, 12 };

		int[] result = heapSort(arr);

		for (int num : result) {
			System.out.println(num);
		}
	}
	
   public static int[] heapSort(int[] before){
       int[] a= Arrays.copyOf(before, before.length);
       int n = a.length;
       int temp;
       for(int i = n / 2 - 1; i >= 0; i--){ //创建初始堆
           sift(i, n, a);
       }
       for(int i = n - 1; i > 0; i--){ //每趟将最小关键字值交换到后面，再调整成堆
           temp = a[0];
           a[0] = a[i];
           a[i] = temp;
           sift(0, i, a);
       }
       return a;
   }

   /**
    * @description 筛选法调整堆算法 ，以low为根结点的子树调整成小顶堆
    * @param low
    * @param high
    */
   private static void sift(int low, int high, int[] a){
       int i = low; //子树的根结点
       int j = 2 * i + 1;
       int temp = a[i];
       while(j < high){
           //判断条件j < high - 1 表示有右结点，即j+1 < high
           if(j < high - 1 && a[j] > a[j + 1])
               j++;

           if(temp > a[j]){
               a[i] = a[j]; //孩子结点中的较小值上移
               i = j;
               j = 2 * i + 1;
           }
           else
               j = high + 1;
       }
       a[i] = temp;
   }
}
