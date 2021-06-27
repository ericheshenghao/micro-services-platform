package cn.central.entity;

import cn.central.common.model.SuperEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("sys_role_menu")
@ApiModel(description = "角色菜单表")
public class SysRoleMenu extends Model<SysRoleMenu> implements Serializable {

    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "编号")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String id;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String roleId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String menuId;

    public SysRoleMenu(String roleId, String menuId) {
        this.roleId = roleId;
        this.menuId = menuId;
    }
}