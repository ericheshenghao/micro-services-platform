package cn.siques.activiti;

import cn.central.common.feign.UserInfoService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Lazy;

import javax.annotation.Resource;

@SpringBootTest
class ActivitiApplicationTests {

    @Resource
    RepositoryService repositoryService;



    @Test
    void testUserService() {

    }


    @Test
	void contextLoads() {
         ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
                query.processDefinitionTenantId("思趣")
                        .latestVersion().list();
	}

    @Test
    void suspend() {
        repositoryService.suspendProcessDefinitionById("123");
    }

}
