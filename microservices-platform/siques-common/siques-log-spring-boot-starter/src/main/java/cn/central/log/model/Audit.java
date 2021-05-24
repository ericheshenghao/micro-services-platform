package cn.central.log.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 审计日志
 *
 * @author he
 * @date 2020/2/3
 */
@Setter
@Getter
public class Audit {
    /**
     * 操作时间
     */
    private LocalDateTime timestamp;
    /**
     * 应用名
     */
    private String applicationName;
    /**
     * 类名
     */
    private String className;
    /**
     * 方法名
     */
    private String methodName;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 用户编码
     */
    private String userCode;
    /**
     * 租户id
     */
    private String clientId;
    /**
     * 操作信息
     */
    private String operation;
}
