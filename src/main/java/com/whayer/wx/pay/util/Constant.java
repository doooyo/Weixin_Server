package com.whayer.wx.pay.util;

public class Constant {
	public static final String DOMAIN = "https://skyg.com.cn";

    public static final String APP_ID = "wx95e0f6128d7e91f6";  

    public static final String APP_SECRET = "bf64789369b16c812f0ebdae79c8154e";

    public static final String APP_KEY = ""; 

    public static final String MCH_ID = "";  //商户号

    public static final String URL_UNIFIED_ORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    
    public static final String URL_OPENID = "https://api.weixin.qq.com/sns/jscode2session";

    public static final String URL_NOTIFY = Constant.DOMAIN + "/payV2/callback"; 

    public static final String TIME_FORMAT = "yyyyMMddHHmmss";

    public static final int TIME_EXPIRE = 2;  
}
