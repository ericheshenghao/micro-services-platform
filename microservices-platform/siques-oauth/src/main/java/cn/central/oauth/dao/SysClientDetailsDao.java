package cn.central.oauth.dao;


import cn.central.oauth.entity.SysClientDetails;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author : heshenghao
 * @date : 20:09 2020/11/13
 */
@Repository
public interface SysClientDetailsDao extends BaseMapper<SysClientDetails> {

    /**
     * 通过 clientId 查找客户端信息.
     *
     * @param clientId clientId
     * @return 结果
     */

    @Select({"select * from sys_client_details where client_id = #{clientId} limit 1 "})
    Optional<SysClientDetails> findFirstByClientId(String clientId);


    @Select("select * from sys_client_details")
    List<SysClientDetails> findAll();
}
