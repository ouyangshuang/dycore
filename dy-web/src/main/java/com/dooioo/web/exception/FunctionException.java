package com.dooioo.web.exception;

/**
 * 方法执行时出现的异常
 * 捕获该异常会返回 500状态
 * User: kuang
 * Date: 12-8-25
 * Time: 下午10:09
 */
public class FunctionException extends Exception {

    public FunctionException(String message) {
        super(message);
    }
}
