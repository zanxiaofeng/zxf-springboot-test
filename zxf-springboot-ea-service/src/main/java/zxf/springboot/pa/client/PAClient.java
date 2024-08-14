package zxf.springboot.pa.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.*;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
        restTemplate.setInterceptors(List.of(new LoggingRequestInterceptor()));
        return restTemplate;
    }

    final static class LoggingRequestInterceptor implements ClientHttpRequestInterceptor {
        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
            logRequest(request, body);
            //BufferingClientHttpResponseWrapper response = new BufferingClientHttpResponseWrapper(execution.execute(request, body));
            ClientHttpResponse response = execution.execute(request, body);
            Assert.isTrue(response.getClass().getName().equals("org.springframework.http.client.BufferingClientHttpResponseWrapper"));
            logResponse(response);
            return response;
        }

        private void logRequest(HttpRequest request, byte[] body) {
            System.out.println("=================================================Request being=================================================");
            System.out.println("URI             : " + request.getURI());
            System.out.println("Methed          : " + request.getMethod());
            System.out.println("Headers         : " + request.getHeaders());
            System.out.println("Request Body    : " + new String(body, StandardCharsets.UTF_8));
            System.out.println("=================================================Request end=================================================");
        }

        private void logResponse(ClientHttpResponse response) throws IOException {
            System.out.println("=================================================Response being=================================================");
            System.out.println("Status code     : " + response.getStatusCode().value());
            System.out.println("Status text     : " + response.getStatusCode().name());
            System.out.println("Headers         : " + response.getHeaders());
            System.out.println("Response Body   : " + toString(response));
            System.out.println("=================================================Response end=================================================");
        }

        private String toString(ClientHttpResponse response) throws IOException {
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody(), StandardCharsets.UTF_8))) {
                String inputStr;
                StringBuilder builder = new StringBuilder();
                while ((inputStr = bufferedReader.readLine()) != null) {
                    builder.append(inputStr);
                }
                return builder.toString();
            }
        }
    }

    final static class BufferingClientHttpResponseWrapper implements ClientHttpResponse {

        private final ClientHttpResponse response;

        @Nullable
        private byte[] body;


        BufferingClientHttpResponseWrapper(ClientHttpResponse response) {
            this.response = response;
        }


        @Override
        public HttpStatus getStatusCode() throws IOException {
            return this.response.getStatusCode();
        }

        @Override
        public int getRawStatusCode() throws IOException {
            return this.response.getRawStatusCode();
        }

        @Override
        public String getStatusText() throws IOException {
            return this.response.getStatusText();
        }

        @Override
        public HttpHeaders getHeaders() {
            return this.response.getHeaders();
        }

        @Override
        public InputStream getBody() throws IOException {
            if (this.body == null) {
                this.body = StreamUtils.copyToByteArray(this.response.getBody());
            }
            return new ByteArrayInputStream(this.body);
        }

        @Override
        public void close() {
            this.response.close();
        }

    }
}
