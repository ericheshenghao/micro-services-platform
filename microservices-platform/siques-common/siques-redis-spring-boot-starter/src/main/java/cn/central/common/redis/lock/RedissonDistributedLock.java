package cn.central.common.redis.lock;


import cn.central.common.constant.CommonConstant;
import cn.central.common.exception.LockException;
import cn.central.common.lock.DistributedLock;
import cn.central.common.lock.HLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.util.concurrent.TimeUnit;

/**
 * redisson分布式锁实现，基本锁功能的抽象实现
 * 本接口能满足绝大部分的需求，高级的锁功能，请自行扩展或直接使用原生api
 * https://gitbook.cn/gitchat/activity/5f02746f34b17609e14c7d5a
 *
 * @date 2020/5/5
 * <p>

 */
@ConditionalOnClass(RedissonClient.class)
@ConditionalOnProperty(prefix = "siques.lock", name = "lockerType", havingValue = "REDIS", matchIfMissing = true)
public class RedissonDistributedLock implements DistributedLock {
    @Autowired
    private RedissonClient redisson;

    private HLock getLock(String key, boolean isFair) {
        RLock lock;
        if (isFair) {
            lock = redisson.getFairLock(CommonConstant.LOCK_KEY_PREFIX + key);
        } else {
            lock =  redisson.getLock(CommonConstant.LOCK_KEY_PREFIX + key);
        }
        return new HLock(lock, this);
    }

    @Override
    public HLock lock(String key, long leaseTime, TimeUnit unit, boolean isFair) {
        HLock hlock = getLock(key, isFair);
        RLock lock = (RLock)hlock.getLock();
        lock.lock(leaseTime, unit);
        return hlock;
    }

    @Override
    public HLock tryLock(String key, long waitTime, long leaseTime, TimeUnit unit, boolean isFair) throws InterruptedException {
        HLock hlock = getLock(key, isFair);
        RLock lock = (RLock)hlock.getLock();
        if (lock.tryLock(waitTime, leaseTime, unit)) {
            return hlock;
        }
        return null;
    }

    @Override
    public void unlock(Object lock) {
        if (lock != null) {
            if (lock instanceof RLock) {
                RLock rLock = (RLock)lock;
                if (rLock.isLocked()) {
                    rLock.unlock();
                }
            } else {
                throw new LockException("requires RLock type");
            }
        }
    }
}
