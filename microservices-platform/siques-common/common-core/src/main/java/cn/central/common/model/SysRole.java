package cn.central.common.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


/**
 * @author Administrator
 */
@Data
@TableName("sys_role")
@ApiModel(description = "角色表")
public class SysRole extends SuperEntity<SysRole> {

    @ApiModelProperty(value = "角色编码")
    private String roleCode;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "角色排序")
    public String roleSort;

    @ApiModelProperty(value = "状态")
    private Byte status;

    @ApiModelProperty(value = "角色描述")
    private String description;


    @TableField(exist = false)
    private List<SysMenu> sysMenuList;
}