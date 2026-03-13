package zxf.springboot.pa.apitest.support.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Base test class for API tests using TestRestTemplate.
 * Provides common GET/POST methods with optional status assertion.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"pa-service.url=http://localhost:8090"})
public abstract class BaseApiTest {

    @Autowired
    protected TestRestTemplate testRestTemplate;

    // ==================== GET Methods ====================

    /**
     * Execute GET request without assertion.
     *
     * @param url the request URL
     * @return ResponseEntity with String body
     */
    protected ResponseEntity<String> get(String url) {
        return testRestTemplate.getForEntity(url, String.class);
    }

    /**
     * Execute GET request and assert status code.
     *
     * @param url           the request URL
     * @param expectedStatus expected HTTP status
     * @return ResponseEntity with String body
     */
    protected ResponseEntity<String> getAndAssert(String url, HttpStatus expectedStatus) {
        ResponseEntity<String> response = get(url);
        assertThat(response.getStatusCode()).isEqualTo(expectedStatus);
        return response;
    }

    // ==================== POST Methods ====================

    /**
     * Execute POST request without assertion.
     *
     * @param url  the request URL
     * @param body the request body (will be serialized as JSON)
     * @return ResponseEntity with String body
     */
    protected ResponseEntity<String> post(String url, Object body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RequestEntity<Object> requestEntity = new RequestEntity<>(body, headers, HttpMethod.POST, URI.create(url));
        return testRestTemplate.exchange(requestEntity, String.class);
    }

    /**
     * Execute POST request and assert status code.
     *
     * @param url           the request URL
     * @param body          the request body
     * @param expectedStatus expected HTTP status
     * @return ResponseEntity with String body
     */
    protected ResponseEntity<String> postAndAssert(String url, Object body, HttpStatus expectedStatus) {
        ResponseEntity<String> response = post(url, body);
        assertThat(response.getStatusCode()).isEqualTo(expectedStatus);
        return response;
    }
}