package cn.central.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;
import org.apache.ibatis.annotations.Select;
import java.util.List;
import cn.central.entity.SysLogger;

/**
 * <p>
 * 
 * </p>
 *
 * @package:  cn.siques.mangosound.mapper
 * @description: 
 * @author: Shenghao.He
 * @date: Created in 2020-11-18 22:12:02
 * @copyright: Copyright (c) 2020
 * @version: V1.0
 * @modified: Shenghao.He
 */
@Component
public interface SysLoggerMapper extends BaseMapper<SysLogger> {


}
