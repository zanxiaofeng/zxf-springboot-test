package zxf.springboot.test.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig {
    public MyConfig(){
        System.out.println("*******************MyConfig::ctor*******************");
    }
}
