package zxf.springboot.pa.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ConfigurationProperties(prefix = "c")
public class CProperties {
    private String name;

    public CProperties() {
        log.info("::ctor");
    }

    public String getName() {
        log.info("::getName");
        return name;
    }

    public void setName(String name) {
        log.info("::setName");
        this.name = name;
    }
}
