package cn.central.common.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.bytebuddy.implementation.bind.annotation.Super;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Data
@TableName("sys_role")
@ApiModel(description = "角色表")
public class SysRole extends SuperEntity<SysRole>  {

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