package zxf.springboot.test.rest.a;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zxf.springboot.test.service.a.AService;

import java.util.Map;

@RestController
@RequestMapping("/a")
public class AController {
    @Autowired
    private AService aService;
    @Value("${version}")
    private String version;

    public AController() {
        System.out.println("*******************AController::ctor*******************");
    }

    @GetMapping("/json")
    public Map<String, String> json() {
        System.out.println("*******************AController::json*******************");
        return aService.json(version);
    }
}
