package cn.central.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.central.entity.SysLogger;
import cn.central.dao.SysLoggerMapper;
import cn.central.service.SysLoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 
 * </p>
 *
 * @package: cn.siques.mangosound.service.impl
 * @description: 
 * @author: Shenghao.He
 * @date: Created in 2020-11-18 22:12:02
 * @copyright: Copyright (c) 2020
 * @version: V1.0
 * @modified: Shenghao.He
 */
@Service
public class SysLoggerServiceImpl extends ServiceImpl<SysLoggerMapper, SysLogger> implements SysLoggerService {

    @Autowired
    SysLoggerMapper sysLoggerMapper;

}
