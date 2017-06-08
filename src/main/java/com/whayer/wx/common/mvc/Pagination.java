package com.whayer.wx.common.mvc;

/**
 * https://github.com/pagehelper/Mybatis-PageHelper
 * @author duyu
 *
 */
public final class Pagination {
	private int pageNum = 1;        // 当前页码
	private int pageSize = 20;      // 每页size 默认20
	private int navigationSize = 10; // 分页条相邻页数量 默认10

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getNavigationSize() {
		return navigationSize;
	}

	public void setNavigationSize(int navigationSize) {
		this.navigationSize = navigationSize;
	}
}
