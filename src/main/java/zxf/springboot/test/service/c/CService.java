package zxf.springboot.test.service.c;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zxf.springboot.test.config.CProperties;

import java.util.HashMap;
import java.util.Map;

@Service
public class CService {
    @Autowired
    private CProperties cProperties;

    public CService() {
        System.out.println("*******************CService::ctor*******************");
    }

    public Map<String, String> json(String version) {
        System.out.println("*******************CService::json*******************");
        Map<String, String> json = new HashMap<>();
        json.put("version", version);
        json.put("abc", "in C service");
        json.put("name", cProperties.getName());
        return json;
    }
}
