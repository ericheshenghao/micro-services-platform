package cn.central.common.model;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@TableName("sys_menu")
@ApiModel(description = "菜单或者权限表")
public class SysMenu extends SuperEntity<SysMenu>  {
    private static final long serialVersionUID = -4855350263474682807L;



    @TableField(exist = false)
    private String parentName;

    @TableField(exist = false)
    private Integer level;

    @TableField(exist = false)
    private List<SysMenu> children;


    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long parentId;


    @ApiModelProperty(value = "父菜单集合")
    private String parentIds;


    @TableField(exist = false)
    private List<String> parentArray;

    public List<String> getParentArray() {
        if(ObjectUtil.isNotNull(parentArray)) {
            return parentArray;
        }
        return Arrays.asList(this.parentIds.split(","))
                .stream().filter(s -> !"".equals(s)).collect(Collectors.toList());
    }

    private String url;

    @ApiModelProperty(value = "是否外链")
    private String target;

    private String perms;


    private Integer type;


    private String icon;


    private Integer orderNum;

    @ApiModelProperty(value = "状态")
    private Boolean status;

}