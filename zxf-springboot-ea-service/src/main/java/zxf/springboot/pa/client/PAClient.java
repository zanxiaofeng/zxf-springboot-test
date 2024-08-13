package zxf.springboot.pa.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PAClient {
    @Value("${pa-service.url}")
    private String baseUrl;

    @Value("${pa-service.connectTimeout}")
    private Integer connectTimeout;

    @Value("${pa-service.readTimeout}")
    private Integer readTimeout;

    public Map<String, Object> callDownstreamSync(String path, Boolean exception) {
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

    private RestTemplate createRestTemplate(Boolean exception) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(connectTimeout);
        requestFactory.setReadTimeout(readTimeout);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        if (!exception) {
            restTemplate.setErrorHandler(new ResponseErrorHandler() {
                @Override
                public boolean hasError(ClientHttpResponse response) throws IOException {
                    return false;
                }

                @Override
                public void handleError(ClientHttpResponse response) throws IOException {

                }
            });
        }
        restTemplate.setInterceptors(List.of((request, body, execution) -> {
            log.info("Request body: {}", new String(body, StandardCharsets.UTF_8));
            ClientHttpResponse response = execution.execute(request, body);
//            InputStreamReader isr = new InputStreamReader(response.getBody(), StandardCharsets.UTF_8);
//            String responseBody = new BufferedReader(isr).lines().collect(Collectors.joining("\n"));
//            log.info("Response body: {}", responseBody);
            return response;
        }));
        return restTemplate;
    }
}
