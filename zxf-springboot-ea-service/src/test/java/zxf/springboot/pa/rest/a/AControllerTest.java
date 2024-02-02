package zxf.springboot.pa.rest.a;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import zxf.springboot.pa.client.PAClient;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class AControllerTest {
    @Autowired
    TestRestTemplate testRestTemplate;

    @SpyBean
    PAClient paClient;

    @Test
    void testJson() throws Exception {
        //Given
        String requestPath = "/a/json?task=200";
        Mockito.doReturn(Collections.singletonMap("abc", "in Mock Service")).when(paClient).callDownstreamSync(eq("/pa/a/json?task=200"));

        //When
        ResponseEntity<String> response = testRestTemplate.getForEntity(requestPath, String.class);

        //Then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("application/json", response.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE));
        Assertions.assertEquals("{\"task\":\"200\",\"downstream\":{\"abc\":\"in Mock Service\"},\"value\":\"Default Value in A Service of EA\"}", response.getBody());
        Mockito.verify(paClient).callDownstreamSync(eq("/pa/a/json?task=200"));
    }
}