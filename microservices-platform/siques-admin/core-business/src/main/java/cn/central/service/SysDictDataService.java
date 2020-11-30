package cn.central.service;



import cn.central.common.Page.PageRequest;
import cn.central.common.Page.PageResult;
import cn.central.entity.SysDictData;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 字典数据表
 * </p>
 *
 * @package:  cn.siques.mangosound.service
 * @description: 字典数据表
 * @author: Shenghao.He
 * @date: Created in 2020-09-03 11:29:19
 * @copyright: Copyright (c) 2020
 * @version: V1.0
 * @modified: Shenghao.He
 */
public interface SysDictDataService extends IService<SysDictData> {


    List<SysDictData> listKeyByType(String dictType);
}
