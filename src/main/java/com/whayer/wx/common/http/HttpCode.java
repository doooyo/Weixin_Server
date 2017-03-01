package com.whayer.wx.common.http;

public enum HttpCode {
	INVALID(-1),
    HTTP_NONE(0),

    HTTP_OK(200),

    HTTP_MOVED_PERMANENTLY(301),   // 永久重定向
    HTTP_FOUND(302),               // 临时重定向

    HTTP_BAD_REQUEST(400),         //参数错误


    // 参数错误
    HTTP_UNAUTHORIZED(401),        // 身份验证失败，无token信息
    HTTP_FORBIDDEN(403),           // 无权限
    HTTP_NOT_FOUND(404),
    HTTP_METHOD_NOT_ALLOWED(405),  // 资源被禁止，例如：Http Method 不正确

    HTTP_INTERNAL_SERVER_ERROR(500); // 程序代码抛出异常

    // 定义私有变量
    private int val;

    // 构造函数，枚举类型只能为私有
    private HttpCode(int val) {
        this.val = val;
    }

    public int value() {
        return this.val;
    }

    @Override
    public String toString() {
        return String.valueOf(this.val);
    }
}
