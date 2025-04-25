package zxf.springboot.pa;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.ProcessIdUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import zxf.springboot.pa.rest.HomeController;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
public class SmokeTest {
    @Autowired
    private HomeController homeController;

    @BeforeEach
    void setupForEach() throws IOException {
        log.info("***************************Before each {}***************************", ProcessIdUtil.getProcessId());
    }

    @Test
    public void contextLoads() {
        assertThat(homeController).isNotNull();
    }
}
