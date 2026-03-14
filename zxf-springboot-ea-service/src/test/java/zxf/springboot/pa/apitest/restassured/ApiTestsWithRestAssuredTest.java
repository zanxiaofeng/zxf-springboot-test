package zxf.springboot.pa.apitest.restassured;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.google.common.base.Charsets;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.util.ProcessIdUtil;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@Slf4j
@Testcontainers
@SpringBootTest(webEnvironment = RANDOM_PORT)
@Sql(scripts = {"/sql/cleanup/clean-up.sql", "/sql/init/schema.sql", "/sql/init/data.sql"})
public class ApiTestsWithRestAssuredTest {

    @Container
    static GenericContainer<?> wireMockServer = new GenericContainer<>(
            DockerImageName.parse("wiremock/wiremock:3.9.2")
    )
            .withExposedPorts(8080)
            .withFileSystemBind("src/test/resources/mock-data", "/home/wiremock");

    @DynamicPropertySource
    static void wireMockProperties(DynamicPropertyRegistry registry) {
        registry.add("pa-service.url", () -> "http://" + wireMockServer.getHost() + ":" + wireMockServer.getFirstMappedPort());
    }

    @LocalServerPort
    private Integer serverPort;
    private String requestTemplate;

    public ApiTestsWithRestAssuredTest() throws IOException {
        log.atInfo().addArgument(() -> ProcessIdUtil.getProcessId()).log("***************************Ctor {}***************************");
        requestTemplate = IOUtils.resourceToString("/test-data/a-post-request.json", Charsets.UTF_8);
    }

    @BeforeAll
    static void setupForAll() throws IOException {
        log.atInfo().addArgument(() -> ProcessIdUtil.getProcessId()).log("***************************Before all {}***************************");
    }

    @BeforeEach
    void setupForEach() throws IOException {
        log.atInfo().addArgument(() -> ProcessIdUtil.getProcessId()).log("***************************Before each {}***************************");
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = serverPort;
    }


    @ParameterizedTest(name = "for PA-{0}")
    @CsvSource({"200,200,a-post-response-4-PA_200.json", "400,500,a-post-response-4-PA_400.json"
            , "500,500,a-post-response-4-PA_500.json", "503,500,a-post-response-4-PA_503.json"})
    void aWithoutProjectIdForParameterizedTest(String task, Integer status, String responseFile) throws Exception {
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
    void b() {
        // Configure dynamic stub using WireMock Java client connecting to the Docker container
        WireMock wireMock = new WireMock(wireMockServer.getHost(), wireMockServer.getFirstMappedPort());
        wireMock.register(get("/pa/b/json?task=200").willReturn(ok("{\"task\":\"PA.B-200\",\"value\":\"1707039601565\"}")
                .withHeader("Content-Type", "application/json")));

        RestAssured
                .given().log().all()
                .queryParam("task", "200")
                .get("/b/json").then()
                .statusCode(200)
                .header(HttpHeaders.CONTENT_TYPE, Matchers.equalTo("application/json"))
                .assertThat()
                .body("task", Matchers.equalTo("EA.B-200"))
                .body("downstream.task", Matchers.equalTo("PA.B-200"))
                .body("downstream.value", Matchers.equalTo("1707039601565"));
    }
}
