package cn.siques.activiti.service;

import cn.central.common.feign.UserInfoService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: 何胜豪
 * @Title: TODO
 * @Package: cn.siques.activiti.service
 * @Description:
 * @date : 2021/6/25 21:45
 */
@Service
public class ProcessService {
    @Autowired
    RepositoryService repositoryService;


    @Autowired
    UserInfoService userInfoService;

    public List getProcessDefinitionList(String componentId) {
        ProcessDefinitionQuery q = repositoryService.createProcessDefinitionQuery();
        List<ProcessDefinition> list = q.processDefinitionTenantId(componentId)
                .latestVersion().list();
        return list;
    }
}
