package zxf.springboot.pa.rest.b;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/pa/b")
public class BController {

    public BController() {
        System.out.println("*******************BController::ctor*******************");
    }

    @GetMapping("/json")
    public Map<String, String> json(@RequestParam String version) {
        System.out.println("*******************BController::json*******************");
        return null;
    }
}
