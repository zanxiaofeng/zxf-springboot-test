package zxf.springboot.pa.rest.a;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

    @GetMapping("/json")
    public Map<String, Object> json(@RequestParam String task) {
        log.info("::json");
        return paService.a(task);
    }
}
