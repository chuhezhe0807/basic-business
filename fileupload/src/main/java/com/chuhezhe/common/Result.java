package com.chuhezhe.common;

import java.io.Serializable;

/**
 * ClassName: Result
 * Package: com.chuhezhe.common
 * Description:
 *
 * @Author Chuhezhe
 * @Create 2023/10/22 16:02
 * @Version 1.0
 */
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 20231022L;

    // 信息，提供给调用者看
    private String msg = "success";

    // 状态码
    private int code = 0;

    private boolean success = true;

    // 反参数据体
    private T data;

    public Result() {
        super();
    }

    public Result(int code) {
        this.code = code;
    }

    public Result(int code, String msg) {
        this.msg = msg;
        this.code = code;
    }

    public Result(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public Result(String msg, int code, boolean success) {
        this.msg = msg;
        this.code = code;
        this.success = success;
    }

    public Result(String msg, int code, T data) {
        this.msg = msg;
        this.code = code;
        this.data = data;
    }

    public Result(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
