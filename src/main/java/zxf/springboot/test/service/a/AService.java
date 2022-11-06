package zxf.springboot.test.service.a;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zxf.springboot.test.config.AProperties;

import java.util.HashMap;
import java.util.Map;

@Service
public class AService {
    @Autowired
    private AProperties aProperties;

    public AService() {
        System.out.println("*******************AService::ctor*******************");
    }

    public Map<String, String> json(String version) {
        System.out.println("*******************AService::json*******************");
        Map<String, String> json = new HashMap<>();
        json.put("version", version);
        json.put("abc", "in A Service");
        json.put("name", aProperties.getName());
        return json;
    }
}
