package zxf.springboot.pa.apitest.springboot;

import com.google.common.base.Charsets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.util.ProcessIdUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.comparator.JSONComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlMergeMode;
import org.wiremock.spring.ConfigureWireMock;
import org.wiremock.spring.EnableWireMock;
import zxf.springboot.pa.apitest.support.json.JSONComparatorFactory;
import zxf.springboot.pa.apitest.support.mocks.PAServiceMockFactory;

import java.io.IOException;
import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@Slf4j
@EnableWireMock({@ConfigureWireMock(name = "pa-service", port = 8090, filesUnderClasspath= "mock-data")})
@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestPropertySource(properties = {"pa-service.url=http://localhost:8090"})
@Sql(scripts = {"/sql/cleanup/clean-up.sql", "/sql/init/schema.sql", "/sql/init/data.sql"})
public class ApiTestsWithServerModeTest {
    @Autowired
    TestRestTemplate testRestTemplate;
    String requestTemplate;
    JSONComparator jsonComparator;

    public ApiTestsWithServerModeTest() {
        log.atInfo().addArgument(() -> ProcessIdUtil.getProcessId()).log("***************************Ctor {}***************************");
    }

    @BeforeAll
    static void setupForAll() throws IOException {
        log.atInfo().addArgument(() -> ProcessIdUtil.getProcessId()).log("***************************Before all {}***************************");
    }

    @BeforeEach
    void setupForEach() throws IOException {
        requestTemplate = IOUtils.resourceToString("/test-data/a-post-request.json", Charsets.UTF_8);
        jsonComparator = JSONComparatorFactory.buildPAResponseJSONComparator();
        log.atInfo().addArgument(() -> ProcessIdUtil.getProcessId()).log("***************************Before each {}***************************");
    }

    @ParameterizedTest(name = "for PA-{0}")
    @CsvSource({"200,200,a-post-response-4-PA_200.json", "400,500,a-post-response-4-PA_400.json"
            , "500,500,a-post-response-4-PA_500.json", "503,500,a-post-response-4-PA_503.json"})
    void aWithoutProjectIdForParameterizedTest(String task, Integer status, String responseFile) throws Exception {
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
        assertEquals(status, response.getStatusCodeValue());
        JSONAssert.assertEquals(expectedResponse, response.getBody(), jsonComparator);
    }

    @ParameterizedTest(name = "for PA-{0} with project {1}")
    @SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
    @Sql(scripts = {"/sql/cases/project-p-test.sql"}, config = @SqlConfig(encoding = "utf-8", transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @CsvSource({"200,p-1,200,a-post-response-4-PA_200-with-project-p-1.json", "200,p-test,200,a-post-response-4-PA_200-with-project-p-test.json"})
    void aWithProjectIdForParameterizedTest(String task, String projectId, Integer status, String responseFile) throws Exception {
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
        assertEquals(status, response.getStatusCodeValue());
        JSONAssert.assertEquals(expectedResponse, response.getBody(), jsonComparator);
    }

    @Test
    void b_200() throws Exception {
        //Given
        String requestUrl = "/b/json?task=200";
        PAServiceMockFactory.mockBSuccessResponse("200", "{\"task\":\"PA.B-200\",\"value\":\"1707039601565\"}");

        //When
        ResponseEntity<String> response = testRestTemplate.getForEntity(requestUrl, String.class);

        //Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("application/json", response.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE));
        JSONAssert.assertEquals("{\"task\":\"EA.B-200\",\"downstream\":{\"task\":\"PA.B-200\",\"value\":\"1707039601500\"}}", response.getBody(), jsonComparator);
    }

    @Test
    void c_200() throws Exception {
        //Given
        String requestUrl = "/c/json?task=200";
        PAServiceMockFactory.mockCSuccessResponse("200", "{\"task\":\"PA.C-200\",\"value\":\"1707039601565\"}");

        //When
        ResponseEntity<String> response = testRestTemplate.getForEntity(requestUrl, String.class);

        //Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("application/json", response.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE));
        JSONAssert.assertEquals("{\"task\":\"EA.C-200\",\"downstream\":{\"task\":\"PA.C-200\",\"value\":\"1707039601500\"},\"currentTimeMillis\":123456789}", response.getBody(), jsonComparator);
    }

    @Test
    void c_400() throws Exception {
        //Given
        String requestUrl = "/c/json?task=400";
        PAServiceMockFactory.mockCFailedResponse("400", 400, "{\"code\":\"400\"}");

        //When
        ResponseEntity<String> response = testRestTemplate.getForEntity(requestUrl, String.class);

        //Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("application/json", response.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE));
        JSONAssert.assertEquals("{\"task\":\"EA.C-400\",\"downstream\":{\"code\":\"400\"},\"currentTimeMillis\":123456789}", response.getBody(), jsonComparator);
    }
}
