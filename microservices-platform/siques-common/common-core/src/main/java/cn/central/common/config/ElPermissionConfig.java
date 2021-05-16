package cn.central.common.config;

import cn.central.common.constant.AdminConstants;
import cn.central.common.feign.AuthService;
import cn.central.common.utils.SecurityUtils;
import cn.central.log.annotation.AuditLog;
import cn.central.log.monitor.PointUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.Arrays;

/**
 *  自定义url级别el表达式鉴权
 * @author : heshenghao
 * @date : 22:16 2020/11/19
 */

@Slf4j
public class ElPermissionConfig {

    @Autowired
    @Lazy
    AuthService authService;




    /**
     * 发起远程调用鉴权
     * @param permission
     * @return
     */
    @AuditLog(operation = "")
    public Boolean check(String ...permission){
        String userCode = SecurityUtils.getUserCode();
        /**
         * 判断是否有权限，判断userCode是否为管理员元，查询权限
         */
        boolean hasPermit = userCode.equals(AdminConstants.ADMIN) || authService.findPermissionsByUserCode(userCode).contains(permission);
        // 埋点日志
        PointUtil.info("用户:"+userCode, "el表达式鉴权", "permission={"+ Arrays.toString(permission) +"}"+
                "&status="+( hasPermit==true?"鉴权成功":"鉴权失败"));

        return hasPermit;
    }
}
