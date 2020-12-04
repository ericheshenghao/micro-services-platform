package cn.central.oauth.controller.dto;

import lombok.Data;

/**
 * @author heshenghao
 */
@Data
public class LoginDto {

    private String loginCode;
    private String password;
    private String captcha;
}
