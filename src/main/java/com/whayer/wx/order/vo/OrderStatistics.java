package com.whayer.wx.order.vo;

import java.io.Serializable;
import java.math.BigDecimal;


public class OrderStatistics implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private BigDecimal totalFee;       //总金额
	private BigDecimal unfinishedFee;  //未完成金额
	private BigDecimal finishedFee;    //已完成金额
	private Integer unpaidCount;       //未支付数量
	private Integer unbindBoxCount;    //未绑定检测盒数量
	private Integer unsettledCount;    //未结算数量
	private Integer settledCount;      //已结算数量
	
	public BigDecimal getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}
	public BigDecimal getUnfinishedFee() {
		return unfinishedFee;
	}
	public void setUnfinishedFee(BigDecimal unfinishedFee) {
		this.unfinishedFee = unfinishedFee;
	}
	public BigDecimal getFinishedFee() {
		return finishedFee;
	}
	public void setFinishedFee(BigDecimal finishedFee) {
		this.finishedFee = finishedFee;
	}
	public Integer getUnpaidCount() {
		return unpaidCount;
	}
	public void setUnpaidCount(Integer unpaidCount) {
		this.unpaidCount = unpaidCount;
	}
	public Integer getUnbindBoxCount() {
		return unbindBoxCount;
	}
	public void setUnbindBoxCount(Integer unbindBoxCount) {
		this.unbindBoxCount = unbindBoxCount;
	}
	public Integer getUnsettledCount() {
		return unsettledCount;
	}
	public void setUnsettledCount(Integer unsettledCount) {
		this.unsettledCount = unsettledCount;
	}
	public Integer getSettledCount() {
		return settledCount;
	}
	public void setSettledCount(Integer settledCount) {
		this.settledCount = settledCount;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "OrderStatistics [totalFee=" + totalFee + ", unfinishedFee=" + unfinishedFee + ", finishedFee="
				+ finishedFee + ", unpaidCount=" + unpaidCount + ", unbindBoxCount=" + unbindBoxCount
				+ ", unsettledCount=" + unsettledCount + ", settledCount=" + settledCount + "]";
	}
	
}
