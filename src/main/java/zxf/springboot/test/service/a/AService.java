package zxf.springboot.test.service.a;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AService {
    public AService() {
        System.out.println("*******************AService::ctor*******************");
    }

    public Map<String, String> json() {
        System.out.println("*******************AService::json*******************");
        Map<String, String> json = new HashMap<>();
        json.put("abc", "in A Service");
        return json;
    }
}
