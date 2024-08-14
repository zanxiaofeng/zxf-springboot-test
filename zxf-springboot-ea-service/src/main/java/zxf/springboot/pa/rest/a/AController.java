package zxf.springboot.pa.rest.a;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zxf.springboot.pa.rest.request.TaskRequest;
import zxf.springboot.pa.service.PAService;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/a")
public class AController {
    @Autowired
    private PAService paService;

    public AController() {
        log.info("::ctor");
    }

    @PostMapping("/json")
    public Map<String, Object> json(@RequestBody TaskRequest taskRequest) {
        log.info("::post");
        return paService.a(taskRequest);
    }
}
