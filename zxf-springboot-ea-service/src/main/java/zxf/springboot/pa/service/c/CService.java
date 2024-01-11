package zxf.springboot.pa.service.c;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zxf.springboot.pa.client.PAClient;
import zxf.springboot.pa.config.CProperties;

import java.util.HashMap;
import java.util.Map;

@Service
public class CService {
    @Autowired
    private CProperties cProperties;

    @Autowired
    private PAClient paClient;

    public CService() {
        System.out.println("*******************CService::ctor*******************");
    }

    public Map<String, Object> json(String version) {
        System.out.println("*******************CService::json*******************");
        Map<String, Object> json = new HashMap<>();
        json.put("version", version);
        json.put("name", cProperties.getName());
        json.put("value", "Default Value in C Service of EA");
        json.put("downstream", paClient.serviceB(version));
        return json;
    }
}
