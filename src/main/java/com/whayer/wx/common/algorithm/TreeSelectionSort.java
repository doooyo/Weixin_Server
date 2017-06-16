package com.whayer.wx.common.algorithm;

import java.util.Arrays;

/**
 * 树型选择排序
 * @author apple
 *
 */
public class TreeSelectionSort {
	public static void main(String[] args) {
		int a[] = { 49, 38, 65, 97, 76, 13, 27, 49, 78, 34, 12, 64, 5, 4, 62, 99, 98, 54, 56, 17, 18, 23, 34, 15, 35,
				25, 53, 51 };
		
		int[] result = _treeSelectionSort(a);
		
		// 打印快排后的数组
		for (int i = 0; i < result.length; i++)
			System.out.println(result[i]);
	}
	
	/**
	 * 树形选择排序算法，构造胜者树的过程，取根结点是最小关键值 
	 * @param before
	 * @return
	 */
	public static int[] _treeSelectionSort(int[] before) {
		int[] a = Arrays.copyOf(before, before.length);
		TreeNode[] tree; // 胜者树结点数组
		int leafSize = 1; // 胜者树叶子结点数
		// 得到叶子结点的个数，该个数必须是2的次幂
		while (leafSize < a.length) {
			leafSize *= 2;
		}
		int TreeSize = 2 * leafSize - 1; // 胜者树的所有结点数
		int loadIndex = leafSize - 1; // 叶子结点存放位置的起始位置
		tree = new TreeNode[TreeSize];

		int j = 0;
		// 把待排序结点复制到胜者树的叶子结点中
		for (int i = loadIndex; i < TreeSize; i++) {
			tree[i] = new TreeNode();
			tree[i].setIndex(i);
			if (j < a.length) {
				tree[i].setActive(1);
				tree[i].setData(a[j++]);
			} else {
				tree[i].setActive(0);
			}
		}
		int i = loadIndex; // 进行初始化，比较查找关键字值最小的结点
		while (i > 0) {
			j = i;
			// 处理各对比赛者
			while (j < TreeSize - 1) {
				if (tree[j + 1].getActive() == 0 || (tree[j].getData() <= tree[j + 1].getData())) {
					tree[(j - 1) / 2] = tree[j]; // 左孩子胜出
				} else {
					tree[(j - 1) / 2] = tree[j + 1]; // 右孩子胜出
				}
				j += 2; // 下一对比赛者
			}
			i = (i - 1) / 2; // 处理上层结点，类似下一轮比赛（已经决出一轮了）
		}

		// 处理剩余的n-1个元素
		for (i = 0; i < a.length - 1; i++) {
			a[i] = tree[0].getData(); // 将胜者树的根（最小值）存入数组
			tree[tree[0].getIndex()].setActive(0); // 冠军不再参加比赛
			updateTree(tree, tree[0].getIndex()); // 调整有胜者树
		}
		// 最后一个元素只需赋值就结束了 不需要再调整（即再进行下一轮比赛）
		a[a.length - 1] = tree[0].getData();
		return a;
	}

	/**
	 * @description 树形选择排序的调整算法 从当前最小关键字的叶子结点开始到根结点路径上的所有结点关键字的修改
	 * @param tree
	 * @param i 当前最小关键字的下标
	 */
	private static void updateTree(TreeNode[] tree, int i) {
		// 因为i是此时最小的关键字（已是冠军），所以在叶子结点中要将其除去比赛资格，对手直接晋级（升为父结点）
		if (i % 2 == 0) { // i为偶数，自己是右结点，对手是左结点,左结点晋级
			tree[(i - 1) / 2] = tree[i - 1];
		} else {
			tree[(i - 1) / 2] = tree[i + 1];
		}
		i = (i - 1) / 2;

		int j = 0;
		while (i > 0) {
			if (i % 2 == 0) { // i为偶数，自己是右结点，对手是左结点
				j = i - 1;
			} else {
				j = i + 1;
			}

			// 比赛对手中有一个为空
			if (tree[i].getActive() == 0 || tree[j].getActive() == 0) {
				if (tree[i].getActive() == 1) {
					tree[(i - 1) / 2] = tree[i];
				} else {
					tree[(i - 1) / 2] = tree[j];
				}
			}

			// 比赛对手都在
			if (tree[i].getData() < tree[j].getData()) {
				tree[(i - 1) / 2] = tree[i];
			} else {
				tree[(i - 1) / 2] = tree[j];
			}

			i = (i - 1) / 2;
		}
	}

	/**
	 * @description 树形选择排序的胜者树结点结构
	 */
	private static class TreeNode {
		private int data; // 数据域
		private int index; // 待插入结点在满二叉树中的序号
		private int active; // 参加选择标志,1表示参选，0表示不参选

		public int getData() {
			return data;
		}

		public void setData(int data) {
			this.data = data;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public int getActive() {
			return active;
		}

		public void setActive(int active) {
			this.active = active;
		}
	}
}
