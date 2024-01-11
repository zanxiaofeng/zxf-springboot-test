package zxf.springboot.pa.client;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(value = "pa-service", url = "${pa-service.url}")
public interface PAClient {
    @GetMapping("/pa/a/json")
    public Map<String, Object> serviceA(@RequestParam String version);

    @GetMapping("/pa/b/json")
    public Map<String, Object> serviceB(@RequestParam String version);

    @GetMapping("/pa/c/json")
    public Map<String, Object> serviceC(@RequestParam String version);
}
