package zxf.springboot.pa.rest.a;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/pa/a")
public class AController {

    public AController() {
        System.out.println("*******************AController::ctor*******************");
    }

    @GetMapping("/json")
    public Map<String, Object> json(@RequestParam String version) {
        System.out.println("*******************AController::json*******************");
        return null;
    }
}
