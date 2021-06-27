package cn.central.common.page;


import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 分页请求
 *
 * @author he
 */
@Data
public class PageRequest<T> implements Serializable {
    /**
     * 当前页码
     */
    private int pageNum = 1;

    /**
     * 每页数量
     */
    private int pageSize = 10;

    /**
     * {@link cn.central.common.model.SysUser}
     */
    @NotNull(message = "查询参数不能为空")
    private T params;

}
