package zxf.springboot.pa.client;

import java.util.Collections;
import java.util.Map;

public class PAClientFallback implements PAClient {
    @Override
    public Map<String, Object> serviceA(String version) {
        System.out.println("*******************PAClientFallback::serviceA*******************");
        return Collections.singletonMap("value", "Fallback Value of A from EA: " + System.currentTimeMillis());
    }

    @Override
    public Map<String, Object> serviceB(String version) {
        System.out.println("*******************PAClientFallback::serviceB*******************");
        return Collections.singletonMap("value", "Fallback Value of B from EA: " + System.currentTimeMillis());
    }

    @Override
    public Map<String, Object> serviceC(String version) {
        System.out.println("*******************PAClientFallback::serviceC*******************");
        return Collections.singletonMap("value", "Fallback Value of C from EA: " + System.currentTimeMillis());
    }
}
