package zxf.springboot.pa.rest.b;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zxf.springboot.pa.service.b.BService;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/b")
public class BController {
    @Autowired
    private BService bService;
    @Value("${version}")
    private String version;

    public BController() {
        log.info("::ctor");
    }

    @GetMapping("/json")
    public Map<String, Object> json() {
        log.info("::json");
        return bService.json(version);
    }
}
