package cn.siques.job.core.biz.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : heshenghao
 * @date : 15:33 2021/5/16
 */
@Data
public class HandleCallbackParam implements Serializable {
    private static final long  serialVersionUID = 42L;
    private int logId;
    private long logDateTim;

    private ReturnT<String> executeResult;

    public HandleCallbackParam(){}
    public HandleCallbackParam(int logId, long logDateTim, ReturnT<String> executeResult) {
        this.logId = logId;
        this.logDateTim = logDateTim;
        this.executeResult = executeResult;
    }

}
