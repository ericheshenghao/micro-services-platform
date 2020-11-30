package cn.central.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;


@Data
@AllArgsConstructor
@TableName("sys_config")
@ApiModel(description = "系统配置表")
public class SysConfig  extends Model<SysConfig> {

    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "编号")
    private Long id;


    private String value;


    private String label;


    private String type;


    private String description;


    private Long sort;


    private String createBy;


    private Date createTime;


    private String lastUpdateBy;


    private Date lastUpdateTime;


    private Byte delFlag;

    private byte[] remarks;

}