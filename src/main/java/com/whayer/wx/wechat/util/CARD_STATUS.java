package com.whayer.wx.wechat.util;

public enum CARD_STATUS {
	CARD_STATUS_NOT_VERIFY("CARD_STATUS_NOT_VERIFY"),   //待审核
	CARD_STATUS_VERIFY_FAIL("CARD_STATUS_VERIFY_FAIL"), //审核失败
	CARD_STATUS_VERIFY_OK("CARD_STATUS_VERIFY_OK"),     //通过审核
	CARD_STATUS_DELETE("CARD_STATUS_DELETE"),           //卡券被商户删除
	CARD_STATUS_DISPATCH("CARD_STATUS_DISPATCH");       //在公众平台投放过的卡券

    private String cardStatus;

    private CARD_STATUS(String cardStatus) {
        this.cardStatus = cardStatus;
    }
    
    @Override
    public String toString() {
        return cardStatus;
    }
}
