package zxf.springboot.pa.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "b")
public class BProperties {
    private String name;

    public BProperties() {
        System.out.println("*******************BProperties::ctor*******************");
    }

    public String getName() {
        System.out.println("*******************BProperties::getName*******************");
        return name;
    }

    public void setName(String name) {
        System.out.println("*******************BProperties::setName*******************");
        this.name = name;
    }
}
