package cn.central.oauth.service.impl;

import cn.central.common.constant.SecurityConstants;


import cn.central.common.model.SysUser;
import cn.central.common.redis.template.RedisRepository;
import cn.central.oauth.exception.ValidateCodeException;
import cn.central.oauth.service.SysUserService;
import cn.central.oauth.service.ValidateCodeService;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;

/**
 * @author : heshenghao
 * @date : 20:36 2020/12/4
 */
@Service
@Slf4j
public class ValidateCodeServiceImpl implements ValidateCodeService {

    @Autowired
    private RedisRepository redisRepository;

    @Autowired
    private SysUserService sysUserService;

    /**
     * 保存用户验证码，和randomStr绑定
     *
     * @param deviceId 客户端生成
     * @param imageCode 验证码信息
     */
    @Override
    public void saveImageCode(String deviceId, String imageCode) {
        redisRepository.setExpire(buildKey(deviceId),imageCode,SecurityConstants.DEFAULT_IMAGE_EXPIRE);
    }

    @Override
    public void validate(String username, String deviceId, String validCode) {
        if(StringUtils.isBlank(deviceId)){
            throw new ValidateCodeException("请携带deviceId参数");
        }
        String code = this.getCode(deviceId);
        if (org.apache.commons.lang.StringUtils.isBlank(validCode)) {
            throw new ValidateCodeException("请填写验证码");
        }

        if (code == null) {
            throw new ValidateCodeException("验证码不存在或已过期");
        }

        if (!org.apache.commons.lang.StringUtils.equals(code, validCode.toLowerCase())) {
            throw new ValidateCodeException("验证码不正确");
        }

        SysUser user = sysUserService.getOne(new QueryWrapper<SysUser>().eq("user_name", username));
        if(ObjectUtil.isNull(user) ||user.getStatus()==false){
            throw  new ValidateCodeException("账号不存在或被冻结");
        }
        this.remove(deviceId);
    }

    /**
     * 获取验证码
     * @param deviceId 前端唯一标识/手机号
     */
    public String getCode(String deviceId) {
        return (String)redisRepository.get(buildKey(deviceId));
    }


    /**
     * 删除验证码
     * @param deviceId 前端唯一标识/手机号
     */
    public void remove(String deviceId) {
        redisRepository.del(buildKey(deviceId));
    }


    private String buildKey(String deviceId) {
        return SecurityConstants.DEFAULT_CODE_KEY + ":" + deviceId;
    }
}
