package zxf.springboot.pa.rest.a;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/pa/a")
public class AController {

    public AController() {
        log.info("::ctor");
    }

    @GetMapping("/json")
    public Map<String, Object> json(@RequestParam String version) {
        log.info("::json");
        return Collections.singletonMap("value", "Value of A from PA: " + System.currentTimeMillis());
    }
}
