package zxf.springboot.pa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import zxf.springboot.pa.rest.HomeController;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SmokeTest {
    @Autowired
    private HomeController homeController;

    @Test
    public void contextLoads() {
        assertThat(homeController).isNotNull();
    }
}
