package zxf.springboot.test.service.b;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zxf.springboot.test.config.BProperties;

import java.util.HashMap;
import java.util.Map;

@Service
public class BService {
    @Autowired
    private BProperties bProperties;

    public BService() {
        System.out.println("*******************BService::ctor*******************");
    }

    public Map<String, String> json(String version) {
        System.out.println("*******************BService::json*******************");
        Map<String, String> json = new HashMap<>();
        json.put("version", version);
        json.put("abc", "in B service");
        json.put("name", bProperties.getName());
        return json;
    }
}
