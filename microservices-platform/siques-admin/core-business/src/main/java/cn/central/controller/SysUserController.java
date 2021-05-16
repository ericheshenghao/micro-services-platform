package cn.central.controller;


import cn.central.common.annotation.LoginUser;
import cn.central.common.page.PageRequest;
import cn.central.common.constant.AdminConstants;
import cn.central.common.model.Result;
import cn.central.common.model.SysUser;

import cn.central.search.model.LogicDelDto;
import cn.central.search.model.SearchDto;
import cn.central.search.service.IQueryService;
import cn.central.service.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @author Shenghao.He
 */
@RestController
@RequestMapping("/pri/user")
@Api(tags = {"用户接口"})
public class SysUserController {


    @Resource
    SysUserService sysUserService;

    @Resource
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private IQueryService queryService;


    @GetMapping("info")
    public Result<SysUser> getUserInfo(@LoginUser(isFull = true) SysUser sysUser){
        sysUser.setPermissions(sysUserService.findPermission(sysUser.getUserCode()));
       return Result.succeed(sysUser);
    }

    @GetMapping("/{userCode}")
    public  SysUser getUserByUserCode(@PathVariable String userCode){
        return sysUserService.getOne(new QueryWrapper<SysUser>().eq("user_code",userCode));
    }

    /**
     * 鉴权的话每次都会向一个服务发起调用获取
     * @param userId
     * @return
     */
    @PreAuthorize("@el.check('sys:user:edit')")
    @PutMapping("/password/{id}")
    @ApiOperation(value = "重置密码", notes = "重置密码")
    @Transactional
    public Result resetPassword(@PathVariable("id") String userId){

        // 提供密码的情况
          return Result.succeed(sysUserService.update(new UpdateWrapper<SysUser>()
                  .eq("id",userId).set("password",passwordEncoder.encode(AdminConstants.PASSWORD))));
    }

    @PreAuthorize("@el.check('sys:user:delete')")
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除用户", notes = "删除用户")
    @Transactional
    public Result delete(@PathVariable("id") String id){
       return Result.succeed(sysUserService.removeById(id));
    }


    @PreAuthorize("@el.check('sys:user:delete')")
    @PostMapping("/deleteBatch")
    @ApiOperation(value = "批量删除用户", notes = "批量删除用户")
    @Transactional
    public Result deleteBatch(@RequestBody Set<String> set){
        return Result.succeed(sysUserService.removeByIds(set));
    }



    @PreAuthorize("@el.check('sys:user:view')")
    @PostMapping("/findPage")
    @ApiOperation(value = "条件搜索或分页", notes = "条件搜索用户")
    public Result findPage(@RequestBody PageRequest<SysUser> pageRequest){
        return sysUserService.findPage(pageRequest);
    }


    @PreAuthorize("@el.check('sys:user:add') AND @el.check('sys:user:edit')")
    @PostMapping()
    @Transactional
    @ApiOperation(value = "保存或更新用户", notes = "保存或更新用户")
    public Result saveOrUpdate(@RequestBody SysUser record){
        // 查询是否已有该用户
        SysUser user = sysUserService.getOne(new QueryWrapper<SysUser>().eq("user_code",record.getUserCode()));
        // 如果没有，直接新增
        if(user==null) {
            return Result.succeed(sysUserService.save(record));
        }
        // 到这里说明数据库有该用户 判断是否为admin用户
        if( AdminConstants.ADMIN.equalsIgnoreCase(user.getUserCode())){
            return Result.failed("不允许修改超级管理员");
        }
        // 角色分配
        sysUserService.saveUserRoles(record);

        // 更新其他字段
        return Result.succeed(sysUserService.update(record,new QueryWrapper<SysUser>().eq("user_code",record.getUserCode())));
    }


    /**
     * 匹配每个需要权限的接口, 鉴权查询接口
     *  {@link cn.central.common.config.ElPermissionConfig}
     *
     * 必须是登录用户才可以远程调用此接口
     * @param userCode
     * @return
     */
    @ApiOperation(httpMethod="GET", value="根据用户code查询权限")
    @GetMapping("permissions/{userCode}")
    @Cacheable(value = "userPermission",key = "#userCode")
    public Set<String> findPermissionsByUserCode(@PathVariable("userCode") String userCode){
        return sysUserService.findPermission(userCode);
    }



    private static final LogicDelDto SEARCH_LOGIC_DEL_DTO = new LogicDelDto("status", "正常");

    @ApiOperation(value = "用户全文搜索列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "分页数量", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "queryStr", value = "搜索关键字", dataType = "String")
    })
    @PostMapping("/search")
    public Result search(@RequestBody SearchDto searchDto) {
        searchDto.setIsHighlighter(true);
        searchDto.setSortCol("createTime");
        return queryService.strQuery("sys_user", searchDto, SEARCH_LOGIC_DEL_DTO);
    }

}
