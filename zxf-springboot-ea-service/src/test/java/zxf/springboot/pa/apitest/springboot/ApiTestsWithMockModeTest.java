package zxf.springboot.pa.apitest.springboot;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.google.common.base.Charsets;
import jakarta.servlet.ServletException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.util.ProcessIdUtil;
import org.junit.jupiter.api.*;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.comparator.JSONComparator;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlMergeMode;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.client.HttpServerErrorException;
import org.wiremock.spring.ConfigureWireMock;
import org.wiremock.spring.EnableWireMock;
import zxf.springboot.pa.apitest.support.json.JSONComparatorFactory;
import zxf.springboot.pa.apitest.support.mocks.PAServiceMockFactory;
import zxf.springboot.pa.apitest.support.mocks.PAServiceMockVerifier;
import zxf.springboot.pa.apitest.support.springboot.BaseMockMvcTest;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@Slf4j
@EnableWireMock({@ConfigureWireMock(name = "pa-service", port = 8089, filesUnderClasspath = "mock-data")})
public class ApiTestsWithMockModeTest extends BaseMockMvcTest {
    String requestTemplate;
    JSONComparator jsonComparator;

    public ApiTestsWithMockModeTest() {
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

    @Test
    void a_pa_200WithoutProjectId() throws Exception {
        //Given
        String requestBody = requestTemplate.replace("{{task}}", "200").replace("{{projectId}}", "null");

        //When - 使用简化方法 httpPostAndAssert
        MvcResult mvcResult = httpPostAndAssert("/a/json", requestBody, HttpStatus.OK);

        //Then
        String expectedResponse = IOUtils.resourceToString("/test-data/a-post-response-4-PA_200.json", Charsets.UTF_8);
        JSONAssert.assertEquals(expectedResponse, mvcResult.getResponse().getContentAsString(), jsonComparator);
        PAServiceMockVerifier.verifyACalled(1, "200");
    }

    @Test
    void a_pa_200WithProjectIdP1() throws Exception {
        //Given
        String requestBody = requestTemplate.replace("{{task}}", "200").replace("{{projectId}}", "\"p-1\"");

        //When - 使用简化方法 httpPostAndAssert
        MvcResult mvcResult = httpPostAndAssert("/a/json", requestBody, HttpStatus.OK);

        //Then
        String expectedResponse = IOUtils.resourceToString("/test-data/a-post-response-4-PA_200-with-project-p-1.json", Charsets.UTF_8);
        JSONAssert.assertEquals(expectedResponse, mvcResult.getResponse().getContentAsString(), jsonComparator);
        PAServiceMockVerifier.verifyACalled(1, "200");
    }

    @Test
    @SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
    @Sql(scripts = {"/sql/cases/project-p-test.sql"}, config = @SqlConfig(encoding = "utf-8", transactionMode = SqlConfig.TransactionMode.ISOLATED))
    void a_pa_200WithProjectIdPTest() throws Exception {
        //Given
        String requestBody = requestTemplate.replace("{{task}}", "200").replace("{{projectId}}", "\"p-test\"");

        //When - 使用简化方法 httpPostAndAssert
        MvcResult mvcResult = httpPostAndAssert("/a/json", requestBody, HttpStatus.OK);

        //Then
        String expectedResponse = IOUtils.resourceToString("/test-data/a-post-response-4-PA_200-with-project-p-test.json", Charsets.UTF_8);
        JSONAssert.assertEquals(expectedResponse, mvcResult.getResponse().getContentAsString(), jsonComparator);
        PAServiceMockVerifier.verifyACalled(1, "200");
    }

    @Test
    void testA_PA_500() throws Exception {
        //Given
        String requestBody = requestTemplate.replace("{{task}}", "500").replace("{{projectId}}", "null");

        PAServiceMockFactory.mockAFailedResponse("500", 500, "{}");

        ServletException servletException = Assertions.assertThrows(ServletException.class, () -> {
            httpPost("/a/json", requestBody);
        });

        Assertions.assertInstanceOf(HttpServerErrorException.InternalServerError.class, servletException.getCause());

        PAServiceMockVerifier.verifyACalled(1, "500");
    }

    @Test
    void b_200(WireMockRuntimeInfo wireMockRuntimeInfo) throws Exception {
        //Given
        // Dynamic mock can be used as required in callback code
        WireMock wireMock = wireMockRuntimeInfo.getWireMock();
        wireMock.stubFor(get("/pa/b/json?task=200").willReturn(ok("{\"task\":\"PA.B-200\",\"value\":\"1707039601565\"}")
                .withHeader("Content-Type", "application/json")));

        //When - 使用简化方法 httpGetAndAssert
        MvcResult mvcResult = httpGetAndAssert("/b/json?task=200", HttpStatus.OK);

        //Then
        JSONAssert.assertEquals("{\"task\":\"EA.B-200\",\"downstream\":{\"task\":\"PA.B-200\",\"value\":\"1707039601500\"}}", mvcResult.getResponse().getContentAsString(), jsonComparator);
        PAServiceMockVerifier.verifyBCalled(1, "200");
    }

    @Test
    void c_200(WireMockRuntimeInfo wireMockRuntimeInfo) throws Exception {
        //Given
        // Dynamic mock can be used as required in callback code
        WireMock wireMock = wireMockRuntimeInfo.getWireMock();
        wireMock.stubFor(get("/pa/c/json?task=200").willReturn(ok("{\"task\":\"PA.C-200\",\"value\":\"1707039601565\"}")
                .withHeader("Content-Type", "application/json")));

        //When - 使用简化方法 httpGetAndAssert
        MvcResult mvcResult = httpGetAndAssert("/c/json?task=200", HttpStatus.OK);

        //Then
        JSONAssert.assertEquals("{\"task\":\"EA.C-200\",\"downstream\":{\"task\":\"PA.C-200\",\"value\":\"1707039601500\"},\"currentTimeMillis\":123456789}", mvcResult.getResponse().getContentAsString(), jsonComparator);
        PAServiceMockVerifier.verifyCCalled(1, "200");
    }

    @Test
    void c400(WireMockRuntimeInfo wireMockRuntimeInfo) throws Exception {
        //Given
        // Dynamic mock can be used as required in callback code
        WireMock wireMock = wireMockRuntimeInfo.getWireMock();
        wireMock.stubFor(get("/pa/c/json?task=400").willReturn(badRequest().withBody("{\"code\":\"400\"}")
                .withHeader("Content-Type", "application/json")));

        //When - 使用简化方法 httpGetAndAssert
        MvcResult mvcResult = httpGetAndAssert("/c/json?task=400", HttpStatus.OK);

        //Then
        JSONAssert.assertEquals("{\"task\":\"EA.C-400\",\"downstream\":{\"code\":\"400\"},\"currentTimeMillis\":123456789}", mvcResult.getResponse().getContentAsString(), jsonComparator);
    }
}