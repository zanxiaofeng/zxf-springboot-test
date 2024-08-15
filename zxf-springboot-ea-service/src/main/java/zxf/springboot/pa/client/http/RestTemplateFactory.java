package zxf.springboot.pa.client.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplateFactory {
    @Autowired
    private ClientHttpRequestFactory clientHttpRequestFactory;

    public RestTemplate basicRestTemplate(Boolean exceptionable) {
        RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(clientHttpRequestFactory));
        if (!exceptionable) {
            restTemplate.setErrorHandler(new MyResponseErrorHandler());
        }
        restTemplate.getInterceptors().add(new LoggingRequestInterceptor());
        return restTemplate;
    }

    public RestTemplate restTemplateWithBasicAuth(Boolean exceptionable, String username, String passwd) {
        RestTemplate basicRestTemplate = basicRestTemplate(exceptionable);
        basicRestTemplate.getInterceptors().add(0, new BasicAuthenticationInterceptor(username, passwd));
        return basicRestTemplate;
    }

    public RestTemplate restTemplateWithTokenAuth(Boolean exceptionable, String token) {
        RestTemplate basicRestTemplate = basicRestTemplate(exceptionable);
        basicRestTemplate.getInterceptors().add(0, new SetHeaderRequestInterceptor("X-Token", token));
        return basicRestTemplate;
    }
}
