package cn.siques.job.core.biz;

import cn.siques.job.core.biz.model.LogResult;
import cn.siques.job.core.biz.model.ReturnT;
import cn.siques.job.core.biz.model.TriggerParam;

/**
 * @author : heshenghao
 * @date : 15:27 2021/5/16
 */
public interface ExecutorBiz {

    public ReturnT<String> beat();

    public ReturnT<String> idleBeat(int jobId);


    public ReturnT<String> kill(int jobId);


    public ReturnT<LogResult> log(long logDateTim, int logId, int fromLineNum);


    public ReturnT<String> run(TriggerParam triggerParam);

}
