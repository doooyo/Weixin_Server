package com.whayer.wx.common.algorithm;

public class TreeSelectionsortV2 {
	
	public static void main(String[] args) {
		int[] arr = { 1, 3, 2, 45, 65, 33, 12 };

		treeSelectionSort(arr);

		for (int num : arr) {
			System.out.print(num + " ");
		}
	}
	
	public static void treeSelectionSort(int[] data) {
		if (data == null || data.length <= 1)
			return;
		int len = data.length, low = 0, i, j;
		// add Auxiliary Space
		int[] tmp = new int[2 * len - 1];
		int tSize = tmp.length;
		// construct a tree
		for (i = len - 1, j = tmp.length - 1; i >= 0; i--, j--) {
			tmp[j] = data[i];
		}
		for (i = tSize - 1; i > 0; i -= 2) {
			tmp[(i - 1) / 2] = tmp[i] > tmp[i - 1] ? tmp[i - 1] : tmp[i];
		}
		// end
		// remove the minimum node.
		while (low < len) {
			data[low++] = tmp[0];
			for (j = tSize - 1; tmp[j] != tmp[0]; j--) {
				tmp[j] = Integer.MAX_VALUE;
				while (j > 0) {
					if (j % 2 == 0) { // 如果是右节点
						tmp[(j - 1) / 2] = tmp[j] > tmp[j - 1] ? tmp[j - 1] : tmp[j];
						j = (j - 1) / 2;
					} else { // 如果是左节点
						tmp[j / 2] = tmp[j] > tmp[j + 1] ? tmp[j + 1] : tmp[j];
						j = j / 2;
					}
				}
			}

		}
	}

}
