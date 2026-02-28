package zxf.springboot.pa.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.util.Timeout;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

@Slf4j
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

    public ClientHttpRequestFactoryConfiguration() {
        log.info("::ctor");
    }

    @Bean("internalCallClientHttpRequestFactory")
    public ClientHttpRequestFactory internalCallClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(internalCallConnectTimeout);
        requestFactory.setReadTimeout(internalCallReadTimeout);
        return requestFactory;
    }

    @Bean("externalCallClientHttpRequestFactory")
    public ClientHttpRequestFactory externalCallClientHttpRequestFactory() {
        CloseableHttpClient httpClient = HttpClients.custom()
            .setDefaultRequestConfig(RequestConfig.custom()
                .setConnectTimeout(Timeout.ofMilliseconds(externalCallConnectTimeout))
                .setResponseTimeout(Timeout.ofMilliseconds(externalCallReadTimeout))
                .build())
            .build();
        return new HttpComponentsClientHttpRequestFactory(httpClient);
    }
}
