package cn.central.common.model;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * @author 用户实体
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_user")
@ApiModel(description = "用户表")
public class SysUser extends SuperEntity<SysUser> {
    private static final long serialVersionUID = -5886012896705137070L;


    @ApiModelProperty(value = "用户编码")
    @NotNull(message = "用户编码不能为空！")
    private String userCode;

    @ApiModelProperty(value = "登录账号")
    @NotNull(message = "登录账号不能为空！")
    private String userName;

    @ApiModelProperty(value = "登录密码")
    @JsonIgnore
    private String password = "123456";

    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    @ApiModelProperty(value = "头像路径")
    private String avatar;

    @ApiModelProperty(value = "电子邮箱")
    private String email;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "用户状态")
    private Boolean status;


    @ApiModelProperty(value = "排序")
    private Integer orderNum;


    @TableField(exist = false)
    private List<SysRole> sysRoles;

    @TableField(exist = false)
    private Set<String> permissions;

    @TableField(exist = false)
    private List<String> roleIds;


    public Map<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<>();
        if (ObjectUtil.isNotEmpty(userCode)) {
            map.put("user_code", userCode);
        }
        if (ObjectUtil.isNotEmpty(userName)) {
            map.put("user_name", userName);
        }
        if (ObjectUtil.isNotEmpty(nickName)) {
            map.put("nick_name", nickName);
        }
        if (ObjectUtil.isNotEmpty(email)) {
            map.put("email", email);
        }
        if (ObjectUtil.isNotEmpty(mobile)) {
            map.put("mobile", mobile);
        }
        if (ObjectUtil.isNotEmpty(status)) {
            map.put("status", status);
        }
        return map;
    }
}
