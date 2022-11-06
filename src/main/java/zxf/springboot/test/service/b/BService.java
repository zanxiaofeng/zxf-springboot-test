package zxf.springboot.test.service.b;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class BService {
    public BService() {
        System.out.println("*******************BService::ctor*******************");
    }

    public Map<String, String> json() {
        System.out.println("*******************BService::json*******************");
        Map<String, String> json = new HashMap<>();
        json.put("abc", "in B service");
        return json;
    }
}
