package cn.central.storage.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author : heshenghao
 * @date : 19:02 2021/5/15
 */
@Data
@Accessors(chain = true)
@TableName("storage_tbl")
public class Storage {
    @TableId
    private Long id;
    private String commodityCode;
    private Long count;
}
