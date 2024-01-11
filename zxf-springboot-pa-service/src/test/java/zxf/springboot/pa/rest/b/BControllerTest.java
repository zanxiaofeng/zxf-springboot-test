package zxf.springboot.pa.rest.b;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BController.class)
public class BControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    void testJson() throws Exception {
        //Given
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/pa/b/json");

        //When
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();

        //Then
        Assertions.assertEquals("{\"abc\":\"in Mock Service\"}", mvcResult.getResponse().getContentAsString());
    }
}
