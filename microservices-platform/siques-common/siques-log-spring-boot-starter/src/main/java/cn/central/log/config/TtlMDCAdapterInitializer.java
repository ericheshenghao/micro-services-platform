package cn.central.log.config;


import org.sl4j.TtlMDCAdapter;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 初始化TtlMDCAdapter实例，并替换MDC中的adapter对象
 * @author he
 * @date 2019/8/17
 */
public class TtlMDCAdapterInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        //加载TtlMDCAdapter实例
        TtlMDCAdapter.getInstance();
    }
}