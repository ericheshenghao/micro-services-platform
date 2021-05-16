package cn.central.account.model;

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
@TableName("account_tbl")
public class Account {
    @TableId
    private Long id;

    private String userId;
    private Integer money;
}
