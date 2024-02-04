package zxf.springboot.pa.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zxf.springboot.pa.client.PAClient;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class PAService {
    @Autowired
    private PAClient paClient;

    public PAService() {
        log.info("::ctor");
    }

    public Map<String, Object> a(String task) {
        log.info("::a");
        Map<String, Object> json = new HashMap<>();
        json.put("task", "A-" + task);
        json.put("downstream", paClient.callDownstreamSync("/pa/a/json?task=" + task, true));
        return json;
    }

    public Map<String, Object> b(String task) {
        log.info("::b");
        Map<String, Object> json = new HashMap<>();
        json.put("task", "B-" + task);
        json.put("downstream", paClient.callDownstreamSync("/pa/b/json?task=" + task, true));
        return json;
    }

    public Map<String, Object> c(String task) {
        log.info("::c");
        Map<String, Object> json = new HashMap<>();
        json.put("task", "C-" + task);
        json.put("downstream", paClient.callDownstreamSync("/pa/c/json?task=" + task, false));
        return json;
    }
}
