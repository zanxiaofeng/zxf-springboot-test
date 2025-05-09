package zxf.springboot.pa.rest.a;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.ProcessIdUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import zxf.springboot.pa.client.PAClient;
import zxf.springboot.pa.rest.request.TaskRequest;

import java.io.IOException;
import java.net.URI;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@Slf4j
@SpringBootTest(webEnvironment = RANDOM_PORT)
class AControllerTest {
    @Autowired
    TestRestTemplate testRestTemplate;

    @SpyBean
    PAClient paClient;

    public AControllerTest() {
        log.info("***************************Ctor {}***************************", ProcessIdUtil.getProcessId());
    }

    @BeforeAll
    static void setupForAll() throws IOException {
        log.info("***************************Before all {}***************************", ProcessIdUtil.getProcessId());
    }

    @BeforeEach
    void setupForEach() throws IOException {
        log.info("***************************Before each {}***************************", ProcessIdUtil.getProcessId());
    }

    @Test
    void testJson() throws Exception {
        //Given
        String requestPath = "/a/json";
        String requestBody = "{\"task\": 200}";
        Mockito.doReturn(Collections.singletonMap("abc", "in Mock Service")).when(paClient).callDownstreamSyncByPost(eq("/pa/a/json"), any(TaskRequest.class), eq(true));

        //When
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RequestEntity<String> requestEntity = new RequestEntity<>(requestBody, headers, HttpMethod.POST, URI.create(requestPath));
        ResponseEntity<String> response = testRestTemplate.exchange(requestEntity, String.class);

        //Then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("application/json", response.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE));
        JSONAssert.assertEquals("{\"task\":\"EA.A-200\",\"downstream\":{\"abc\":\"in Mock Service\"}}", response.getBody(), true);
        Mockito.verify(paClient).callDownstreamSyncByPost(eq("/pa/a/json"), any(TaskRequest.class), eq(true));
    }
}