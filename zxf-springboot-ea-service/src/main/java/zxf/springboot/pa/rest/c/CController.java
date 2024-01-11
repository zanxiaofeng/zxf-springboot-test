package zxf.springboot.pa.rest.c;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zxf.springboot.pa.service.c.CService;

import java.util.Map;

@RestController
@RequestMapping("/c")
public class CController {
    @Autowired
    private CService cService;
    @Value("${version}")
    private String version;

    public CController() {
        System.out.println("*******************CController::ctor*******************");
    }

    @GetMapping("/json")
    public Map<String, String> json() {
        System.out.println("*******************CController::json*******************");
        return cService.json(version);
    }
}
