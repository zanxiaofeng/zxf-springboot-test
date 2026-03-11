package zxf.springboot.pa.apitest.support.mocks;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class PAServiceMockFactory {
    public static void mockBSuccessResponse(String task, String mockResponse) {
        WireMock.stubFor(WireMock.get("/pa/b/json?task=%s".formatted(task))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(mockResponse)));
    }

    public static void mockBFailedResponse(String task, int status, String mockResponse) {
        WireMock.stubFor(WireMock.get("/pa/b/json?task=%s".formatted(task))
                .willReturn(WireMock.aResponse()
                        .withStatus(status)
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(mockResponse)));
    }

    public static void mockCSuccessResponse(String task, String mockResponse) {
        WireMock.stubFor(WireMock.get("/pa/c/json?task=%s".formatted(task))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(mockResponse)));
    }

    public static void mockCFailedResponse(String task, int status, String mockResponse) {
        WireMock.stubFor(WireMock.get("/pa/c/json?task=%s".formatted(task))
                .willReturn(WireMock.aResponse()
                        .withStatus(status)
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(mockResponse)));
    }
}
