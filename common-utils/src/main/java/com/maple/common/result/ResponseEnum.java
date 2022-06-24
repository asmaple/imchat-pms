package com.maple.common.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;


@Getter
@AllArgsConstructor
@ToString
public enum ResponseEnum {

    SUCCESS(200, "操作成功！"),
    ERROR(-1, "操作失败！"),

    //-1xx 服务器错误
    PARAMETER_ERROR(-100, "参数错误"),
    BAD_SQL_GRAMMAR_ERROR(-101, "sql语法错误"),
    SERVLET_ERROR(-102, "servlet请求异常"),

    // -2xx 用户错误
    LOGIN_AUTH_ERROR(-200, "未登录"),
    ACCOUNT_NOT_FOUNT(-201, "用户不存在"),
    ACCOUNT_EXIST_ERROR(202, "该账号已被注册"),
    LOGIN_ERROR(-203, "账号或密码错误"),
    CODE_ERROR(-204, "验证码错误"),
    EMAIL_ERROR(-205, "邮箱格式错误"),
    PHONE_ERROR(-206, "手机号格式错误"),
    PASSWORD_ERROR(-207, "密码格式错误"),
    LOGIN_LOKED_ERROR(-208, "您的账号被锁定,无法操作！"),
    ROLE_NOT_PERMISSION(-209, "您的账号暂无权限,无法操作！"),

    AUTH_FAIL(-401, "请重新登陆"),


    // -7xx 文件错误
    UPLOAD_ERROR(-701, "文件上传错误"),
    DOWNLOAD_ERROR(-702, "文件下载错误"),


    DATA_EMPTY(900, "暂无数据！"),
    ;


    //响应状态码
    private Integer code;
    //响应信息
    private String message;
}
