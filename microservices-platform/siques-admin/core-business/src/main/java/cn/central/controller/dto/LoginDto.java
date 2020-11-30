package cn.central.controller.dto;

import lombok.Data;

@Data
public class LoginDto {

    private String loginCode;
    private String password;
    private String captcha;
}
