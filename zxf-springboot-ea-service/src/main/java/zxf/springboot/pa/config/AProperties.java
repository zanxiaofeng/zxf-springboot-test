package zxf.springboot.pa.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "a")
public class AProperties {
    private String name;

    public AProperties() {
        System.out.println("*******************AProperties::ctor*******************");
    }

    public String getName() {
        System.out.println("*******************AProperties::getName*******************");
        return name;
    }

    public void setName(String name) {
        System.out.println("*******************AProperties::setName*******************");
        this.name = name;
    }
}
