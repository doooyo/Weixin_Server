package com.whayer.wx.common.algorithm;

public class HeapSortV2 {
	// 堆排序的堆的调整过程
	// 已知 H.r[s..m]中记录的关键字除 H.r[s] 之外均满足堆的特征，本函数自上而下调整 H.r[s] 的关键字，使 H.r[s..m]
	// 成为一个大顶堆
	void heapAdjust(int List[], int s, int length) {
		// s 为 当前子树 的 临时 堆顶，先把堆顶暂存到 temp
		int maxTemp = s;
		// s 结点 的 左孩子 2 * s ， 2 * s + 1是 s结点 的右孩子，这是自上而下的筛选过程，length是序列的长度
		int sLchild = 2 * s;
		int sRchild = 2 * s + 1;
		// 完全二叉树的叶子结点不需要调整，没有孩子
		if (s <= length / 2) {
			// 如果 当前 结点的左孩子比当前结点记录值大，调整，大顶堆
			if (sLchild <= length && List[sLchild] > List[maxTemp]) {
				// 更新 temp
				maxTemp = sLchild;
			}
			// 如果 当前 结点的右孩子比当前结点记录值大，调整，大顶堆
			if (sRchild <= length && List[sRchild] > List[maxTemp]) {
				maxTemp = sRchild;
			}
			// 如果调整了就交换，否则不需要交换
			if (List[maxTemp] != List[s]) {
				List[maxTemp] = List[maxTemp] + List[s];
				List[s] = List[maxTemp] - List[s];
				List[maxTemp] = List[maxTemp] - List[s];
				// 交换完毕，防止调整之后的新的以 maxtemp 为父节点的子树不是大顶堆，再调整一次
				heapAdjust(List, maxTemp, length);
			}
		}
	}

	// 建堆,就是把待排序序列一一对应的建立成完全二叉树（从上到下，从左到右的顺序填满完全二叉树），然后建立大（小）顶堆
	void bulidHeap(int List[], int length) {
		// 明确，具有 n 个结点的完全二叉树（从左到右，从上到下），编号后，有如下关系，设 a 结点编号为 i，若 i 不是第一个结点，那么 a
		// 结点的双亲结点的编号为[i/2]
		// 非叶节点的最大序号值为 length / 2
		for (int i = length / 2; i >= 0; i--) {
			// 从头开始调整为大顶堆
			heapAdjust(List, i, length);
		}
	}

	// 堆排序过程
	void heapSort(int List[], int length) {
		// 建大顶堆
		bulidHeap(List, length);
		// 调整过程
		for (int i = length; i >= 1; i--) {
			// 将堆顶记录和当前未经排序子序列中最后一个记录相互交换
			// 即每次将剩余元素中的最大者list[0] 放到最后面 list[i]
			List[i] = List[i] + List[0];
			List[0] = List[i] - List[0];
			List[i] = List[i] - List[0];
			// 重新筛选余下的节点成为新的大顶堆
			heapAdjust(List, 0, i - 1);
		}
	}
}
