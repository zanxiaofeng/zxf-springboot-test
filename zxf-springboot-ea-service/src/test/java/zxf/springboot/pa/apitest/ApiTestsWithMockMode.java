package zxf.springboot.pa.apitest;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.RegularExpressionValueMatcher;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.skyscreamer.jsonassert.comparator.JSONComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.util.NestedServletException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WireMockTest(httpPort = 8089)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = MOCK)
@TestPropertySource(properties = {"pa-service.url=http://localhost:8089"})
public class ApiTestsWithMockMode {
    @Autowired
    MockMvc mockMvc;

    @Test
    void testA() throws Exception {
        //Given
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/a/json?task=200");
        // Static mock
        stubFor(get("/pa/a/json?task=200").willReturn(ok("{\"task\":\"A-200\",\"value\":\"1707039601565\"}")
                .withHeader("Content-Type", "application/json")));

        //When
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();

        //Then
        JSONAssert.assertEquals("{\"task\":\"A-200\",\"downstream\":{\"task\":\"A-200\",\"value\":\"1707039601500\"}}", mvcResult.getResponse().getContentAsString(), customizeJSONComparator());
    }

    @Test
    void testB(WireMockRuntimeInfo wireMockRuntimeInfo) throws Exception {
        //Given
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/b/json?task=200");

        // Dynamic mock can be used as required in callback code
        WireMock wireMock = wireMockRuntimeInfo.getWireMock();
        wireMock.stubFor(get("/pa/b/json?task=200").willReturn(ok("{\"task\":\"B-200\",\"value\":\"1707039601565\"}")
                .withHeader("Content-Type", "application/json")));

        //When
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();

        //Then
        JSONAssert.assertEquals("{\"task\":\"B-200\",\"downstream\":{\"task\":\"B-200\",\"value\":\"1707039601500\"}}", mvcResult.getResponse().getContentAsString(), customizeJSONComparator());
    }

    @Test
    void testC(WireMockRuntimeInfo wireMockRuntimeInfo) throws Exception {
        //Given
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/c/json?task=200");

        // Dynamic mock can be used as required in callback code
        WireMock wireMock = wireMockRuntimeInfo.getWireMock();
        wireMock.stubFor(get("/pa/c/json?task=200").willReturn(ok("{\"task\":\"C-200\",\"value\":\"1707039601565\"}")
                .withHeader("Content-Type", "application/json")));

        //When
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();

        //Then
        JSONAssert.assertEquals("{\"task\":\"C-200\",\"downstream\":{\"task\":\"C-200\",\"value\":\"1707039601500\"}}", mvcResult.getResponse().getContentAsString(), customizeJSONComparator());
    }

    @Test
    void testA400() throws Exception {
        //Given
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/a/json?task=400");
        // Static mock
        stubFor(get("/pa/a/json?task=400").willReturn(badRequest().withBody("{\"code\":\"400\"}")
                .withHeader("Content-Type", "application/json")));

        //When
        Assertions.assertThrows(NestedServletException.class, () -> {
            mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
        });
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
        JSONAssert.assertEquals("{\"task\":\"C-400\",\"downstream\":{\"code\":\"400\"}}", mvcResult.getResponse().getContentAsString(), true);
    }

    private JSONComparator customizeJSONComparator() {
        return new CustomComparator(JSONCompareMode.STRICT, Customization.customization("downstream.value", new RegularExpressionValueMatcher<>("\\d+")));
    }
}
