package com.whayer.wx.pay.util;

public class Constant {
	public static final String DOMAIN = "m";

	//唯一id
    public static final String APP_ID = ""; 

    //应用密匙(API接口密码)
    public static final String APP_SECRET = ""; 

    //签名密钥
    public static final String APP_KEY = ""; 

    public static final String MCH_ID = "";  //商户号

    public static final String URL_UNIFIED_ORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    
    public static final String URL_OPENID = "https://api.weixin.qq.com/sns/jscode2session";
    
    public static final String URL_ORDER_QUERY = "https://api.mch.weixin.qq.com/pay/orderquery";

    public static final String URL_NOTIFY = DOMAIN + ""; 
    
    public static final String URL_BARCODE = DOMAIN + ""; 

    public static final String TIME_FORMAT = "yyyyMMddHHmmss";

    public static final int TIME_EXPIRE = 2;  
}
