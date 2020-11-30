package cn.central.service.impl;


import cn.central.dao.SysOfficeMapper;
import cn.central.entity.SysOffice;
import cn.central.service.SysOfficeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class SysOfficeServiceImpl extends ServiceImpl<SysOfficeMapper, SysOffice> implements SysOfficeService {
    @Resource
    SysOfficeMapper sysDeptMapper;



    @Override
    public List<SysOffice> findOfficeTree() {
        List<SysOffice> sysOffices = new ArrayList<>();
        // 查出所有的机构进行排序
        List<SysOffice> offices= sysDeptMapper.selectList(new QueryWrapper<>());

        for (SysOffice office:offices
             ) {
            if(office.getParentId()==null|| office.getParentId()==0){
                List<Long> treeDept= new ArrayList<>();
                   office.setLevel(0);
                   treeDept.add(office.getId());
                   office.setOfficeList(treeDept);
                sysOffices.add(office);
            }
        }

        findChildren(sysOffices, offices);
        return sysOffices;
    }

    @Override
    public IPage<SysOffice> findOfficePageById(Page<SysOffice> officePage, Long id) {
        return sysDeptMapper.findOfficePageById(officePage,id);
    }


    // TODO 逻辑
    private void findChildren(List<SysOffice> sysOffices, List<SysOffice> offices) {
        for (SysOffice sysOffice : sysOffices) {
            List<SysOffice> children = new ArrayList<>();
            // 最父节点的树


            for (SysOffice office : offices) {
                // 遍历所有节点，若该节点的父节点与最外层节点相同，则说明是子节点
                List<Long> tree = new ArrayList<>();
                if (sysOffice.getId() != null && sysOffice.getId().equals(office.getParentId())) {
//                    // 父节点的机构树
                    List<Long> deptList = sysOffice.getOfficeList();
                    tree.addAll(deptList);
//                    // 添加子节点id
                    tree.add(office.getId());
//                    // 将树设置到子节点
                   office.setOfficeList(tree);
                    office.setParentName(office.getOfficeName());
                    office.setLevel(sysOffice.getLevel() + 1);
                    children.add(office);
                }
            }
            if(children.size()!=0){

                sysOffice.setChildren(children);
            }
            findChildren(children, offices);
        }
    }

}
