package com.whayer.wx.common.algorithm;

public class QuickSort {

	public static void main(String[] args) {
		int a[] = { 49, 38, 65, 97, 76, 13};
		_quickSort(a, 0, a.length - 1);

		// 打印快排后的数组
		for (int i = 0; i < a.length; i++)
			System.out.println(a[i]);
	}

	public static void _quickSort(int s[], int l, int r) {
		if (l < r)  
	    {  
	        //Swap(s[l], s[(l + r) / 2]); //将中间的这个数和第一个数交换 参见注1  
	        int i = l, j = r, x = s[l];  
	        while (i < j)  
	        {  
	            while(i < j && s[j] >= x) // 从右向左找第一个小于x的数  
	                j--;    
	            if(i < j)   
	                s[i++] = s[j];  
	              
	            while(i < j && s[i] < x) // 从左向右找第一个大于等于x的数  
	                i++;    
	            if(i < j)   
	                s[j--] = s[i];  
	        }  
	        s[i] = x;  
	        _quickSort(s, l, i - 1); // 递归调用   
	        _quickSort(s, i + 1, r);  
	    }  
	}
}
