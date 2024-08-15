package zxf.springboot.pa.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

@Configuration
public class ClientHttpRequestFactoryConfiguration {
    @Value("${internal-call.connectTimeout}")
    private Integer internalCallConnectTimeout;

    @Value("${internal-call.readTimeout}")
    private Integer internalCallReadTimeout;

    @Value("${external-call.connectTimeout}")
    private Integer externalCallConnectTimeout;

    @Value("${external-call.readTimeout}")
    private Integer externalCallReadTimeout;

    @Bean("internalCallClientHttpRequestFactory")
    public ClientHttpRequestFactory internalCallClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(internalCallConnectTimeout);
        requestFactory.setReadTimeout(internalCallReadTimeout);
        return requestFactory;
    }

    @Bean("externalCallClientHttpRequestFactory")
    public ClientHttpRequestFactory externalCallClientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(externalCallConnectTimeout);
        requestFactory.setReadTimeout(externalCallReadTimeout);
        return requestFactory;
    }
}