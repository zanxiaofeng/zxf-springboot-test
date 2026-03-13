package zxf.springboot.pa.apitest.support.restassured;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

/**
 * Base test class for API tests using RestAssured.
 * Provides common GET/POST methods with optional status assertion.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseRestAssuredTest {

    @LocalServerPort
    protected Integer serverPort;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = serverPort;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    // ==================== GET Methods ====================

    /**
     * Execute GET request without assertion.
     *
     * @param url the request URL (without host and port)
     * @return Response object
     */
    protected Response get(String url) {
        return RestAssured.get(url);
    }

    /**
     * Execute GET request and assert status code.
     *
     * @param url            the request URL
     * @param expectedStatus expected HTTP status code
     * @return Response object
     */
    protected Response getAndAssert(String url, int expectedStatus) {
        return RestAssured.get(url)
                .then()
                .statusCode(expectedStatus)
                .extract()
                .response();
    }

    // ==================== POST Methods ====================

    /**
     * Execute POST request without assertion.
     *
     * @param url  the request URL
     * @param body the request body (will be serialized as JSON)
     * @return Response object
     */
    protected Response post(String url, Object body) {
        return RestAssured.given()
                .contentType("application/json")
                .body(body)
                .post(url);
    }

    /**
     * Execute POST request and assert status code.
     *
     * @param url            the request URL
     * @param body           the request body
     * @param expectedStatus expected HTTP status code
     * @return Response object
     */
    protected Response postAndAssert(String url, Object body, int expectedStatus) {
        return RestAssured.given()
                .contentType("application/json")
                .body(body)
                .post(url)
                .then()
                .statusCode(expectedStatus)
                .extract()
                .response();
    }

    // ==================== PUT Methods ====================

    /**
     * Execute PUT request without assertion.
     */
    protected Response put(String url, Object body) {
        return RestAssured.given()
                .contentType("application/json")
                .body(body)
                .put(url);
    }

    /**
     * Execute PUT request and assert status code.
     */
    protected Response putAndAssert(String url, Object body, int expectedStatus) {
        return RestAssured.given()
                .contentType("application/json")
                .body(body)
                .put(url)
                .then()
                .statusCode(expectedStatus)
                .extract()
                .response();
    }

    // ==================== DELETE Methods ====================

    /**
     * Execute DELETE request without assertion.
     */
    protected Response delete(String url) {
        return RestAssured.delete(url);
    }

    /**
     * Execute DELETE request and assert status code.
     */
    protected Response deleteAndAssert(String url, int expectedStatus) {
        return RestAssured.delete(url)
                .then()
                .statusCode(expectedStatus)
                .extract()
                .response();
    }
}