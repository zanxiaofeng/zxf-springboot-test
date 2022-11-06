package zxf.springboot.test.rest.b;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zxf.springboot.test.service.b.BService;

import java.util.Map;

@RestController
@RequestMapping("/b")
public class BController {
    @Autowired
    private BService bService;

    public BController() {
        System.out.println("*******************BController::ctor*******************");
    }

    @GetMapping("/json")
    public Map<String, String> json() {
        System.out.println("*******************BController::json*******************");
        return bService.json();
    }
}
