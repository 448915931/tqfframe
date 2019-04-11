package com.tqfframe.exception;

/**
 * @author zhaoxinguo on 2017/9/13.
 */
public class UsernameIsExitedException extends BaseException {

    public UsernameIsExitedException(String msg) {
        super(msg); //调取父类的构造方法
    }

    public UsernameIsExitedException(String msg, Throwable t) {
        super(msg, t);
    }
}