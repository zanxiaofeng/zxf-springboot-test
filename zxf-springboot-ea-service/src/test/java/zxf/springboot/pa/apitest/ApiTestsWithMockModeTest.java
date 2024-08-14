package zxf.springboot.pa.apitest;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.google.common.base.Charsets;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.RegularExpressionValueMatcher;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.skyscreamer.jsonassert.comparator.JSONComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.util.NestedServletException;

import java.io.IOException;
import java.net.URI;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WireMockTest(httpPort = 8089)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = MOCK)
public class ApiTestsWithMockModeTest {
    @Autowired
    MockMvc mockMvc;
    String requestTemplate;
    JSONComparator jsonComparator;

    @BeforeEach
    void setup() throws IOException {
        requestTemplate = IOUtils.resourceToString("/test-data/a-post-request.json", Charsets.UTF_8);
        jsonComparator = new CustomComparator(JSONCompareMode.STRICT,
                Customization.customization("**.downstream.value",
                        new RegularExpressionValueMatcher<>("\\d+")));
    }

    @Test
    void testA_PA_200() throws Exception {
        //Given
        String requestBody = requestTemplate.replace("{{task}}", "200");
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/a/json")
                .content(requestBody).contentType("application/json");

        //When
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();

        //Then
        String expectedResponse = IOUtils.resourceToString("/test-data/a-post-response-4-PA_200.json", Charsets.UTF_8);
        JSONAssert.assertEquals(expectedResponse, mvcResult.getResponse().getContentAsString(), jsonComparator);
    }

    //@Test
    void testA_PA_505() throws Exception {
        //Given
        String requestBody = requestTemplate.replace("{{task}}", "505");
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/a/json")
                .content(requestBody).contentType("application/json");

        // Static mock
        stubFor(post("/pa/a/json").withRequestBody(equalToJson(requestBody))
                .withHeader("Content-Type", equalTo("application/json"))
                .willReturn(WireMock.status(505)));

        //When
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andExpect(status().isInternalServerError()).andReturn();
    }

    @Test
    void testB(WireMockRuntimeInfo wireMockRuntimeInfo) throws Exception {
        //Given
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/b/json?task=200");

        // Dynamic mock can be used as required in callback code
        WireMock wireMock = wireMockRuntimeInfo.getWireMock();
        wireMock.stubFor(get("/pa/b/json?task=200").willReturn(ok("{\"task\":\"PA.B-200\",\"value\":\"1707039601565\"}")
                .withHeader("Content-Type", "application/json")));

        //When
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();

        //Then
        JSONAssert.assertEquals("{\"task\":\"EA.B-200\",\"downstream\":{\"task\":\"PA.B-200\",\"value\":\"1707039601500\"}}", mvcResult.getResponse().getContentAsString(), jsonComparator);
    }

    @Test
    void testC(WireMockRuntimeInfo wireMockRuntimeInfo) throws Exception {
        //Given
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/c/json?task=200");

        // Dynamic mock can be used as required in callback code
        WireMock wireMock = wireMockRuntimeInfo.getWireMock();
        wireMock.stubFor(get("/pa/c/json?task=200").willReturn(ok("{\"task\":\"PA.C-200\",\"value\":\"1707039601565\"}")
                .withHeader("Content-Type", "application/json")));

        //When
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();

        //Then
        JSONAssert.assertEquals("{\"task\":\"EA.C-200\",\"downstream\":{\"task\":\"PA.C-200\",\"value\":\"1707039601500\"}}", mvcResult.getResponse().getContentAsString(), jsonComparator);
    }

    @Test
    void testC400(WireMockRuntimeInfo wireMockRuntimeInfo) throws Exception {
        //Given
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/c/json?task=400");

        // Dynamic mock can be used as required in callback code
        WireMock wireMock = wireMockRuntimeInfo.getWireMock();
        wireMock.stubFor(get("/pa/c/json?task=400").willReturn(badRequest().withBody("{\"code\":\"400\"}")
                .withHeader("Content-Type", "application/json")));

        //When
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();

        //Then
        JSONAssert.assertEquals("{\"task\":\"EA.C-400\",\"downstream\":{\"code\":\"400\"}}", mvcResult.getResponse().getContentAsString(), true);
    }
}
