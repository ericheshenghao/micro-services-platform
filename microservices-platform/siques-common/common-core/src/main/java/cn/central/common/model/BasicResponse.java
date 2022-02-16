package cn.central.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * @Author:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasicResponse<T> implements Serializable {

    private T data;
    private Integer code;
    private String msg;

    public static <T> BasicResponse<T> succeed(String msg) {
        return of(null, HttpStatus.OK.value(), msg);
    }

    public static <T> BasicResponse<T> succeed(T data, String msg) {
        return of(data, HttpStatus.OK.value(), msg);
    }

    public static <T> BasicResponse<T> succeed(T data) {
        return of(data,  HttpStatus.OK.value(), "");
    }

    public static <T> BasicResponse<T> of(T data, Integer code, String msg) {
        return new BasicResponse<>(data, code, msg);
    }

    public static <T> BasicResponse<T> failed(String msg) {
        return of(null, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg);
    }

    public static <T> BasicResponse<T> failed(String msg, Integer code) {
        return of(null, code, msg);
    }

    public static <T> BasicResponse<T> failed(T data, String msg) {
        return of(data, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg);
    }


}
