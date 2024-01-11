package zxf.springboot.test.rest.b;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import zxf.springboot.test.service.b.BService;

import java.util.Collections;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BController.class)
@TestPropertySource(properties = {"version=v"})
public class BControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    BService bService;

    @Test
    void testJson() throws Exception {
        //Given
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/b/json");
        Mockito.when(bService.json("v")).thenReturn(Collections.singletonMap("abc", "in Mock Service"));

        //When
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();

        //Then
        Assertions.assertEquals("{\"abc\":\"in Mock Service\"}", mvcResult.getResponse().getContentAsString());
        Mockito.verify(bService).json("v");
    }
}
