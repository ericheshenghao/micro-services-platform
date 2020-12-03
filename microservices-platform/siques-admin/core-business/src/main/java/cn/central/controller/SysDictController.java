package cn.central.controller;


import cn.central.common.Page.PageRequest;
import cn.central.common.model.Result;
import cn.central.entity.SysDict;
import cn.central.service.SysDictService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pri/dict")
@Api(tags = {"字典接口"})
public class SysDictController {
    @Autowired
    private SysDictService sysDictService;

    @PreAuthorize("@el.check('sys:dict:add') AND @el.check('sys:dict:edit')")
    @PostMapping("save")
    public Result save(@RequestBody SysDict sysDict){
        return Result.succeed(sysDictService.save(sysDict));
    }

    /**
     * 列表删除
     * @param records
     * @return
     */
    @PreAuthorize("@el.check('sys:dict:delete')")
    @PostMapping("delete")
    public Result delete(@RequestBody List<SysDict> records){
        return Result.succeed(sysDictService.removeByIds(records));
    }

    @PreAuthorize("@el.check('sys:dict:view')")
    @PostMapping("findPage")
    public Result findPage(@RequestBody PageRequest page){
        Page<SysDict> detailsPage = new Page<>(page.getPageNum(), page.getPageSize());
        return Result.succeed(sysDictService.page(detailsPage));

    }

    @PreAuthorize("@el.check('sys:dict:view')")
    @GetMapping("findByLabel")
    public Result findByLabel(@RequestParam String label){
        return Result.succeed(sysDictService.list(new QueryWrapper<SysDict>().eq("label",label)));
    }
}
