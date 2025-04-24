package zxf.springboot.pa.apitest;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.google.common.base.Charsets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.RegularExpressionValueMatcher;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.skyscreamer.jsonassert.comparator.JSONComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

import java.io.IOException;
import java.net.URI;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@Slf4j
@WireMockTest(httpPort = 8090)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestPropertySource(properties = {"pa-service.url=http://localhost:8090"})
public class ApiTestsWithServerModeTest {
    @Autowired
    TestRestTemplate testRestTemplate;
    String requestTemplate;
    JSONComparator jsonComparator;

    @BeforeAll
    static void setupForAll() throws IOException {
        log.info("Before all");
    }

    @AfterAll
    @Sql(scripts = {"/sql/cases/clean-up.sql"})
    static void cleanUpForAll() throws IOException {
        log.info("After all");
    }

    @BeforeEach
    void setupForEach() throws IOException {
        requestTemplate = IOUtils.resourceToString("/test-data/a-post-request.json", Charsets.UTF_8);
        Customization timestamp = Customization.customization("timestamp",
                new RegularExpressionValueMatcher<>("[\\d T:.+-]+"));
        Customization downstream = Customization.customization("**.downstream.value",
                new RegularExpressionValueMatcher<>("\\d+"));
        jsonComparator = new CustomComparator(JSONCompareMode.STRICT,
                downstream, timestamp);
        log.info("Before each");
    }

    @ParameterizedTest(name = "for PA-{0}")
    @CsvSource({"200,200,a-post-response-4-PA_200.json", "400,500,a-post-response-4-PA_400.json"
            , "500,500,a-post-response-4-PA_500.json", "503,500,a-post-response-4-PA_503.json"})
    void testAWithoutProjectIdForParameterizedTest(String task, Integer status, String responseFile) throws Exception {
        //Given
        String requestUrl = "/a/json";
        String requestBody = requestTemplate.replace("{{task}}", task).replace("{{projectId}}", "null");

        //When
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RequestEntity<Object> requestEntity = new RequestEntity<>(requestBody, headers, HttpMethod.POST, URI.create(requestUrl));
        ResponseEntity<String> response = testRestTemplate.exchange(requestEntity, String.class);

        //Then
        String expectedResponse = IOUtils.resourceToString("/test-data/" + responseFile, Charsets.UTF_8);
        Assertions.assertEquals(status, response.getStatusCodeValue());
        JSONAssert.assertEquals(expectedResponse, response.getBody(), jsonComparator);
    }

    @ParameterizedTest(name = "for PA-{0} with project {1}")
    @Sql(scripts = {"/sql/cases/project-p-test.sql"}, config = @SqlConfig(encoding = "utf-8", transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @CsvSource({"200,p-1,200,a-post-response-4-PA_200-with-project-p-1.json", "200,p-test,200,a-post-response-4-PA_200-with-project-p-test.json"})
    void testAWithProjectIdForParameterizedTest(String task, String projectId, Integer status, String responseFile) throws Exception {
        //Given
        String requestUrl = "/a/json";
        String requestBody = requestTemplate.replace("{{task}}", task).replace("{{projectId}}", "\"" + projectId + "\"");

        //When
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RequestEntity<Object> requestEntity = new RequestEntity<>(requestBody, headers, HttpMethod.POST, URI.create(requestUrl));
        ResponseEntity<String> response = testRestTemplate.exchange(requestEntity, String.class);

        //Then
        String expectedResponse = IOUtils.resourceToString("/test-data/" + responseFile, Charsets.UTF_8);
        Assertions.assertEquals(status, response.getStatusCodeValue());
        JSONAssert.assertEquals(expectedResponse, response.getBody(), jsonComparator);
    }


    @Test
    void testB(WireMockRuntimeInfo wireMockRuntimeInfo) throws JSONException {
        //Given
        String requestUrl = "/b/json?task=200";

        // Dynamic mock can be used as required in callback code
        WireMock wireMock = wireMockRuntimeInfo.getWireMock();
        wireMock.register(get("/pa/b/json?task=200").willReturn(ok("{\"task\":\"PA.B-200\",\"value\":\"1707039601565\"}")
                .withHeader("Content-Type", "application/json")));

        //When
        ResponseEntity<String> response = testRestTemplate.getForEntity(requestUrl, String.class);

        //Then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("application/json", response.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE));
        JSONAssert.assertEquals("{\"task\":\"EA.B-200\",\"downstream\":{\"task\":\"PA.B-200\",\"value\":\"1707039601500\"}}", response.getBody(), jsonComparator);
    }

    @Test
    void testC(WireMockRuntimeInfo wireMockRuntimeInfo) throws JSONException {
        //Given
        String requestUrl = "/c/json?task=200";

        // Dynamic mock can be used as required in callback code
        WireMock wireMock = wireMockRuntimeInfo.getWireMock();
        wireMock.register(get("/pa/c/json?task=200").willReturn(ok("{\"task\":\"PA.C-200\",\"value\":\"1707039601565\"}")
                .withHeader("Content-Type", "application/json")));

        //When
        ResponseEntity<String> response = testRestTemplate.getForEntity(requestUrl, String.class);

        //Then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("application/json", response.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE));
        JSONAssert.assertEquals("{\"task\":\"EA.C-200\",\"downstream\":{\"task\":\"PA.C-200\",\"value\":\"1707039601500\"}}", response.getBody(), jsonComparator);
    }

    @Test
    void testC400(WireMockRuntimeInfo wireMockRuntimeInfo) throws JSONException {
        //Given
        String requestUrl = "/c/json?task=400";

        // Dynamic mock can be used as required in callback code
        WireMock wireMock = wireMockRuntimeInfo.getWireMock();
        wireMock.register(get("/pa/c/json?task=400").willReturn(badRequest().withBody("{\"code\":\"400\"}")
                .withHeader("Content-Type", "application/json")));

        //When
        ResponseEntity<String> response = testRestTemplate.getForEntity(requestUrl, String.class);

        //Then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("application/json", response.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE));
        JSONAssert.assertEquals("{\"task\":\"EA.C-400\",\"downstream\":{\"code\":\"400\"}}", response.getBody(), true);
    }
}
