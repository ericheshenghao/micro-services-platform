package cn.central.es.config;

import cn.central.es.properties.RestClientPoolProperties;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.boot.autoconfigure.elasticsearch.rest.RestClientBuilderCustomizer;
import org.springframework.boot.autoconfigure.elasticsearch.rest.RestClientProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.context.annotation.Bean;

/**
 * @author : heshenghao
 * @date : 9:40 2020/11/19
 */
@EnableConfigurationProperties(RestClientPoolProperties.class)
public class RestAutoCnfigure {
    @Bean
    public RestClientBuilderCustomizer restClientBuilderCustomizer(
            RestClientPoolProperties poolProperties,
            RestClientProperties restClientProperties
    ){
            return (builder -> {
                setRequestConfig(builder, poolProperties);

                setHttpClientConfig(builder,poolProperties,restClientProperties);
            });
    }

    /**
     * 异步httpclient连接数配置
     */
    private void setHttpClientConfig(RestClientBuilder builder, RestClientPoolProperties poolProperties, RestClientProperties restClientProperties) {
        builder.setHttpClientConfigCallback(httpAsyncClientBuilder -> {
            httpAsyncClientBuilder.setMaxConnPerRoute(poolProperties.getMaxConnectPerRoute())
                    .setMaxConnPerRoute(poolProperties.getMaxConnectPerRoute());
            PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
            map.from(restClientProperties::getUsername).to(username->{
                CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
                credentialsProvider.setCredentials(AuthScope.ANY,
                        new UsernamePasswordCredentials(username, restClientProperties.getPassword()));
            httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
            });
        return httpAsyncClientBuilder;
        });
    }

    /**
     * 异步httpclient连接延时配置
     */
    private void setRequestConfig(RestClientBuilder builder, RestClientPoolProperties poolProperties) {
        builder.setRequestConfigCallback(requestConfigBuilder ->{
           requestConfigBuilder.setConnectTimeout(poolProperties.getConnectTimeOut())
           .setSocketTimeout(poolProperties.getSocketTimeOut())
           .setConnectionRequestTimeout(poolProperties.getConnectionRequestTimeOut());
           return requestConfigBuilder;
        });
    }

}
