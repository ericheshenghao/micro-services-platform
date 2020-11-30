package cn.central.controller.dto;


import lombok.Data;

/**
 * 切换数据库
 */
@Data
public class DbConfig {
    public  String  url ;
    public   String username;
    public  String password;
}
