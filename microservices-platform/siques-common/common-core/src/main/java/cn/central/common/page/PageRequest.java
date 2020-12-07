package cn.central.common.page;


import lombok.Data;

import java.io.Serializable;

/**
 * 分页请求
 * @author he
 */
@Data
public class PageRequest<T>  implements Serializable {
    /**
    当前页码
     */
    private int pageNum=1;

    /**
    每页数量
     */
    private int pageSize=10;

    /**
     * {@link cn.central.common.model.SysUser}
     */
    private T params;

}
