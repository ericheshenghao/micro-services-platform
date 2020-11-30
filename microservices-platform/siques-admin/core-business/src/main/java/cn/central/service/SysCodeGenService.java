package cn.central.service;

import cn.central.common.Page.PageRequest;
import cn.central.common.Page.PageResult;
import cn.central.controller.dto.DbConfig;
import cn.central.entity.gen.GenConfig;

import java.util.List;

public interface SysCodeGenService  {
   byte[] generatorCode(GenConfig genConfig);

    PageResult findPage(PageRequest pageRequest);


    List<DbConfig> queryDbList();

}
