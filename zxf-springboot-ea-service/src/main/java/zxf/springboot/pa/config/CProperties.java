package zxf.springboot.pa.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "c")
public class CProperties {
    private String name;

    public CProperties() {
        System.out.println("*******************CProperties::ctor*******************");
    }

    public String getName() {
        System.out.println("*******************CProperties::getName*******************");
        return name;
    }

    public void setName(String name) {
        System.out.println("*******************CProperties::setName*******************");
        this.name = name;
    }
}
