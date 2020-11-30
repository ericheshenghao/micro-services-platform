package cn.central.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;


@Data
@RequiredArgsConstructor
@ApiModel(description = "用户角色表")
public class SysUserRole extends Model<SysUserRole> {

    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "编号")
    private Long id;

    private Long userId;


    private Long roleId;

    private String remarks;

    public SysUserRole(long userId, long roleId) {
        this.userId=userId;
        this.roleId=roleId;
    }
}