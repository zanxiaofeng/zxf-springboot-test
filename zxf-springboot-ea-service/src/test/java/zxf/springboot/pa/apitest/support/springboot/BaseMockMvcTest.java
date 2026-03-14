package zxf.springboot.pa.apitest.support.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Base test class for API tests using MockMvc.
 * Provides common GET/POST methods with optional status assertion.
 */
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Sql(scripts = {"/sql/cleanup/clean-up.sql", "/sql/init/schema.sql", "/sql/init/data.sql"})
public abstract class BaseMockMvcTest {
    @Autowired
    protected MockMvc mockMvc;

    // ==================== GET Methods ====================

    /**
     * Execute HTTP GET request without assertion.
     *
     * @param url the request URL
     * @return MvcResult containing response
     */
    protected MvcResult httpGet(String url) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andReturn();
    }

    /**
     * Execute HTTP GET request and assert status code.
     *
     * @param url            the request URL
     * @param expectedStatus expected HTTP status
     * @return MvcResult containing response
     */
    protected MvcResult httpGetAndAssert(String url, HttpStatus expectedStatus) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andExpect(status().is(expectedStatus.value()))
                .andReturn();
    }

    // ==================== POST Methods ====================

    /**
     * Execute HTTP POST request without assertion.
     *
     * @param url  the request URL
     * @param body the request body as JSON string (will be sent directly)
     * @return MvcResult containing response
     */
    protected MvcResult httpPost(String url, String body) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andReturn();
    }

    /**
     * Execute HTTP POST request and assert status code.
     *
     * @param url            the request URL
     * @param body           the request body as JSON string (will be sent directly)
     * @param expectedStatus expected HTTP status
     * @return MvcResult containing response
     */
    protected MvcResult httpPostAndAssert(String url, String body, HttpStatus expectedStatus) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().is(expectedStatus.value()))
                .andReturn();
    }
}