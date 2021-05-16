package cn.central.common.ribbon.config;

import cn.central.common.ribbon.rule.VersionIsolationRule;
import com.alibaba.cloud.nacos.ribbon.NacosServer;

import com.netflix.loadbalancer.IRule;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @date 2020/4/24
 */
public class RuleConfigure {
    @Bean
    @ConditionalOnClass(NacosServer.class)
    @ConditionalOnMissingBean
    public IRule versionIsolationRule() {
        return new VersionIsolationRule();
    }
}
