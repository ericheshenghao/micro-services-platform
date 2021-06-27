package cn.central.service;

import cn.central.common.page.PageRequest;
import cn.central.common.page.PageResult;
import cn.central.controller.dto.DbConfig;
import cn.central.entity.gen.GenConfig;

import java.util.List;

/**
 * @author he
 */
public interface SysCodeGenService {
    /**
     * 生成代码
     *
     * @param genConfig
     * @return
     */
    byte[] generatorCode(GenConfig genConfig);

    /**
     * 分页
     *
     * @param pageRequest
     * @return
     */
    PageResult findPage(PageRequest pageRequest);


    /**
     * 数据库列表
     *
     * @return
     */
    List<DbConfig> queryDbList();

}
