package zxf.springboot.pa.apitest.support.mocks;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

public class PAServiceMockFactory {
    public static void mockASuccessResponse(String task, String response) {
        WireMock.stubFor(WireMock.post(urlEqualTo("/pa/a/json"))
                .withHeader(HttpHeaders.CONTENT_TYPE, equalTo(MediaType.APPLICATION_JSON_VALUE))
                .withRequestBody(equalToJson("{\"task\":%s}".formatted(task)))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(response)));
    }

    public static void mockAFailedResponse(String task, int status, String response) {
        WireMock.stubFor(WireMock.post(urlEqualTo("/pa/a/json"))
                .withHeader(HttpHeaders.CONTENT_TYPE, equalTo(MediaType.APPLICATION_JSON_VALUE))
                .withRequestBody(equalToJson("{\"task\":%s}".formatted(task)))
                .willReturn(WireMock.aResponse()
                        .withStatus(status)
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(response)));
    }

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
