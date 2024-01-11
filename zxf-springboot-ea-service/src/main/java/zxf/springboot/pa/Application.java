package zxf.springboot.pa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class Application {
    public Application() {
        System.out.println("*******************Application::ctor*******************");
    }

    public static void main(String[] args) {
        System.out.println("*******************Application::main*******************");
        SpringApplication.run(Application.class, args);
    }
}

