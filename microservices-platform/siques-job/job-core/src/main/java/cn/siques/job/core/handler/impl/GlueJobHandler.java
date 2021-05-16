package cn.siques.job.core.handler.impl;

import cn.siques.job.core.biz.model.ReturnT;
import cn.siques.job.core.handler.IJobHandler;
import cn.siques.job.core.log.XxlJobLogger;

/**
 * @author : heshenghao
 * @date : 15:45 2021/5/16
 */
public class GlueJobHandler  extends IJobHandler {

    private long glueUpdatetime;
    private IJobHandler jobHandler;
    public GlueJobHandler(IJobHandler jobHandler, long glueUpdatetime) {
        this.jobHandler = jobHandler;
        this.glueUpdatetime = glueUpdatetime;
    }
    public long getGlueUpdatetime() {
        return glueUpdatetime;
    }

    @Override
    public ReturnT<String> execute(String param) throws Exception {
        XxlJobLogger.log("----------- glue.version:"+ glueUpdatetime +" -----------");
        return jobHandler.execute(param);
    }

}