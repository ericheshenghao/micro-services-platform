package cn.central.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 字典数据表
 * </p>
 *
 * @package:  cn.siques.mangosound.entity
 * @description: 字典数据表
 * @author: Shenghao.He
 * @date: Created in 2020-09-03 11:29:19
 * @copyright: Copyright (c) 2020
 * @version: V1.0
 * @modified: Shenghao.He
 */
@Data
@TableName("sys_dict_data")
@ApiModel(description = "字典数据表")
@EqualsAndHashCode(callSuper = true)
public class SysDictData extends Model<SysDictData> {
    private static final long serialVersionUID = 1L;

    /**
     * 树
     */
    @TableField(exist = false)
    private List<SysDictData> children;

    /**
     * 字典编码
     */
    @TableId
    @ApiModelProperty(value = "字典编码")
    private String dictCode;
    /**
     * 父级编号
     */
    @ApiModelProperty(value = "父级编号")
    private String parentCode;
    /**
     * 所有父级编号
     */
    @ApiModelProperty(value = "所有父级编号")
    private String parentCodes;
    /**
     * 本级排序号（升序）
     */
    @ApiModelProperty(value = "本级排序号（升序）")
    private BigDecimal treeSort;
    /**
     * 所有级别排序号
     */
    @ApiModelProperty(value = "所有级别排序号")
    private String treeSorts;
    /**
     * 是否最末级
     */
    @ApiModelProperty(value = "是否最末级")
    private String treeLeaf;
    /**
     * 层次级别
     */
    @ApiModelProperty(value = "层次级别")
    private BigDecimal treeLevel;
    /**
     * 全节点名
     */
    @ApiModelProperty(value = "全节点名")
    private String treeNames;
    /**
     * 字典标签
     */
    @ApiModelProperty(value = "字典标签")
    private String dictLabel;
    /**
     * 字典键值
     */
    @ApiModelProperty(value = "字典键值")
    private String dictValue;
    /**
     * 字典类型
     */
    @ApiModelProperty(value = "字典类型")
    private String dictType;
    /**
     * 系统内置（1是 0否）
     */
    @ApiModelProperty(value = "系统内置（1是 0否）")
    private String isSys;
    /**
     * 字典描述
     */
    @ApiModelProperty(value = "字典描述")
    private String description;
    /**
     * css样式（如：color:red)
     */
    @ApiModelProperty(value = "css样式（如：color:red)")
    private String cssStyle;
    /**
     * css类名（如：red）
     */
    @ApiModelProperty(value = "css类名（如：red）")
    private String cssClass;
    /**
     * 状态（0正常 1删除 2停用）
     */
    @ApiModelProperty(value = "状态（0正常 1删除 2停用）")
    private String status;
    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者")
    private String createBy;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date createDate;
    /**
     * 更新者
     */
    @ApiModelProperty(value = "更新者")
    private String updateBy;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date updateDate;
    /**
     * 备注信息
     */
    @ApiModelProperty(value = "备注信息")
    private String remarks;
    /**
     * 租户代码
     */
    @ApiModelProperty(value = "租户代码")
    private String corpCode;
    /**
     * 租户名称
     */
    @ApiModelProperty(value = "租户名称")
    private String corpName;
    /**
     * 扩展 String 1
     */
    @ApiModelProperty(value = "扩展 String 1")
    @JsonIgnore
    private String extendS1;
    /**
     * 扩展 String 2
     */
    @ApiModelProperty(value = "扩展 String 2")
    @JsonIgnore
    private String extendS2;
    /**
     * 扩展 String 3
     */
    @ApiModelProperty(value = "扩展 String 3")
    @JsonIgnore
    private String extendS3;
    /**
     * 扩展 String 4
     */
    @ApiModelProperty(value = "扩展 String 4")
    @JsonIgnore
    private String extendS4;
    /**
     * 扩展 String 5
     */
    @ApiModelProperty(value = "扩展 String 5")
    @JsonIgnore
    private String extendS5;
    /**
     * 扩展 String 6
     */
    @ApiModelProperty(value = "扩展 String 6")
    @JsonIgnore
    private String extendS6;
    /**
     * 扩展 String 7
     */
    @ApiModelProperty(value = "扩展 String 7")
    @JsonIgnore
    private String extendS7;
    /**
     * 扩展 String 8
     */
    @ApiModelProperty(value = "扩展 String 8")
    @JsonIgnore
    private String extendS8;
    /**
     * 扩展 Integer 1
     */
    @ApiModelProperty(value = "扩展 Integer 1")
    @JsonIgnore
    private BigDecimal extendI1;
    /**
     * 扩展 Integer 2
     */
    @ApiModelProperty(value = "扩展 Integer 2")
    @JsonIgnore
    private BigDecimal extendI2;
    /**
     * 扩展 Integer 3
     */
    @ApiModelProperty(value = "扩展 Integer 3")
    @JsonIgnore
    private BigDecimal extendI3;
    /**
     * 扩展 Integer 4
     */
    @ApiModelProperty(value = "扩展 Integer 4")
    @JsonIgnore
    private BigDecimal extendI4;
    /**
     * 扩展 Float 1
     */
    @ApiModelProperty(value = "扩展 Float 1")
    @JsonIgnore
    private BigDecimal extendF1;
    /**
     * 扩展 Float 2
     */
    @ApiModelProperty(value = "扩展 Float 2")
    @JsonIgnore
    private BigDecimal extendF2;
    /**
     * 扩展 Float 3
     */
    @ApiModelProperty(value = "扩展 Float 3")
    @JsonIgnore
    private BigDecimal extendF3;
    /**
     * 扩展 Float 4
     */
    @ApiModelProperty(value = "扩展 Float 4")
    @JsonIgnore
    private BigDecimal extendF4;
    /**
     * 扩展 Date 1
     */
    @ApiModelProperty(value = "扩展 Date 1")
    @JsonIgnore
    private Date extendD1;
    /**
     * 扩展 Date 2
     */
    @ApiModelProperty(value = "扩展 Date 2")
    @JsonIgnore
    private Date extendD2;
    /**
     * 扩展 Date 3
     */
    @ApiModelProperty(value = "扩展 Date 3")
    @JsonIgnore
    private Date extendD3;
    /**
     * 扩展 Date 4
     */
    @ApiModelProperty(value = "扩展 Date 4")
    @JsonIgnore
    private Date extendD4;


}
