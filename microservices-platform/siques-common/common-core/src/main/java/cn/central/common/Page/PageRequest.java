package cn.central.common.Page;


import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 分页请求
 * @author he
 */
@Data
public class PageRequest<T>  implements Serializable {
    /*
    当前页码
     */
    private int pageNum=1;

    /*
    每页数量
     */
    private int pageSize=10;

    private T params;

}
