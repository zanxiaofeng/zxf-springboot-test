package zxf.springboot.pa.apitest.support.springboot;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Base test class for API tests using MockMvc.
 * Provides common GET/POST methods with optional status assertion.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public abstract class BaseMockMvcTest {
    @Autowired
    protected MockMvc mockMvc;
    
    private final ObjectMapper objectMapper = new ObjectMapper();

    // ==================== GET Methods ====================

    /**
     * Execute GET request without assertion.
     *
     * @param url the request URL
     * @return MvcResult containing response
     */
    protected MvcResult get(String url) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andReturn();
    }

    /**
     * Execute GET request and assert status code.
     *
     * @param url            the request URL
     * @param expectedStatus expected HTTP status
     * @return MvcResult containing response
     */
    protected MvcResult getAndAssert(String url, HttpStatus expectedStatus) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andExpect(status().is(expectedStatus.value()))
                .andReturn();
    }

    // ==================== POST Methods ====================

    /**
     * Execute POST request without assertion.
     *
     * @param url  the request URL
     * @param body the request body (will be serialized as JSON)
     * @return MvcResult containing response
     */
    protected MvcResult post(String url, Object body) throws Exception {
        return mockMvc.perform(createPostRequest(url, body))
                .andReturn();
    }

    /**
     * Execute POST request and assert status code.
     *
     * @param url            the request URL
     * @param body           the request body
     * @param expectedStatus expected HTTP status
     * @return MvcResult containing response
     */
    protected MvcResult postAndAssert(String url, Object body, HttpStatus expectedStatus) throws Exception {
        return mockMvc.perform(createPostRequest(url, body))
                .andExpect(status().is(expectedStatus.value()))
                .andReturn();
    }

    // ==================== PUT Methods ====================

    /**
     * Execute PUT request without assertion.
     */
    protected MvcResult put(String url, Object body) throws Exception {
        return mockMvc.perform(createPutRequest(url, body))
                .andReturn();
    }

    /**
     * Execute PUT request and assert status code.
     */
    protected MvcResult putAndAssert(String url, Object body, HttpStatus expectedStatus) throws Exception {
        return mockMvc.perform(createPutRequest(url, body))
                .andExpect(status().is(expectedStatus.value()))
                .andReturn();
    }

    // ==================== DELETE Methods ====================

    /**
     * Execute DELETE request without assertion.
     */
    protected MvcResult delete(String url) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.delete(url))
                .andReturn();
    }

    /**
     * Execute DELETE request and assert status code.
     */
    protected MvcResult deleteAndAssert(String url, HttpStatus expectedStatus) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.delete(url))
                .andExpect(status().is(expectedStatus.value()))
                .andReturn();
    }

    // ==================== Helper Methods ====================

    private MockHttpServletRequestBuilder createPostRequest(String url, Object body) throws Exception {
        String jsonBody = objectMapper.writeValueAsString(body);
        return MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody);
    }

    private MockHttpServletRequestBuilder createPutRequest(String url, Object body) throws Exception {
        String jsonBody = objectMapper.writeValueAsString(body);
        return MockMvcRequestBuilders.put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody);
    }
}