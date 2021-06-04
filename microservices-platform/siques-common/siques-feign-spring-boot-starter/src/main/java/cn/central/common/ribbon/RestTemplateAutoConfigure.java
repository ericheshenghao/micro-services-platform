package cn.central.common.ribbon;


import cn.central.common.ribbon.config.RestTemplateProperties;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import feign.Request.Options;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

/**
 * @author he
 * @date 2018/11/17
 */
@EnableConfigurationProperties(RestTemplateProperties.class)
public class RestTemplateAutoConfigure {
    @Autowired
    private RestTemplateProperties restTemplateProperties;


    @Bean
    public Options options(){
        return new Options(30, TimeUnit.SECONDS,30,TimeUnit.SECONDS,true);
    }

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(httpRequestFactory());
        return restTemplate;
    }

    /**
     * httpclient 实现的ClientHttpRequestFactory
     */
    @Bean
    public ClientHttpRequestFactory httpRequestFactory() {
        return new HttpComponentsClientHttpRequestFactory(httpClient());
    }

    /**
     * 使用连接池的 httpclient
     */
    @Bean
    public HttpClient httpClient() {
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", SSLConnectionSocketFactory.getSocketFactory())
                .build();
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
        // 最大链接数
        connectionManager.setMaxTotal(restTemplateProperties.getMaxTotal());
        // 同路由并发数20
        connectionManager.setDefaultMaxPerRoute(restTemplateProperties.getMaxPerRoute());


        RequestConfig requestConfig = RequestConfig.custom()
                // 读超时
                .setSocketTimeout(restTemplateProperties.getReadTimeout())
                // 链接超时
                .setConnectTimeout(restTemplateProperties.getConnectTimeout())
                // 链接不够用的等待时间
                .setConnectionRequestTimeout(restTemplateProperties.getReadTimeout())
                .build();



        return HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(connectionManager)
                .setRetryHandler(new DefaultHttpRequestRetryHandler(3, true))
                .build();
    }
}
