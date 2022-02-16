package cn.central.common.config;

import cn.central.common.constant.AdminConstants;
import cn.central.common.feign.UserInfoService;
import cn.central.common.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Set;

/**
 * 自定义url级别el表达式鉴权
 *
 * @author : heshenghao
 * @date : 22:16 2020/11/19
 */

@Slf4j
public class ElPermissionConfig {

    @Autowired
    UserInfoService authService;

    /**
     * 发起远程调用鉴权
     *
     * @param p
     * @return
     */

    public Boolean check(String... p) {
        String userCode = SecurityUtils.getUserCode();
        /**
         * 判断是否有权限，判断userCode是否为管理员，查询权限
         */
        if(userCode.equals(AdminConstants.ADMIN)){
            return true;
        }

        Set<String> pt = authService.findPermissionsByUserCode(userCode);

        return  CollectionUtils.containsAny(pt, Arrays.asList(p));
    }
}
