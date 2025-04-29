package zxf.springboot.pa.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zxf.springboot.pa.client.PAClient;
import zxf.springboot.pa.rest.request.TaskRequest;
import zxf.springboot.pa.utils.SystemUtils;

import java.util.Collections;
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

    public Map<String, Object> a(TaskRequest task) {
        log.info("::a");
        Map<String, Object> json = new HashMap<>();
        json.put("task", "EA.A-" + task.getTask());
        json.put("downstream", paClient.callDownstreamSyncByPost("/pa/a/json", task, true));
        return json;
    }

    public Map<String, Object> b(String task) {
        log.info("::b");
        Map<String, Object> json = new HashMap<>();
        json.put("task", "EA.B-" + task);
        json.put("downstream", paClient.callDownstreamSyncByGet("/pa/b/json?task=" + task, true));
        return json;
    }

    public Map<String, Object> c(String task) {
        log.info("::c");
        Map<String, Object> json = new HashMap<>();
        json.put("task", "EA.C-" + task);
        json.put("downstream", paClient.callDownstreamSyncByGet("/pa/c/json?task=" + task, false));
        json.put("currentTimeMillis", SystemUtils.currentTimeMillis());
        return json;
    }
}
