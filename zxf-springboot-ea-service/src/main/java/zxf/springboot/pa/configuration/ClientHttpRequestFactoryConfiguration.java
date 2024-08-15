package zxf.springboot.pa.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

@Configuration
public class ClientHttpRequestFactoryConfiguration {
    @Value("${http.connectTimeout}")
    private Integer connectTimeout;

    @Value("${http.readTimeout}")
    private Integer readTimeout;

    @Bean
    public ClientHttpRequestFactory createClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(connectTimeout);
        requestFactory.setReadTimeout(readTimeout);
        return requestFactory;
    }
}
