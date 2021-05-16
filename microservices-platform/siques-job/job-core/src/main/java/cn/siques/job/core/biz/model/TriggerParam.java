package cn.siques.job.core.biz.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : heshenghao
 * @date : 15:37 2021/5/16
 */
@Data
public class TriggerParam implements Serializable {

    private static final long serialVersionUID = 42L;

    private int jobId;

    private String executorHandler;
    private String executorParams;
    private String executorBlockStrategy;
    private int executorTimeout;

    private int logId;
    private long logDateTim;

    private String glueType;
    private String glueSource;
    private long glueUpdateTime;

    private int broadcastIndex;
    private int broadcastTotal;
}
