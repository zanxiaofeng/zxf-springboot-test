package zxf.springboot.pa.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.net.URI;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class PAClient {
    @Value("${pa-service.url}")
    private String baseUrl;

    @Value("${pa-service.connectTimeout}")
    private Integer connectTimeout;

    @Value("${pa-service.readTimeout}")
    private Integer readTimeout;

    public Map<String, Object> callDownstreamSyncByGet(String path, Boolean exception) {
        try {
            log.info("::callDownstreamSync START, path={}", path);
            Map<String, Object> result = createRestTemplate(exception).getForObject(URI.create(baseUrl + path), Map.class);
            log.info("::callDownstreamSync END, path={}, result={}", path, result);
            return result;
        } catch (Throwable ex) {
            log.error("Exception when call downstream api.", ex);
            throw ex;
        }
    }

    public Map<String, Object> callDownstreamSyncByPost(String path, Object body, Boolean exception) {
        try {
            log.info("::callDownstreamSync START, path={}, body={}", path, body);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            RequestEntity<Object> requestEntity = new RequestEntity<>(body, headers, HttpMethod.POST, URI.create(baseUrl + path));
            Map<String, Object> result = createRestTemplate(exception).exchange(requestEntity, Map.class).getBody();
            log.info("::callDownstreamSync END, path={}, body={}, result={}", path, body, result);
            return result;
        } catch (Throwable ex) {
            log.error("Exception when call downstream api.", ex);
            throw ex;
        }
    }

    private RestTemplate createRestTemplate(Boolean exception) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(connectTimeout);
        requestFactory.setReadTimeout(readTimeout);
        RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(requestFactory));
        if (!exception) {
            restTemplate.setErrorHandler(new MyResponseErrorHandler());
        }
        restTemplate.setInterceptors(List.of(new LoggingRequestInterceptor()));
        return restTemplate;
    }
}
