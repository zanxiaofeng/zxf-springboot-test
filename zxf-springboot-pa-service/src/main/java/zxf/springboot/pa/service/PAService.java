package zxf.springboot.pa.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

public class PAService {
    public static ResponseEntity<Map<String, Object>> result(Integer task, String service) throws InterruptedException {
        HttpStatus httpStatus = HttpStatus.resolve(task);
        Map<String, Object> result = new HashMap<>();
        result.put("task", service + "-" + task);
        result.put("value", String.valueOf(System.currentTimeMillis()));
        switch (httpStatus) {
            case OK:
                return ResponseEntity.ok().body(result);
            case BAD_REQUEST:
                return ResponseEntity.status(BAD_REQUEST).build();
            case REQUEST_TIMEOUT:
                Thread.sleep(1000 * 10);
                return ResponseEntity.ok().body(result);
            case SERVICE_UNAVAILABLE:
                return ResponseEntity.status(SERVICE_UNAVAILABLE).build();
            default:
                return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }
}
