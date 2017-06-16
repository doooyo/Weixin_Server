package com.whayer.wx.common.algorithm;

/**
 * 将 {@code QuickSort} 分解步骤
 * @author duyu
 *
 */
public class QuickSortV2 {

	public static void main(String[] args) {
//		int a[] = { 49, 38, 65, 97, 76, 13, 27, 49, 78, 34, 12, 64, 5, 4, 62, 99, 98, 54, 56, 17, 18, 23, 34, 15, 35,
//				25, 53, 51 };
		int a2[] =  { 49, 38, 65, 97, 76, 13, 27};
		_quickSort(a2, 0, a2.length - 1);

		// 打印快排后的数组
		for (int i = 0; i < a2.length; i++)
			System.out.println(a2[i]);
	}

	/**
	 * 获取中轴位置
	 * 
	 * @param list
	 * @param low
	 * @param high
	 * @return
	 */
	public static int getMiddle(int[] list, int low, int high) {
		
		// 数组的第一个作为中轴
		int tmp = list[low]; 
		// 先从右往左扫描,然后从左往右扫描,如此称为一个循环(交换值)
		while (low < high) {
			//先从右端往左扫描,比中轴(一般是原数组首位)大则继续左移;比中轴小则赋值为中轴值
			while (low < high && list[high] >= tmp) {
				high--;
			}
			/**
			 *  比中轴小的记录移到低端(这里采用赋值方式,而非交换)
			 *  问题: 当文件为正序时,还是会进行一次无用的赋值
			 *       使用if判断
			 *  第一趟结果:{ 27, 38, 65, 97, 76, 13, 27} ; low=0, high=6
			 *  第二趟结果:{ 27, 38, 13, 97, 76, 13, 65} ; low=2, high=5
			 *  第三趟结果:{ 27, 38, 13, 97, 76, 97, 65} ; low=3, high=3
			 */
			if(high != 0 && list[low] != list[high])
				list[low] = list[high];
			
			
			//然后从左端往右扫描,比中轴(一般是原数组首位)小则继续左移;比中轴大则赋值为中轴值
			while (low < high && list[low] <= tmp) {
				low++;
			}
			/**
			 *  比中轴大的记录移到高端
			 *  问题:同上问题
			 *  第一趟结果:{ 27, 38, 65, 97, 76, 13, 65} ; low=2, high=6
			 *  第二趟结果:{ 27, 38, 13, 97, 76, 97, 65} ; low=3, high=5
			 */
			if(low != (list.length - 1) && list[low] != list[high])
				list[high] = list[low]; 
		}
		// 中轴记录到尾
		// 第一趟排序结果: {27, 38, 13, 49, 76, 97, 65}, 中轴位置=3
		list[low] = tmp; 
		// 返回中轴的位置
		return low; 
	}

	public static void _quickSort(int[] list, int low, int high) {
		if (low < high) {
			int middle = getMiddle(list, low, high); // 将list数组进行一分为二
			//{27, 38, 13, 49, 76, 97, 65} => {27, 38, 13}, {49}, {76, 97, 65}
			_quickSort(list, low, middle - 1); // 对低字表进行递归排序
			_quickSort(list, middle + 1, high); // 对高字表进行递归排序
		}
	}

}
