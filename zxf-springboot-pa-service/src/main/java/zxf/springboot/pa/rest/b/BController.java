package zxf.springboot.pa.rest.b;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/pa/b")
public class BController {

    public BController() {
        log.info("::ctor");
    }

    @GetMapping("/json")
    public Map<String, String> json(@RequestParam String version) {
        log.info("::json");
        return Collections.singletonMap("value", "Value of B from PA: " + System.currentTimeMillis());
    }
}
