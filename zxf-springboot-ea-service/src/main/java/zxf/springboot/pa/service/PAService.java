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
        json.put("task", task);
        json.put("value", "Default Value in A Service of EA");
        json.put("downstream", paClient.callDownstreamSync("/pa/a/json?task=" + task, true));
        return json;
    }

    public Map<String, Object> b(String task) {
        log.info("::b");
        Map<String, Object> json = new HashMap<>();
        json.put("task", task);
        json.put("value", "Default Value in B Service of EA");
        json.put("downstream", paClient.callDownstreamSync("/pa/b/json?task=" + task, true));
        return json;
    }

    public Map<String, Object> c(String task) {
        log.info("::c");
        Map<String, Object> json = new HashMap<>();
        json.put("task", task);
        json.put("value", "Default Value in C Service of EA");
        json.put("downstream", paClient.callDownstreamSync("/pa/c/json?task=" + task, false));
        return json;
    }
}
