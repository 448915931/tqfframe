package com.tqfframe.exception;

/**
 * 描述：
 * <p>
 *
 * @author: 赵新国
 * @date: 2018/4/11 23:06
 */
public class BaseException extends RuntimeException {

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }
}

