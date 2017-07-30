package com.whayer.wx.wechat.util;

public enum DATE_TYPE {
	DATE_TYPE_FIX_TIME_RANGE("DATE_TYPE_FIX_TIME_RANGE"), //表示固定日期区间
	DATE_TYPE_FIX_TERM("DATE_TYPE_FIX_TERM"),             //表示固定时长（自领取后按天算）
	DATE_TYPE_PERMANENT("DATE_TYPE_PERMANENT");           //表示永久有效（会员卡类型专用）

    private String dateType;

    private DATE_TYPE(String dateType) {
        this.dateType = dateType;
    }
    
    @Override
    public String toString() {
        return dateType;
    }
}
