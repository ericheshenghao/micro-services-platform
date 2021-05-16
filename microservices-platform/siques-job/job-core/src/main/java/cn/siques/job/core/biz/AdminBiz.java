package cn.siques.job.core.biz;

import cn.siques.job.core.biz.model.HandleCallbackParam;
import cn.siques.job.core.biz.model.RegistryParam;
import cn.siques.job.core.biz.model.ReturnT;

import java.util.List;

/**
 * @author : heshenghao
 * @date : 15:26 2021/5/16
 */
public interface AdminBiz {

    public static final String MAPPING = "/api";

    public ReturnT<String> callback(List<HandleCallbackParam> callbackParamList);

    public ReturnT<String> registry(RegistryParam registryParam);

    public ReturnT<String> registryRemove(RegistryParam registryParam);
}
