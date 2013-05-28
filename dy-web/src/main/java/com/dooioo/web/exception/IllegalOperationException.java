package com.dooioo.web.exception;

/**
 * 非法操作异常
 * 例如：没有权限
 *
 * User: kuang
 * Date: 12-8-31
 * Time: 下午3:39
 */
public class IllegalOperationException extends Exception {

    public IllegalOperationException(String message) {
        super(message);
    }
}
