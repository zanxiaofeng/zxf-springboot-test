package zxf.springboot.pa.rest.b;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zxf.springboot.pa.service.b.BService;

import java.util.Map;

@RestController
@RequestMapping("/b")
public class BController {
    @Autowired
    private BService bService;
    @Value("${version}")
    private String version;

    public BController() {
        System.out.println("*******************BController::ctor*******************");
    }

    @GetMapping("/json")
    public Map<String, Object> json() {
        System.out.println("*******************BController::json*******************");
        return bService.json(version);
    }
}
