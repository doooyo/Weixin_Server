package com.whayer.wx.pay.util;

public class Constant {
	public static final String DOMAIN = "https://scskss.com";

	//唯一id
    public static final String APP_ID = "wx2e4ffffd52348193"; 

    //应用密匙(API接口密码)
    public static final String APP_SECRET = "5fb1312de6c4ec7f6433fc37efae20ad"; 

    //签名密钥
    public static final String APP_KEY = "4QwPY2JcpDCzaxWBZtsK49DiOKTlYTxO"; 

    public static final String MCH_ID = "1465571902";  //商户号

    public static final String URL_UNIFIED_ORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    
    public static final String URL_OPENID = "https://api.weixin.qq.com/sns/jscode2session";
    
    public static final String URL_ORDER_QUERY = "https://api.mch.weixin.qq.com/pay/orderquery";

    public static final String URL_NOTIFY = DOMAIN + "/payV2/callback"; 
    
    public static final String URL_BARCODE = DOMAIN + "/gift/gift.html"; 

    public static final String TIME_FORMAT = "yyyyMMddHHmmss";

    public static final int TIME_EXPIRE = 2;  
}
