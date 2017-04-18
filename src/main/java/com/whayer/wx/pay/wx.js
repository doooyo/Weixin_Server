 var wxPay = function(e) {
        var code = '' //传给服务器以获得openId
        var timestamp = String(Date.parse(new Date())) //时间戳
        var nonceStr = '' //随机字符串，后台返回
        var prepayId = '' //预支付id，后台返回
        var paySign = '' //加密字符串

        //获取用户登录状态
        wx.login({
            success: function(res) {
                if (res.code) {
                    code = res.code //wx.login() 获取登陆状态,并拿到code
                        //发起网络请求,发起的是HTTPS请求，向服务端请求预支付
                    wx.request({
                        url: 'https://i-test.com.cn/wxpay/prepay.do',
                        data: {
                            code: res.code
                        },
                        success: function(res) {
                            if (res.data.result == true) {
                                nonceStr = res.data.nonceStr //后台返回到随机字符串
                                prepayId = res.data.prepayId //预支付id
                                    // 按字典序键值对MD5加密
                                    //appId,nonceStr,package,signType,timeStamp
                                var payDataA = "appId=" + app.globalData.appId + "&nonceStr=" + res.data.nonceStr + "&package=prepay_id=" + res.data.prepayId + "&signType=MD5&timeStamp=" + timestamp;
                                var payDataB = payDataA + "&key=" + app.globalData.key;
                                // 使用MD5加密算法计算加密字符串
                                paySign = MD5Util.MD5(payDataB).toUpperCase();
                                // 发起微信支付
                                wx.requestPayment({
                                    'timeStamp': timestamp,
                                    'nonceStr': nonceStr,
                                    'package': 'prepay_id=' + prepayId,
                                    'signType': 'MD5',
                                    'paySign': paySign,
                                    'success': function(res) {
                                        // 保留当前页面，跳转到应用内某个页面，使用wx.nevigeteBack可以返回原页面
                                        wx.navigateTo({
                                            url: '../pay/pay'
                                        })
                                    },
                                    'fail': function(res) {
                                        console.log(res.errMsg)
                                    }
                                })
                            } else {
                                console.log('请求失败' + res.data.info)
                            }
                        }
                    })
                } else {
                    console.log('获取用户登录态失败！' + res.errMsg)
                }
            }
        });
    }