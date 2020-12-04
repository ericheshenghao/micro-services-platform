package cn.central.controller.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Administrator
 */
@Data
@AllArgsConstructor
public class UserRoleDto {
    private long userId;
    private long roleId;
}
