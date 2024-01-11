package zxf.springboot.pa.rest.c;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/pa/c")
public class CController {
    public CController() {
        System.out.println("*******************CController::ctor*******************");
    }

    @GetMapping("/json")
    public Map<String, String> json(@RequestParam String version) {
        System.out.println("*******************CController::json*******************");
        return Collections.singletonMap("value", "Value of C from PA: " + System.currentTimeMillis());
    }
}
