package zxf.springboot.pa.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;

@Slf4j
@Service
public class PAClient {
    @Value("${pa-service.url}")
    private String baseUrl;

    public Map<String, Object> callDownstreamSync(String path) {
        try {
            log.info("::callDownstreamSync START, path={}", path);
            Map<String, Object> result = new RestTemplate().getForObject(URI.create(baseUrl + path), Map.class);
            log.info("::callDownstreamSync END, path={}, result={}", path, result);
            return result;
        } catch (Throwable ex) {
            log.error("Exception when call downstream api.", ex);
            throw ex;
        }
    }
}
