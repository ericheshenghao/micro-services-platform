package cn.central.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> implements Serializable {

    private T data;
    private Integer respCode;
    private String respMsg;

    public static <T> Result<T> succeed(String msg) {
        return of(null, HttpStatus.SUCCESS, msg);
    }

    public static <T> Result<T> succeed(T data, String msg) {
        return of(data, HttpStatus.SUCCESS, msg);
    }

    public static <T> Result<T> succeed(T data) {
        return of(data, HttpStatus.SUCCESS, "");
    }

    public static <T> Result<T> of(T data, Integer code, String msg) {
        return new Result<>(data, code, msg);
    }

    public static <T> Result<T> failed(String msg) {
        return of(null, HttpStatus.FAILED, msg);
    }

    public static <T> Result<T> failed(T data, String msg) {
        return of(data, HttpStatus.FAILED, msg);
    }
}
