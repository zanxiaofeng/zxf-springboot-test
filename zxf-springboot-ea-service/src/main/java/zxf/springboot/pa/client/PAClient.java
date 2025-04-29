package zxf.springboot.pa.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import zxf.springboot.pa.client.http.RestTemplateFactory;

import java.net.URI;
import java.util.Map;

@Slf4j
@Service
public class PAClient {
    @Value("${pa-service.url}")
    private String baseUrl;

    @Autowired
    @Qualifier("internalCallClientHttpRequestFactory")
    private ClientHttpRequestFactory internalCallClientHttpRequestFactory;

    public PAClient() {
        log.info("::ctor");
    }

    public Map<String, Object> callDownstreamSyncByGet(String path, Boolean exception) {
        try {
            log.info("::callDownstreamSync START, path={}", path);
            Map<String, Object> result = new RestTemplateFactory(internalCallClientHttpRequestFactory)
                    .basicRestTemplate(exception).getForObject(URI.create(baseUrl + path), Map.class);
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
            Map<String, Object> result = new RestTemplateFactory(internalCallClientHttpRequestFactory)
                    .basicRestTemplate(exception).exchange(requestEntity, Map.class).getBody();
            log.info("::callDownstreamSync END, path={}, body={}, result={}", path, body, result);
            return result;
        } catch (Throwable ex) {
            log.error("Exception when call downstream api.", ex);
            throw ex;
        }
    }
}
