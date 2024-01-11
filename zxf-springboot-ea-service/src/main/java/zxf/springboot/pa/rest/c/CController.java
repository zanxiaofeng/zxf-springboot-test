package zxf.springboot.pa.rest.c;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zxf.springboot.pa.service.c.CService;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/c")
public class CController {
    @Autowired
    private CService cService;
    @Value("${version}")
    private String version;

    public CController() {
        log.info("::ctor");
    }

    @GetMapping("/json")
    public Map<String, Object> json() {
        log.info("::json");
        return cService.json(version);
    }
}
