package com.tqfframe;


import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tang-QiFeng on 2019/4/8
 */
public class ResultUtil extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public ResultUtil() {
        put("code", 0);
        put("msg", "success");
    }

    public static ResultUtil error() {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
    }

    public static ResultUtil error(String msg) {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
    }

    public static ResultUtil error(int code, String msg) {
        ResultUtil r = new ResultUtil();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static ResultUtil ok(String msg) {
        ResultUtil r = new ResultUtil();
        r.put("msg", msg);
        return r;
    }

    public static ResultUtil ok(Map<String, Object> map) {
        ResultUtil r = new ResultUtil();
        r.putAll(map);
        return r;
    }

    public static ResultUtil ok() {
        return new ResultUtil();
    }

    public ResultUtil put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
