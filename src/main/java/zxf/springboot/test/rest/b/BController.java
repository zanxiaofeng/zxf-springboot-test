package zxf.springboot.test.rest.b;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class BController {
    public BController(){
        System.out.println("*******************BController::ctor*******************");
    }
}
