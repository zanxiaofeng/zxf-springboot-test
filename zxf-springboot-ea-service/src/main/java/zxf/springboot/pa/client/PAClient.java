package zxf.springboot.pa.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(value = "pa-service", url = "${pa-service.url}", fallback = PAClientFallback.class)
public interface PAClient {
    @GetMapping("/pa/a/json")
    Map<String, Object> serviceA(@RequestParam String version);

    @GetMapping("/pa/b/json")
    Map<String, Object> serviceB(@RequestParam String version);

    @GetMapping("/pa/c/json")
    Map<String, Object> serviceC(@RequestParam String version);
}
