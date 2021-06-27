package cn.siques.job.core.biz.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : heshenghao
 * @date : 15:36 2021/5/16
 */
@Data
public class LogResult implements Serializable {
    private static final long serialVersionUID = 42L;

    public LogResult(int fromLineNum, int toLineNum, String logContent, boolean isEnd) {
        this.fromLineNum = fromLineNum;
        this.toLineNum = toLineNum;
        this.logContent = logContent;
        this.isEnd = isEnd;
    }

    private int fromLineNum;
    private int toLineNum;
    private String logContent;
    private boolean isEnd;


}
