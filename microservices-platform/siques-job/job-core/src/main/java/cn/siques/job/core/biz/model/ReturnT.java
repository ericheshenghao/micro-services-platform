package cn.siques.job.core.biz.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : heshenghao
 * @date : 15:29 2021/5/16
 */
@Data
public class ReturnT<T> implements Serializable {
    public static final long serialVersionUID = 42L;

    public static final int SUCCESS_CODE = 1;
    public static final int FAIL_CODE = 0;
    private int code;
    private String msg;
    private T content;

    public static final ReturnT<String> SUCCESS = new ReturnT<>(null);
    public static final ReturnT<String> FAIL = new ReturnT<String>(FAIL_CODE, null);

    public ReturnT() {
    }

    public ReturnT(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ReturnT(T content) {
        this.code = SUCCESS_CODE;
        this.content = content;
    }


}
