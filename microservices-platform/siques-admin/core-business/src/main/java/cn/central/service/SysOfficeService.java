package cn.central.service;



import cn.central.entity.SysOffice;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface SysOfficeService extends IService<SysOffice> {

    List<SysOffice> findOfficeTree();

    IPage<SysOffice> findOfficePageById(Page<SysOffice> deptPage, Long id);
}
