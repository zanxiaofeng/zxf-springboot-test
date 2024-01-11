package zxf.springboot.pa.service.a;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zxf.springboot.pa.client.PAClient;
import zxf.springboot.pa.config.AProperties;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class AService {
    @Autowired
    private AProperties aProperties;

    @Autowired
    private PAClient paClient;

    public AService() {
        log.info("::ctor");
    }

    public Map<String, Object> json(String version) {
        log.info("::json");
        Map<String, Object> json = new HashMap<>();
        json.put("version", version);
        json.put("name", aProperties.getName());
        json.put("value", "Default Value in A Service of EA");
        json.put("downstream", paClient.serviceA(version));
        return json;
    }
}
