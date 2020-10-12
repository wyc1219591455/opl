package me.zhengjie.model.exception;

import lombok.Data;

@Data
public class MyException extends RuntimeException {

    private Integer code = -1; // 错误码 1:正确 0:无权限 -1：异常
    private String message;

    public MyException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public MyException(String message) {
        super(message);
        this.message = message;
    }
}
