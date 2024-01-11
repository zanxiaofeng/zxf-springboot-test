package zxf.springboot.pa.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Slf4j
@Service
public class PAClientFallback implements PAClient {
    public PAClientFallback() {
        log.info("::ctor");
    }

    @Override
    public Map<String, Object> serviceA(String version) {
        log.info("::serviceA");
        return Collections.singletonMap("value", "Fallback Value of A from EA: " + System.currentTimeMillis());
    }

    @Override
    public Map<String, Object> serviceB(String version) {
        log.info("::serviceB");
        return Collections.singletonMap("value", "Fallback Value of B from EA: " + System.currentTimeMillis());
    }

    @Override
    public Map<String, Object> serviceC(String version) {
        log.info("::serviceC");
        return Collections.singletonMap("value", "Fallback Value of C from EA: " + System.currentTimeMillis());
    }
}
