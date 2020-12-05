package cn.central.oauth.service;

/**
 * @author : heshenghao
 * @date : 20:35 2020/12/4
 */
public interface ValidateCodeService {

    /**
     * 保存图形验证码
     * @param deviceId 前端唯一标识
     * @param imageCode 验证码
     */
    void saveImageCode(String deviceId, String imageCode);

    /**
     * 验证验证码
     * @param username
     * @param deviceId
     * @param validCode
     */
    void validate(String username, String deviceId, String validCode);
}
