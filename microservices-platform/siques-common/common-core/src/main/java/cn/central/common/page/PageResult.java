package cn.central.common.page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 分页返回结果
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> implements Serializable {
    private static final long serialVersionUID = -275582248840135389L;
    /**
     * 当前页码
     */
    private int pageNum;

    /**
     * 每页数量
     */
    private int pageSize;

    /**
     * 记录总量
     */
    private long total;
    /**
     * 是否成功：0 成功、1 失败
     */
    private int code;
    /**
     * 页码总数
     */
    private  int totalPages;

    /**
     * 分页数据
     */
    private List<T> records;


}
