package cn.central.common.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体父类
 *
 * @author zlt
 */
@Setter
@Getter
public class SuperEntity<T extends Model<?>> extends Model<T> implements Serializable {
    /**
     * 主键ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "编号")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    private String createBy;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    private String lastUpdateBy;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date lastUpdateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
