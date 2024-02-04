package zxf.springboot.pa.rest.b;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import zxf.springboot.pa.client.PAClient;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = MOCK)
public class BControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    PAClient paClient;

    @Test
    void testJson() throws Exception {
        //Given
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/b/json?task=200");
        Mockito.doReturn(Collections.singletonMap("abc", "in Mock Service")).when(paClient).callDownstreamSync(eq("/pa/b/json?task=200"), eq(true));

        //When
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();

        //Then
        JSONAssert.assertEquals("{\"task\":\"B-200\",\"downstream\":{\"abc\":\"in Mock Service\"}}", mvcResult.getResponse().getContentAsString(), true);
        Mockito.verify(paClient).callDownstreamSync(eq("/pa/b/json?task=200"), eq(true));
    }
}
