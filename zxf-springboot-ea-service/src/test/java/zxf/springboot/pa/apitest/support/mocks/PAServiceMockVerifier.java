package zxf.springboot.pa.apitest.support.mocks;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class PAServiceMockVerifier {
    public static void verifyACalled(int calledCount, String task) {
        WireMock.verify(calledCount, postRequestedFor(urlEqualTo("/pa/a/json"))
                .withHeader(HttpHeaders.CONTENT_TYPE, equalTo(MediaType.APPLICATION_JSON_VALUE))
                .withRequestBody(equalToJson("{\"task\":%s}".formatted(task)))
        );
    }

    public static void verifyBCalled(int calledCount, String task) {
        WireMock.verify(calledCount, getRequestedFor(urlEqualTo("/pa/b/json?task=%s".formatted(task))));
    }

    public static void verifyCCalled(int calledCount, String task) {
        WireMock.verify(calledCount, getRequestedFor(urlEqualTo("/pa/c/json?task=%s".formatted(task))));
    }
}
