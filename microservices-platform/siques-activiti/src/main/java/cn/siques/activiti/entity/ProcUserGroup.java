package cn.siques.activiti.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

/**
 * @author: 何胜豪
 * @Title: TODO
 * @Package: cn.siques.activiti.entity
 * @Description:
 * @date : 2021/6/25 23:25
 */
@Data
@TableName("sys_proc_user_group")
@ApiModel(description = "流程用户组表")
public class ProcUserGroup {

    @TableId
    String id;
    String name;
    String param;
    String isql;
    String isValid;

    private String createBy;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    private String lastUpdateBy;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date lastUpdateTime;
}
