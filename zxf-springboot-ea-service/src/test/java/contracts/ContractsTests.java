package contracts;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import java.util.Collections;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@WireMockTest(httpPort = 8089)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestPropertySource(properties = {"pa-service.url=http://localhost:8089"})
public class ContractsTests {
    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    void testA(WireMockRuntimeInfo wireMockRuntimeInfo) {
        //Given
        String requestUrl = "/a/json?task=200";
        // Static mock
        stubFor(get("/pa/a/json?task=200").willReturn(ok("{\"abc\":\"in Mock Service\"}")));

        //When
        ResponseEntity<String> response = testRestTemplate.getForEntity(requestUrl, String.class);

        //Then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("application/json", response.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE));
        Assertions.assertEquals("{\"task\":\"200\",\"downstream\":{\"abc\":\"in Mock Service\"},\"value\":\"Default Value in A Service of EA\"}", response.getBody());
    }

    @Test
    void testB(WireMockRuntimeInfo wireMockRuntimeInfo) {
        //Given
        String requestUrl = "/b/json?task=200";

        // Dynamic mock
        WireMock wireMock = wireMockRuntimeInfo.getWireMock();
        wireMock.register(get("/pa/b/json?version=200").willReturn(ok("{\"abc\":\"in Mock Service\"}")));

        //When
        ResponseEntity<String> response = testRestTemplate.getForEntity(requestUrl, String.class);

        //Then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("application/json", response.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE));
        Assertions.assertEquals("{\"task\":\"200\",\"downstream\":{\"abc\":\"in Mock Service\"},\"value\":\"Default Value in A Service of EA\"}", response.getBody());
    }
}
