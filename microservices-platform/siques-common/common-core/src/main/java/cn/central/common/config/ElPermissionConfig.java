package cn.central.common.config;

import cn.central.common.constant.AdminConstants;
import cn.central.common.feign.UserService;
import cn.central.common.utils.SecurityUtils;
import cn.central.log.monitor.PointUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.Arrays;
import java.util.Set;

/**
 *  自定义url级别el表达式鉴权
 * @author : heshenghao
 * @date : 22:16 2020/11/19
 */

@Slf4j
public class ElPermissionConfig {

    @Autowired
    @Lazy
    UserService userService;

    /**
     * 发起远程调用鉴权 如果是admin直接返回true
     * @param permission
     * @return
     */
    public Boolean check(String ...permission){
        String userCode = SecurityUtils.getUsername();
        /**
         * 唯一用户代码核对
         */
        boolean hasPermit = userCode.equals(AdminConstants.ADMIN) || userService.findPermissionsByUserCode(userCode).contains(permission);

        PointUtil.info("用户代码"+userCode, "el表达式鉴权", "permission={"+ Arrays.toString(permission) +"}"+"&status="+hasPermit);

        return hasPermit;
    }
}
