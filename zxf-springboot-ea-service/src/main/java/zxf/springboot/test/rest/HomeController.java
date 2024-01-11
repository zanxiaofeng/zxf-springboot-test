package zxf.springboot.test.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    public HomeController() {
        System.out.println("*******************HomeController::ctor*******************");
    }

    @GetMapping("/")
    public String greeting() {
        System.out.println("*******************HomeController::greeting*******************");
        return "Hello, World";
    }
}
