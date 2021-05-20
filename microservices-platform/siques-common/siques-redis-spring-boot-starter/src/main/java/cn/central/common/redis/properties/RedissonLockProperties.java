package cn.central.common.redis.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * @author : heshenghao
 * @date : 11:43 2021/5/15
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "siques.lock")
public class RedissonLockProperties {
    private LockerType lockerType = LockerType.REDIS;
    public enum LockerType {
        REDIS,
    }
}
