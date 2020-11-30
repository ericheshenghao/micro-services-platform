package cn.central.entity;

import cn.central.common.model.SuperEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_office")
@ApiModel(description = "部门表")
public class SysOffice extends SuperEntity<SysOffice> {

    @ApiModelProperty(value = "全节点名")
    private String treeNames;

    private  String parentIds;

    private Long parentId;

    private String officeName;

    private Integer orderNum;

    private Byte status;

    @TableField(exist = false)
    private String parentName;

    @TableField(exist = false)
    private Integer level;

    @TableField(exist = false)
    private List<SysOffice> children;

    @TableField(exist = false)
    private List<Long>  officeList;

    public String getParentIds() {
        return parentIds;
    }

    // 设置list
    public void setParentIds(String parentIds) {
        Long id = this.getId();
        String[] split = parentIds.split(",");

        List<Long> collect = Arrays.stream(split)
                .mapToLong(Long::valueOf)
                .boxed()
                .collect(Collectors.toList());
        collect.add(id);
        this.officeList=collect;
        this.parentIds = parentIds;
    }

}