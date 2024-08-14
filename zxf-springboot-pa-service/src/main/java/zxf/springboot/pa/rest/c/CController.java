package zxf.springboot.pa.rest.c;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zxf.springboot.pa.service.PAService;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/pa/c")
public class CController {
    public CController() {
        log.info("::ctor");
    }

    @GetMapping("/json")
    public ResponseEntity<Map<String, Object>> json(@RequestParam Integer task) throws InterruptedException {
        log.info("::json");
        return PAService.result(task, "PA.C");
    }
}
