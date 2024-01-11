package zxf.springboot.pa.service.b;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zxf.springboot.pa.client.PAClient;
import zxf.springboot.pa.config.BProperties;

import java.util.HashMap;
import java.util.Map;

@Service
public class BService {
    @Autowired
    private BProperties bProperties;

    @Autowired
    private PAClient paClient;

    public BService() {
        System.out.println("*******************BService::ctor*******************");
    }

    public Map<String, Object> json(String version) {
        System.out.println("*******************BService::json*******************");
        Map<String, Object> json = new HashMap<>();
        json.put("version", version);
        json.put("name", bProperties.getName());
        json.put("value", "Default Value in B Service of EA");
        json.put("downstream", paClient.serviceB(version));
        return json;
    }
}
