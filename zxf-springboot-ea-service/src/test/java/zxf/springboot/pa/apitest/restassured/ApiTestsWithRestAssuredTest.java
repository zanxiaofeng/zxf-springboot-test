package zxf.springboot.pa.apitest.restassured;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.google.common.base.Charsets;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.util.ProcessIdUtil;
import org.hamcrest.Matchers;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@Slf4j
@WireMockTest(httpPort = 8090)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestPropertySource(properties = {"pa-service.url=http://localhost:8090"})
@Sql(scripts = {"/sql/cleanup/clean-up.sql", "/sql/init/schema.sql", "/sql/init/data.sql"})
public class ApiTestsWithRestAssuredTest {
    @LocalServerPort
    private Integer serverPort;
    private String requestTemplate;

    public ApiTestsWithRestAssuredTest() throws IOException {
        log.info("***************************Ctor {}***************************", ProcessIdUtil.getProcessId());
        requestTemplate = IOUtils.resourceToString("/test-data/a-post-request.json", Charsets.UTF_8);
    }

    @BeforeAll
    static void setupForAll() throws IOException {
        log.info("***************************Before all {}***************************", ProcessIdUtil.getProcessId());
    }

    @BeforeEach
    void setupForEach() throws IOException {
        log.info("***************************Before each {}***************************", ProcessIdUtil.getProcessId());
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = serverPort;
    }


    @ParameterizedTest(name = "for PA-{0}")
    @CsvSource({"200,200,a-post-response-4-PA_200.json", "400,500,a-post-response-4-PA_400.json"
            , "500,500,a-post-response-4-PA_500.json", "503,500,a-post-response-4-PA_503.json"})
    void testAWithoutProjectIdForParameterizedTest(String task, Integer status, String responseFile) throws JSONException, IOException {
        String requestUrl = "/a/json";
        String requestBody = requestTemplate.replace("{{task}}", task).replace("{{projectId}}", "null");

        RestAssured
                .given().log().all()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString())
                .body(requestBody.getBytes(StandardCharsets.UTF_8))
                .post(requestUrl)
                .then()
                .statusCode(status)
                .header(HttpHeaders.CONTENT_TYPE, Matchers.equalTo("application/json"))
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("test-data/" + responseFile));
    }


    @Test
    void testB(WireMockRuntimeInfo wireMockRuntimeInfo) throws JSONException {
        // Dynamic mock can be used as required in callback code
        WireMock wireMock = wireMockRuntimeInfo.getWireMock();
        wireMock.register(get("/pa/b/json?task=200").willReturn(ok("{\"task\":\"PA.B-200\",\"value\":\"1707039601565\"}")
                .withHeader("Content-Type", "application/json")));

        RestAssured
                .given().log().all()
                .queryParam("task", "200")
                .get("/b/json").then()
                .statusCode(200)
                .header(HttpHeaders.CONTENT_TYPE, Matchers.equalTo("application/json"))
                .assertThat()
                .body("task", Matchers.equalTo("EA.B-200"),
                        "downstream.task", Matchers.equalTo("PA.B-200"),
                        "downstream.value", Matchers.equalTo("1707039601565")
                );
    }
}
