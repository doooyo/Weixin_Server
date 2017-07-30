package com.whayer.wx.wechat.util;

public enum CARD_TYPE {
	GROUPON("GROUPON"),              //团购券
	DISCOUNT("DISCOUNT"),            //折扣券
	GIFT("GIFT"),                    //礼品券
	CASH("CASH"),                    //代金券
	GENERAL_COUPON("GENERAL_COUPON"),//通用券(无法满足上述四种时)
	MEMBER_CARD("MEMBER_CARD");      //会员卡

    private String cardType;

    private CARD_TYPE(String cardType) {
        this.cardType = cardType;
    }
    
    @Override
    public String toString() {
        return cardType;
    }
}
