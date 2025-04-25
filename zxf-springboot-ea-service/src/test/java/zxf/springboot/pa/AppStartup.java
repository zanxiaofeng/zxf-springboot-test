package zxf.springboot.pa;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.ProcessIdUtil;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AppStartup implements ApplicationListener<ApplicationReadyEvent> {
    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        log.info("***************************App started {}, {}, {}***************************", ProcessIdUtil.getProcessId(), event.getSpringApplication().getMainApplicationClass(), event.getTimeTaken());
    }
}
