package cn.central.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * <p>
 * 字典类型表
 * </p>
 *
 * @package:  cn.siques.mangosound.entity
 * @description: 字典类型表
 * @author: Shenghao.He
 * @date: Created in 2020-09-03 11:22:09
 * @copyright: Copyright (c) 2020
 * @version: V1.0
 * @modified: Shenghao.He
 */
@Data
@TableName("sys_dict_type")
@ApiModel(description = "字典类型表")
@EqualsAndHashCode(callSuper = true)
public class SysDictType extends Model<SysDictType> {
    private static final long serialVersionUID = 1L;

    /**
     * 编号 雪花算法
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "编号")
    private String id;
    /**
     * 字典名称
     */
    @ApiModelProperty(value = "字典名称")
    private String dictName;
    /**
     * 字典类型
     */
    @ApiModelProperty(value = "字典类型")
    private String dictType;
    /**
     * 是否系统字典
     */
    @ApiModelProperty(value = "是否系统字典")
    private String isSys;
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

}
