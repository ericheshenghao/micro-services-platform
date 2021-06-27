package cn.siques.activiti.service;

import cn.central.common.feign.UserInfoService;
import cn.central.common.model.Result;
import cn.central.common.model.SysUser;
import cn.siques.activiti.dao.ProcInstanceDao;
import cn.siques.activiti.dao.ProcTaskInstanceDao;
import cn.siques.activiti.dao.ProcUserGroupDao;
import cn.siques.activiti.entity.ProcInstance;
import cn.siques.activiti.entity.ProcTaskInstance;
import cn.siques.activiti.entity.ProcUserGroup;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.db.DbIdGenerator;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.IdGenerator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * @author: 何胜豪
 * @Title: TODO
 * @Package: cn.siques.activiti.service
 * @Description:
 * @date : 2021/6/25 20:50
 */
@Service
public class AuditService {

    @Autowired
    private ProcInstanceDao procInstanceDao;

    @Autowired
    private ProcTaskInstanceDao procTaskInstanceDao;

    @Autowired
    private ProcUserGroupDao procUserGroupDao;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 查询所有的申请流程
     * @param instance
     * @param pageNum
     * @param pageSize
     * @return
     */
    public Result getInstanceList(ProcInstance instance, int pageNum, int pageSize) {
        IPage<ProcInstance> userPage = new Page<>(pageNum, pageSize);

        QueryWrapper<ProcInstance> in = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(instance.getProcessKey())){
            in.eq("process_key", instance.getProcessKey());
        }

        if(ObjectUtils.isNotEmpty(instance.getProcCurrNodeUserId())){
            in.like("proc_curr_node_user_id", instance.getProcCurrNodeUserId());
        }

        if(ObjectUtils.isNotEmpty(instance.getProcessState())){
            in.in("process_state", instance.getProcessState().split(","));
        }

        if(ObjectUtils.isNotEmpty(instance.getUserCode())){
            in.eq("user_code", instance.getUserCode());
        }

        return Result.succeed( procInstanceDao.selectPage(userPage,in ));
    }

    /**
     * 根据id查询申请数据相应
     * @param id
     * @return
     */
    public ProcInstance findInstanceDetail(String id) {
        ProcInstance procInstance = procInstanceDao.selectById(id);
        return procInstance;
    }

    /**
     * 流程申请
     * @param map
     */
    public void start(Map map) {
        //1.构造业务数据
        String userCode = (String) map.get("userCode");
        String processName = (String) map.get("processName");
        String processKey = (String) map.get("processKey");
        SysUser sysUser = userInfoService.selectByUserCode(userCode);
        ProcInstance instance = new ProcInstance();
        BeanUtils.copyProperties(sysUser,instance);

        instance.setUserCode(userCode);
        instance.setProcessId(new DefaultIdentifierGenerator().nextUUID("1"));
        instance.setProcApplyTime(new Date());
        instance.setProcessKey(processKey);
        instance.setProcessName(processName);
        instance.setProcessState("1");
        String data = JSON.toJSONString(map);
        instance.setProcData(data);


        //2.查询流程定义
        ProcessDefinition definition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey("process_leave").latestVersion().singleResult();

        //3.开启流程
        Map vars = new HashMap();
        // 流程定义id，自定义id，
        if("process_leave".equals(processKey)){
            // 请假
            vars.put("day",map.get("duration"));
            vars.put("PROCESS_LEAVE_APPLY_USER","PROCESS_LEAVE_APPLY_USER");
            vars.put("PROCESS_LEAVE_HR_USER","PROCESS_LEAVE_HR_USER");
            vars.put("PROCESS_LEAVE_RE_USER","PROCESS_LEAVE_RE_USER");
            vars.put("PROCESS_LEAVE_HIGH_USER","PROCESS_LEAVE_HIGH_USER");
        }
        ProcessInstance processInstance = runtimeService.
                startProcessInstanceById(definition.getId(), instance.getProcessId(), vars);

        //4.执行第一个节点
        //4.1 获取待执行的节点
        Task task = taskService.createTaskQuery()
                .processInstanceId(processInstance.getId()).singleResult();
        taskService.complete(task.getId());
        //5.获取下一个节点
        Task next = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        if(next !=null){
            List<SysUser> currUsers = findCurrUsers(next, sysUser);
            String username="",userCodes="";
            for (SysUser currUser : currUsers) {
                username += currUser.getNickName()+" ";
                userCodes += currUser.getUserCode()+" ";
            }
            instance.setProcCurrNodeUserName(username);
            instance.setProcCurrNodeUserId(userCodes);
        }
        // 插入下一个节点的审批人

        procInstanceDao.insert(instance);
        ProcTaskInstance pti = new ProcTaskInstance();
//        pti.setTaskId(new DefaultIdentifierGenerator().nextUUID("1"));
        pti.setProcessId(instance.getProcessId());
        pti.setHandleTime(new Date());
        pti.setHandleType("2");
        pti.setHandleUserCode(userCode);
        pti.setShouldUserName(sysUser.getUserName());
        pti.setTaskKey(task.getTaskDefinitionKey());
        pti.setTaskName(task.getName());
        pti.setHandleOpinion("发起申请");
        procTaskInstanceDao.insert(pti);
    }

    private List<SysUser> findCurrUsers(Task nextTask,SysUser user){
        List<IdentityLink> list = taskService.getIdentityLinksForTask(nextTask.getId());
        List<SysUser> users = new ArrayList<>();
        for (IdentityLink link : list) {
            String groupId = link.getGroupId();
            ProcUserGroup userGroup = procUserGroupDao.selectOne(new QueryWrapper<ProcUserGroup>().eq("id",groupId));
           if(ObjectUtils.isNotEmpty(userGroup)){
               String param = userGroup.getParam();
               String isql = userGroup.getIsql();

               List<SysUser> res = jdbcTemplate.query(isql,new BeanPropertyRowMapper<SysUser>(SysUser.class));
               users.addAll(res);
           }
        }
        return users;
    }

    /**
     * 提交审核
     * @param taskInstance
     * @param tenant
     */
    public void commit(ProcTaskInstance taskInstance, String tenant) {
        String processId = taskInstance.getProcessId();
        ProcInstance instance = procInstanceDao.selectOne(new QueryWrapper<ProcInstance>().eq("process_id", processId));
        instance.setProcessState(taskInstance.getHandleType());
        SysUser sysUser = userInfoService.selectByUserCode(taskInstance.getHandleUserCode());
        // 根据不同的操作，如果审核通过
        // 查询流程实例 根据自己的业务id查询流程实例
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceBusinessKey(processId).singleResult();
        if("2".equals(taskInstance.getHandleType())){
            //3.1如果审核通过，完成当前的任务
            //查询出当前节点，并完成当前节点任务
            Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();

            taskService.complete(task.getId());
            //查询出下一个节点，如果存在，流程尚未结束，如果不存在任务结束
            //5.获取下一个节点
            Task next = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
            if(next !=null){
                List<SysUser> currUsers = findCurrUsers(next, sysUser);
                String username="",userCodes="";
                for (SysUser currUser : currUsers) {
                    username += currUser.getNickName()+" ";
                    userCodes += currUser.getUserCode()+" ";
                }
                instance.setProcCurrNodeUserName(username);
                instance.setProcCurrNodeUserId(userCodes);
                instance.setProcessState("1");
            }else{
                instance.setProcessState("2");
            }
        }else{
            // 如果审核不通过，或者撤销
            runtimeService.deleteProcessInstance(processInstance.getId(),taskInstance.getHandleOpinion());
        }
        // 更新业务流程对象
        procInstanceDao.updateById(instance);

        taskInstance.setTaskId("");
        taskInstance.setHandleUserName(sysUser.getUserName());
        taskInstance.setHandleUserCode(sysUser.getUserCode());
        taskInstance.setHandleTime(new Date());
        procTaskInstanceDao.insert(taskInstance);
    }
}
