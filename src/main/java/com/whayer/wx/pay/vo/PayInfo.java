package com.whayer.wx.pay.vo;


/**
 * @see https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=9_1
 * @author duyu
 *
 */
public class PayInfo {

	private String appid;
    private String mch_id;
    private String device_info; //终端设备号，手机与PC传"WEB"
    private String nonce_str;   //随机字符串，不长于32位
    /**
     * 签名:
     */
    private String sign;         
    private String sign_type;  //签名类型(非必须,默认MD5)
    private String body;       //商品描述(尚康阳光-检测商品)
    //private String detail;   //商品详情,非必须
    private String attach;     //订单自定义原样返回的数据(非必须	)
    /**
     * 商户支付的订单号由商户自定义生成，微信支付要求商户订单号保持唯一性（建议根据当前系统时间加随机序列来生成订单号）。
     * 重新发起一笔支付要使用原订单号，避免重复支付；已支付过或已调用关单、撤销（请见后文的API列表）的订单号不能重新发起支付。
     * String(32) 字母&数字
     */
    private String out_trade_no; 
    
    private String fee_type;  //默认支持人名币 CNY
    /**
     * 交易金额默认为人民币交易，接口中参数支付金额单位为【分】，参数值不能带小数。对账单中的交易金额单位为【元】。
	 * 外币交易的支付金额精确到币种的最小单位，参数值不能带小数点。
     */
    private int total_fee;
    private String spbill_create_ip; //终端IP
    private String time_start;       //订单生成时间，格式为yyyyMMddHHmmss
    private String time_expire;      //订单失效时间，格式为yyyyMMddHHmmss,最短失效时间间隔必须大于5分钟
    //private String goods_tag;  //商品标记，代金券或立减优惠功能的参数
    /**
     * 接收微信支付异步通知回调地址，通知url必须为直接可访问，不能携带参数
     */
    private String notify_url;
    private String trade_type; //交易类型,JSAPI
    private String limit_pay;  //指定支付方式，no_credit  指定不能使用信用卡支付
    private String openid;     //登陆code换取openid 和 session_key

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getDevice_info() {
        return device_info;
    }

    public void setDevice_info(String device_info) {
        this.device_info = device_info;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public int getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(int total_fee) {
        this.total_fee = total_fee;
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }

    public String getTime_start() {
        return time_start;
    }

    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }

    public String getTime_expire() {
        return time_expire;
    }

    public void setTime_expire(String time_expire) {
        this.time_expire = time_expire;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getLimit_pay() {
        return limit_pay;
    }

    public void setLimit_pay(String limit_pay) {
        this.limit_pay = limit_pay;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

	public String getFee_type() {
		return fee_type;
	}

	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}

	@Override
	public String toString() {
		return "PayInfo [appid=" + appid + ", mch_id=" + mch_id + ", device_info=" + device_info + ", nonce_str="
				+ nonce_str + ", sign=" + sign + ", sign_type=" + sign_type + ", body=" + body + ", attach=" + attach
				+ ", out_trade_no=" + out_trade_no + ", fee_type=" + fee_type + ", total_fee=" + total_fee
				+ ", spbill_create_ip=" + spbill_create_ip + ", time_start=" + time_start + ", time_expire="
				+ time_expire + ", notify_url=" + notify_url + ", trade_type=" + trade_type + ", limit_pay=" + limit_pay
				+ ", openid=" + openid + "]";
	}
    
}
