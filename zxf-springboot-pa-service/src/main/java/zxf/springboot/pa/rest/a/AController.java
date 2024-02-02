package zxf.springboot.pa.rest.a;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zxf.springboot.pa.service.PAService;

import java.util.Collections;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestController
@RequestMapping("/pa/a")
public class AController {

    public AController() {
        log.info("::ctor");
    }

    @GetMapping("/json")
    public ResponseEntity<Map<String, Object>> json(@RequestParam Integer task) throws InterruptedException {
        log.info("::json");
        return PAService.result(task, "A");
    }
}
