package zxf.springboot.pa.apitest.support.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Base test class for API tests using TestRestTemplate.
 * Provides common GET/POST methods with optional status assertion.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"/sql/cleanup/clean-up.sql", "/sql/init/schema.sql", "/sql/init/data.sql"})
public abstract class BaseServerModeTest {
    @Autowired
    protected TestRestTemplate testRestTemplate;

    // ==================== GET Methods ====================

    /**
     * Execute HTTP GET request without assertion.
     *
     * @param url the request URL
     * @return ResponseEntity with String body
     */
    protected ResponseEntity<String> httpGet(String url) {
        return testRestTemplate.getForEntity(url, String.class);
    }

    /**
     * Execute HTTP GET request and assert status code.
     *
     * @param url           the request URL
     * @param expectedStatus expected HTTP status
     * @return ResponseEntity with String body
     */
    protected ResponseEntity<String> httpGetAndAssert(String url, HttpStatus expectedStatus) {
        ResponseEntity<String> response = httpGet(url);
        assertThat(response.getStatusCode()).isEqualTo(expectedStatus);
        return response;
    }

    // ==================== POST Methods ====================

    /**
     * Execute HTTP POST request without assertion.
     *
     * @param url  the request URL
     * @param body the request body (will be serialized as JSON)
     * @return ResponseEntity with String body
     */
    protected ResponseEntity<String> httpPost(String url, Object body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RequestEntity<Object> requestEntity = new RequestEntity<>(body, headers, HttpMethod.POST, URI.create(url));
        return testRestTemplate.exchange(requestEntity, String.class);
    }

    /**
     * Execute HTTP POST request and assert status code.
     *
     * @param url           the request URL
     * @param body          the request body
     * @param expectedStatus expected HTTP status
     * @return ResponseEntity with String body
     */
    protected ResponseEntity<String> httpPostAndAssert(String url, Object body, HttpStatus expectedStatus) {
        ResponseEntity<String> response = httpPost(url, body);
        assertThat(response.getStatusCode()).isEqualTo(expectedStatus);
        return response;
    }
}