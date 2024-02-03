package zxf.springboot.pa.apitest;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@WireMockTest(httpPort = 8089)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestPropertySource(properties = {"pa-service.url=http://localhost:8089"})
public class ApiTestsWithServerMode {
    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    void testA() throws JSONException {
        //Given
        String requestUrl = "/a/json?task=200";
        // Static mock
        stubFor(get("/pa/a/json?task=200").willReturn(ok("{\"abc\":\"in Mock Service\"}")
                .withHeader("Content-Type", "application/json")));

        //When
        ResponseEntity<String> response = testRestTemplate.getForEntity(requestUrl, String.class);

        //Then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("application/json", response.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE));
        JSONAssert.assertEquals("{\"task\":\"200\",\"downstream\":{\"abc\":\"in Mock Service\"},\"value\":\"Default Value in A Service of EA\"}", response.getBody(), true);
    }

    @Test
    void testB(WireMockRuntimeInfo wireMockRuntimeInfo) throws JSONException {
        //Given
        String requestUrl = "/b/json?task=200";

        // Dynamic mock can be used as required in callback code
        WireMock wireMock = wireMockRuntimeInfo.getWireMock();
        wireMock.register(get("/pa/b/json?task=200").willReturn(ok("{\"abc\":\"in Mock Service\"}")
                .withHeader("Content-Type", "application/json")));

        //When
        ResponseEntity<String> response = testRestTemplate.getForEntity(requestUrl, String.class);

        //Then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("application/json", response.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE));
        JSONAssert.assertEquals("{\"task\":\"200\",\"downstream\":{\"abc\":\"in Mock Service\"},\"value\":\"Default Value in B Service of EA\"}", response.getBody(), true);
    }

    @Test
    void testC(WireMockRuntimeInfo wireMockRuntimeInfo) throws JSONException {
        //Given
        String requestUrl = "/c/json?task=200";

        // Dynamic mock can be used as required in callback code
        WireMock wireMock = wireMockRuntimeInfo.getWireMock();
        wireMock.register(get("/pa/c/json?task=200").willReturn(ok("{\"abc\":\"in Mock Service\"}")
                .withHeader("Content-Type", "application/json")));

        //When
        ResponseEntity<String> response = testRestTemplate.getForEntity(requestUrl, String.class);

        //Then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("application/json", response.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE));
        JSONAssert.assertEquals("{\"task\":\"200\",\"downstream\":{\"abc\":\"in Mock Service\"},\"value\":\"Default Value in C Service of EA\"}", response.getBody(), true);
    }

    @Test
    void testC400(WireMockRuntimeInfo wireMockRuntimeInfo) throws JSONException {
        //Given
        String requestUrl = "/c/json?task=400";

        // Dynamic mock can be used as required in callback code
        WireMock wireMock = wireMockRuntimeInfo.getWireMock();
        wireMock.register(get("/pa/c/json?task=400").willReturn(badRequest().withBody("{\"code\":\"400\"}")
                .withHeader("Content-Type", "application/json")));

        //When
        ResponseEntity<String> response = testRestTemplate.getForEntity(requestUrl, String.class);

        //Then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("application/json", response.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE));
        JSONAssert.assertEquals("{\"task\":\"400\",\"downstream\":null,\"value\":\"Default Value in C Service of EA\"}", response.getBody(), true);
    }
}
