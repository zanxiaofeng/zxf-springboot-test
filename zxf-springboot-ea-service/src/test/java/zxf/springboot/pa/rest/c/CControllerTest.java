package zxf.springboot.pa.rest.c;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.ProcessIdUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import zxf.springboot.pa.client.PAClient;
import zxf.springboot.pa.service.PAService;

import java.io.IOException;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WebMvcTest(CController.class)
@TestPropertySource("/c.properties")
public class CControllerTest {
    @Autowired
    MockMvc mockMvc;
    @SpyBean
    PAService paService;
    @MockBean
    PAClient paClient;

    @BeforeEach
    void setupForEach() throws IOException {
        log.info("***************************Before each {}***************************", ProcessIdUtil.getProcessId());
    }

    @Test
    void testJson() throws Exception {
        //Given
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/c/json?task=200");
        Mockito.doReturn(Collections.singletonMap("abc", "in Mock Service")).when(paClient).callDownstreamSyncByGet(eq("/pa/c/json?task=200"), eq(false));

        //When
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();

        //Then
        JSONAssert.assertEquals("{\"task\":\"EA.C-200\",\"downstream\":{\"abc\":\"in Mock Service\"}}", mvcResult.getResponse().getContentAsString(), true);
        Mockito.verify(paClient).callDownstreamSyncByGet(eq("/pa/c/json?task=200"), eq(false));
    }
}
