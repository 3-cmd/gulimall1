package com.cs.exception;

/**
 * 错误码和错误信息定义的枚举类
 * 1.错误码定义规则为5位数字
 * 2.前两位表示业务场景,后三位表示错误码
 * 10:通用
 * 11:商品
 * 12:订单
 * 13:购物车
 * 14:物流
 */
public enum BizCodeEnum {
    UNKONW_EXCEPTION(10000,"系统位置异常"),
    VAILD_EXCEPTION(10001,"参数格式校验失败");

    private int code;
    private String message;
    BizCodeEnum(int code,String message){
        this.code=code;
        this.message=message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
