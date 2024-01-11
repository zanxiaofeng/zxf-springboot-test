package zxf.springboot.pa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@Slf4j
@EnableFeignClients
@SpringBootApplication
public class Application {
    public Application() {
        log.info("::ctor");
    }

    public static void main(String[] args) {
        log.info("::main");
        SpringApplication.run(Application.class, args);
    }
}

