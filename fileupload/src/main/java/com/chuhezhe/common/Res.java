package com.chuhezhe.common;

/**
 * 生成返回结果的工具类
 *
 * ClassName: Res
 * Package: com.chuhezhe.common
 * Description:
 *
 * @Author Chuhezhe
 * @Create 2023/10/22 15:56
 * @Version 1.0
 */
public class Res {
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    private static final int SUCCESS_CODE = 0;
    private static final int FAIL_CODE = 1;

    public static <T> Result<T> ok() {return new Result<>(SUCCESS_CODE, SUCCESS);}

    public static <T> Result<T> ok(T data) {return new Result<>(SUCCESS, SUCCESS_CODE, data);}

    public static <T> Result<T> error() {return new Result<>(FAIL, FAIL_CODE, false);}
    public static <T> Result<T> error(String message) {return new Result<>(message, FAIL_CODE, false);}
}
