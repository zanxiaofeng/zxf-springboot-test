package zxf.springboot.pa.apitest;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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
        stubFor(get("/pa/a/json?task=200").willReturn(ok("{\"abc\":\"in Mock Service\"}")
                .withHeader("Content-Type", "application/json")));

        //When
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();

        //Then
        Assertions.assertEquals("{\"task\":\"200\",\"downstream\":{\"abc\":\"in Mock Service\"},\"value\":\"Default Value in A Service of EA\"}", mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testB(WireMockRuntimeInfo wireMockRuntimeInfo) throws Exception {
        //Given
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/b/json?task=200");

        // Dynamic mock can be used as required in callback code
        WireMock wireMock = wireMockRuntimeInfo.getWireMock();
        wireMock.stubFor(get("/pa/b/json?task=200").willReturn(ok("{\"abc\":\"in Mock Service\"}")
                .withHeader("Content-Type", "application/json")));

        //When
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();

        //Then
        Assertions.assertEquals("{\"task\":\"200\",\"downstream\":{\"abc\":\"in Mock Service\"},\"value\":\"Default Value in B Service of EA\"}", mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testC(WireMockRuntimeInfo wireMockRuntimeInfo) throws Exception {
        //Given
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/c/json?task=200");

        // Dynamic mock can be used as required in callback code
        WireMock wireMock = wireMockRuntimeInfo.getWireMock();
        wireMock.stubFor(get("/pa/c/json?task=200").willReturn(ok("{\"abc\":\"in Mock Service\"}")
                .withHeader("Content-Type", "application/json")));

        //When
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();

        //Then
        Assertions.assertEquals("{\"task\":\"200\",\"downstream\":{\"abc\":\"in Mock Service\"},\"value\":\"Default Value in C Service of EA\"}", mvcResult.getResponse().getContentAsString());
    }
}
