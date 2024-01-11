package zxf.springboot.pa.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class HomeController {
    public HomeController() {
        log.info("::ctor");
    }

    @GetMapping("/")
    public String greeting() {
        log.info("::greeting");
        return "Hello, World";
    }
}
