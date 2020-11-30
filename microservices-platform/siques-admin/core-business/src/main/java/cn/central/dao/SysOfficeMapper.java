package cn.central.dao;


import cn.central.entity.SysOffice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface SysOfficeMapper extends BaseMapper<SysOffice> {


    @Select({"SELECT o.* FROM sys_office o " +
            " WHERE  o.parent_ids LIKE CONCAT('%',#{id,jdbcType=BIGINT},'%') OR o.id=#{id,jdbcType=BIGINT}"})
    IPage<SysOffice> findOfficePageById(IPage page, @Param("id") Long id);

 }