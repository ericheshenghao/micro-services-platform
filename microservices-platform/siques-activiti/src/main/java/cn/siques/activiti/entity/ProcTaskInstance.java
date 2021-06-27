package cn.siques.activiti.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author: 何胜豪
 * @Title: TODO
 * @Package: cn.siques.activiti.entity
 * @Description:
 * @date : 2021/6/25 23:20
 */
@Data
@TableName("sys_proc_task_instance")
@ApiModel(description = "流程任务表")
public class ProcTaskInstance {
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "编号")
    String taskId;
    String processId;
    String taskKey;
    String taskName;
    String shouldUserCode;
    String shouldUserName;
    String handleUserCode;
    String handleUserName;
    Date handleTime;
    String handleOpinion;
    @NotNull(message = "handleType不能为空")
    String handleType;

}
