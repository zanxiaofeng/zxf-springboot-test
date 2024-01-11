package zxf.springboot.pa.rest.a;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zxf.springboot.pa.service.a.AService;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/a")
public class AController {
    @Autowired
    private AService aService;
    @Value("${version}")
    private String version;

    public AController() {
        log.info("::ctor");
    }

    @GetMapping("/json")
    public Map<String, Object> json() {
        log.info("::json");
        return aService.json(version);
    }
}
