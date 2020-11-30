package cn.central.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @package:  cn.siques.mangosound.entity
 * @description: 
 * @author: Shenghao.He
 * @date: Created in 2020-11-18 22:12:02
 * @copyright: Copyright (c) 2020
 * @version: V1.0
 * @modified: Shenghao.He
 */
@Data
@TableName("sys_logger")
@ApiModel(description = "")
@EqualsAndHashCode(callSuper = true)
public class SysLogger extends Model<SysLogger> {
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId
    @ApiModelProperty(value = "")
    private Integer id;
    /**
     * 应用名
     */
    @ApiModelProperty(value = "应用名")
    private String applicationName;
    /**
     * 类名
     */
    @ApiModelProperty(value = "类名")
    private String className;
    /**
     * 方法名
     */
    @ApiModelProperty(value = "方法名")
    private String methodName;
    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Integer userId;
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String userName;
    /**
     * 租户id
     */
    @ApiModelProperty(value = "租户id")
    private String clientId;
    /**
     * 操作信息
     */
    @ApiModelProperty(value = "操作信息")
    private String operation;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private String timestamp;

}
