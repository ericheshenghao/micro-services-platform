package cn.siques.activiti.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author: 何胜豪
 * @Title: TODO
 * @Package: cn.siques.activiti.entity
 * @Description:
 * @date : 2021/6/25 20:45
 */
@Data
@TableName("sys_proc_instance")
@ApiModel(description = "流程实体表")
public class ProcInstance extends Model<ProcInstance> {

    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "编号")
    private String processId;
    private String processDefinitionId;
    private String processKey;
    private String processName;
    private String processState;
    private String userCode;
    private String username;
//    private Long departmentId;
//    private String departmentName;
    private Date timeOfEntry;

    private String procCurrNodeUserCode;
    private String procCurrNodeUserName;
    private Date procApplyTime;
    private Date procEndTime;
    private String procData;

}
